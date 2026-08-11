# ☁️ EnergIAi — Configuración de Infraestructura en OCI

[![OCI](https://img.shields.io/badge/Oracle%20Cloud-Infrastructure-F80000?logo=oracle)](#-índice)
[![Ubuntu](https://img.shields.io/badge/Ubuntu-24.04-E95420?logo=ubuntu)](#4-instancias-vms)
[![Always Free](https://img.shields.io/badge/OCI-Always%20Free-brightgreen?logo=oracle)](#1-decisión-de-arquitectura-sin-nat-gateway)
[![systemd](https://img.shields.io/badge/systemd-servicio%20persistente-orange?logo=linux)](#54-dejarlo-corriendo-como-servicio-persistente-systemd)

Guía paso a paso de cómo se armó la infraestructura en Oracle Cloud Infrastructure (Free Tier) para el proyecto EnergIAi. Incluye las decisiones tomadas, los problemas que aparecieron y cómo se resolvieron — para que el equipo pueda reproducirlo o entender el porqué de cada pieza.

Región usada: **Brazil East (São Paulo)**.

---

## 📖 Índice

1. [Decisión de arquitectura: sin NAT Gateway](#1-decisión-de-arquitectura-sin-nat-gateway)
2. [Red (VCN)](#2-red-vcn)
3. [Security List (firewall de OCI)](#3-security-list-firewall-de-oci)
4. [Instancias (VMs)](#4-instancias-vms)
5. [VM Python — Despliegue de FastAPI](#5-vm-python--despliegue-de-fastapi)
6. [VM Java — Estado actual](#6-vm-java--estado-actual)
7. [Checklist de lo pendiente](#7-checklist-de-lo-pendiente)

---

## 1. Decisión de arquitectura: sin NAT Gateway

El plan original (documentado en el README del backend) era tener la VM Python en una **subred privada**, sin IP pública, alcanzable solo vía NAT Gateway para salida a internet.

**Problema encontrado:** las cuentas *Always Free* (con el Free Trial ya vencido) tienen el límite de NAT Gateway en **0** — Oracle se lo quitó a las cuentas gratuitas. Error real recibido: `NAT gateway limit per VCN reached`.

**Solución adoptada:** una sola subred **pública** para ambas VMs. La "privacidad" de la VM Python se logra con **reglas de firewall** (Security List de OCI + `iptables` del sistema operativo) en vez de aislamiento de red real:
- La VM Python tiene IP pública (necesaria para poder instalar paquetes con `pip`/`apt`), pero el puerto 8000 (FastAPI) solo acepta conexiones desde la subred interna (`10.0.0.0/24`), nunca desde internet.

---

## 2. Red (VCN)

| Recurso | Nombre | Detalle |
|---|---|---|
| VCN | `vcn-energiai` | CIDR `10.0.0.0/16` |
| Subred | `subnet-energiai-public` | CIDR `10.0.0.0/24`, tipo Public Regional |
| Internet Gateway | `ig-energiai` | — |
| Route Table | Default Route Table | Regla: `0.0.0.0/0` → `ig-energiai` |

### Pasos

1. **Create VCN** → nombre `vcn-energiai`, CIDR `10.0.0.0/16`, "Create Virtual Cloud Network Only" (no el wizard automático, para entender cada pieza), DNS hostnames activado.
2. **Create Internet Gateway** → `ig-energiai`.
3. **Create Subnet** → `subnet-energiai-public`, CIDR `10.0.0.0/24`, Subnet Access: Public.
4. **Route Table** → Add Route Rule: Target Type `Internet Gateway`, Destination `0.0.0.0/0`, Target `ig-energiai`.

---

## 3. Security List (firewall de OCI)

Reglas de **Ingress** agregadas sobre la Default Security List (además de las reglas ICMP/SSH que vienen por defecto):

| Source | Protocolo | Puerto destino | Motivo |
|---|---|---|---|
| `0.0.0.0/0` | TCP | `22` | SSH (viene por defecto) |
| `0.0.0.0/0` | TCP | `8080` | API Java, acceso público |
| `10.0.0.0/24` | TCP | `8000` | API Python (ML), **solo tráfico interno** |

Egress se dejó con la regla por defecto (todo el tráfico saliente permitido, `0.0.0.0/0`).

---

## 4. Instancias (VMs)

Ambas del tipo **`VM.Standard.E2.1.Micro`** (Always Free — 1 OCPU, 1 GB RAM), imagen **Canonical Ubuntu 24.04**.

> ⚠️ **Nota:** se intentó usar `VM.Standard.A1.Flex` (Ampere, más recursos, también Always Free) pero dio error `Out of capacity for shape VM.Standard.A1.Flex in availability domain AD-1` — es un problema de disponibilidad de Oracle, no algo corregible del lado nuestro. Se puede reintentar en otro momento; mientras tanto, `E2.1.Micro` funciona bien para el MVP.

> ⚠️ **Nota 2:** en el primer intento de crear la VM Python, quedó seleccionada por error la imagen **Oracle Linux 9** en vez de Ubuntu (usuario `opc` en vez de `ubuntu`, comandos `dnf` en vez de `apt`). Se detectó por el campo "Operating system" en el detalle de la instancia, y se resolvió borrando la VM y recreándola verificando explícitamente la imagen antes de confirmar.

| VM | IP pública | IP privada | Uso |
|---|---|---|---|
| `vm-energiai-java` | `163.176.43.143` | `10.0.0.213` | Backend Spring Boot |
| `vm-energiai-python` | `147.15.16.156` | `10.0.0.164` | API FastAPI (modelo ML) |

Cada VM se creó con:
- **Networking:** VCN `vcn-energiai`, subred `subnet-energiai-public`, IP pública automática activada.
- **SSH Keys:** par generado por Oracle (`Generate a key pair for me`), clave privada descargada y renombrada de forma identificable: `ssh-key-vm-energiai-java.key` y `ssh-key-vm-energiai-python.key`. **Una clave distinta por VM**, no reutilizar.

### Conexión SSH

```bash
ssh -i ssh-key-vm-energiai-java.key ubuntu@163.176.43.143
ssh -i ssh-key-vm-energiai-python.key ubuntu@147.15.16.156
```

En Windows, si `venv\Scripts\activate` (o cualquier script) da error de "ejecución de scripts deshabilitada", correr una vez:
```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```

En bash/Git Bash, si SSH dice `bad permissions` sobre la clave:
```bash
chmod 400 ssh-key-vm-energiai-java.key
```

---

## 5. VM Python — Despliegue de FastAPI

### 5.1 Instalar dependencias del sistema
```bash
sudo apt update
sudo apt install -y python3 python3-venv python3-pip git
```

### 5.2 Subir el proyecto desde Windows

Primero, **dentro de la VM por SSH**, crear la carpeta destino:
```bash
mkdir -p ~/fastapi_service
```

Después, desde la compu (no la VM), parado en la carpeta que contiene `app/`, `model/`, `Dockerfile`, `requirements.txt`:
```powershell
scp -i ssh-key-vm-energiai-python.key -r app model Dockerfile requirements.txt README.md ubuntu@147.15.16.156:~/fastapi_service/
```

**No copiar la carpeta `venv` de Windows** — no funciona en Linux, hay que crear una nueva nativa.

### 5.3 Entorno virtual e instalación
```bash
cd ~/fastapi_service
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

### 5.4 Dejarlo corriendo como servicio persistente (systemd)

Crear `/etc/systemd/system/energiai-ml.service`:
```bash
sudo nano /etc/systemd/system/energiai-ml.service
```

Contenido:
```ini
[Unit]
Description=EnergIAi ML Service (FastAPI)
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu/fastapi_service
ExecStart=/home/ubuntu/fastapi_service/venv/bin/uvicorn app.main:app --host 0.0.0.0 --port 8000
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
```

Activar:
```bash
sudo systemctl daemon-reload
sudo systemctl enable energiai-ml
sudo systemctl start energiai-ml
sudo systemctl status energiai-ml   # debe decir "active (running)"
```

Comandos útiles:
```bash
sudo systemctl restart energiai-ml    # tras subir código nuevo
journalctl -u energiai-ml -f          # logs en vivo
```

### 5.5 Firewall interno (iptables) — paso que casi se pasa por alto

**Problema encontrado:** aunque la Security List de OCI ya permitía el puerto 8000 desde `10.0.0.0/24`, la conexión seguía fallando (`Failed to connect ... after 0 ms`). Causa: las imágenes Ubuntu de OCI traen un `iptables` interno que por defecto **solo permite el puerto 22**, y rechaza todo lo demás — es un segundo firewall, además de la Security List de la nube.

**Solución**, ejecutado en la VM Python:
```bash
sudo iptables -L INPUT -n --line-numbers   # ver la posición de la regla REJECT
sudo iptables -I INPUT 5 -p tcp -s 10.0.0.0/24 --dport 8000 -j ACCEPT   # insertar ANTES del REJECT
sudo apt install -y iptables-persistent    # para que la regla sobreviva un reinicio
# Durante la instalación: "Save current IPv4 rules?" → Yes
```

> El número `5` corresponde a la posición de la regla REJECT en nuestro caso — verificar con `iptables -L INPUT -n --line-numbers` antes de insertar, puede variar.

### 5.6 Verificación

Desde afuera (debe fallar — confirma que está bien protegido):
```powershell
curl http://147.15.16.156:8000/health
# Esperado: error de conexión
```

Desde la VM Java, por la red interna (debe funcionar):
```bash
curl http://10.0.0.164:8000/health
# Esperado: {"status":"ok","modelo_cargado":true}
```

---

## 6. VM Java — Estado actual

- ✅ Java 21 instalado (`sudo apt install -y openjdk-21-jdk`), verificado con `java -version`.
- ⏳ Código Spring Boot: **pendiente** (en desarrollo por el equipo).
- ⏳ `MlModelClientImpl` apuntando a `http://10.0.0.164:8000/predict`: pendiente.
- ⏳ Servicio systemd para Java: pendiente (mismo patrón que se usó para Python, adaptado a `java -jar`).

---

## 7. Checklist de lo pendiente

- [ ] Terminar el backend Java (equipo Backend).
- [ ] Configurar `MlModelClientImpl` con la IP privada de Python (`10.0.0.164:8000`).
- [ ] Crear el servicio systemd de Java (mismo patrón que `energiai-ml.service`).
- [ ] Abrir el puerto 8080 en el `iptables` interno de la VM Java (mismo problema que tuvimos en Python — probablemente haga falta el mismo tipo de regla, pero para tráfico público en vez de solo `10.0.0.0/24`).
- [ ] Probar el flujo completo end-to-end: Postman/Web → VM Java (8080) → VM Python (8000) → respuesta.
- [ ] (Opcional) Reintentar `A1.Flex` si se necesita más RAM y hay disponibilidad.

---

## 🙌 Créditos

Infraestructura configurada y documentada por **[Pablo Graff](https://www.linkedin.com/in/hector-pablo-graff/)**.

Developed 💻 from 🇦🇷

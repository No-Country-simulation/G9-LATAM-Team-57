<div align="center">

# ☕ EnergIAi API — Backend Service

*API REST desacoplada y de alta performance para el procesamiento, análisis e integración del consumo energético en tiempo real.*

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](#️-tecnologías-utilizadas)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.x-brightgreen?logo=springboot)](#️-tecnologías-utilizadas)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](#️-tecnologías-utilizadas)
[![OCI](https://img.shields.io/badge/Oracle%20Cloud-Infrastructure-F80000?logo=oracle)](#️-arquitectura-de-infraestructura-oci)
[![Status](https://img.shields.io/badge/status-en%20desarrollo-yellow)](#-estado)

</div>

⬅️ Volver al [README principal del proyecto](../README.md)

---

## 📖 Índice

- [Estado](#-estado)
- [Descripción del problema](#-descripción-del-problema)
- [Arquitectura de la aplicación](#️-arquitectura-de-la-aplicación)
- [Arquitectura de infraestructura (OCI)](#️-arquitectura-de-infraestructura-oci)
- [Tecnologías utilizadas](#️-tecnologías-utilizadas)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Instalación y ejecución local](#️-instalación-y-ejecución-local)
- [Contrato de datos con el modelo](#-contrato-de-datos-con-el-modelo-data-science)
- [Cálculo del consumo promedio diario](#-cálculo-del-consumo-energético-promedio-diario)
- [Mock-Fallback (Client Layer)](#-mock-fallback-client-layer)
- [Endpoints del MVP](#-endpoints-del-mvp)
- [Postman](#-postman)
- [Despliegue en OCI](#-despliegue-en-oci)
- [Equipo Backend](#-equipo-backend)

---

## 🚦 Estado

🔧 **En desarrollo.** La estructura del proyecto, los DTOs, enums y la lógica de cálculo del promedio diario están definidos. Falta cablear `MlModelClientImpl` contra el servicio de ML (ya desplegado y operativo) y desplegar esta API en su VM correspondiente.

---

## 📋 Descripción del problema

El monitoreo de consumo energético requiere un backend robusto capaz de gestionar peticiones, validar entradas y comunicarse de forma segura con modelos predictivos externos.

Este servicio actúa como el **orquestador central**: recibe las solicitudes de consumo, procesa la lógica de negocio en Java y se conecta de forma agnóstica con servicios externos (como el modelo de Machine Learning desarrollado en Python).

---

## 🏗️ Arquitectura de la aplicación

```
┌──────────────┐     HTTP REST     ┌────────────────────────┐
│   Postman /  │ ────────────────▶ │  EnergIAi API (Java)   │
│   Frontend   │ ◀──────────────── │  Spring Boot 3.3 +     │
└──────────────┘                   │  Virtual Threads       │
                                    └───────────┬────────────┘
                                                │
                                 ┌──────────────┴──────────────┐
                                 ▼                             ▼
                       ┌──────────────────┐          ┌──────────────────┐
                       │ Client Layer     │          │ Persistencia BDD │
                       │ (Python ML Model │          │ (PostgreSQL /    │
                       │ / Fallback Mock) │          │ TimescaleDB)*    │
                       └──────────────────┘          └──────────────────┘
```

\* Nota: La capa de persistencia en base de datos está planificada como un hito incremental futuro.

📌 El recorrido completo de una petición, capa por capa, con datos reales de ejemplo:

![Flujo real de una petición en EnergIAi](docs/images/flujo_real_energiai_con_datos.png)

---

## ☁️ Arquitectura de infraestructura (OCI)

El proyecto está desplegado sobre **dos máquinas virtuales de Oracle Cloud Infrastructure (Free Tier)**, dentro de la VCN `vcn-energiai` (`10.0.0.0/16`), región Brazil East (São Paulo):

| Máquina | Rol | IP pública | IP privada | Puerto | Estado |
|---|---|---|---|---|---|
| **VM Java** | Backend principal (Spring Boot) | `163.176.43.143` | `10.0.0.213` | 8080 | 🔧 Java instalado, código pendiente de desplegar |
| **VM Python** | Servicio de Machine Learning (FastAPI + modelo `.pkl`) | `147.15.16.156` | `10.0.0.164` | 8000 | ✅ Desplegado y operativo |

**Sobre el acceso a la VM Python:** el plan original era una subred privada sin salida a internet, pero las cuentas *Always Free* de OCI no incluyen NAT Gateway. La VM Python quedó con IP pública (necesaria para instalar dependencias), y la restricción de acceso se logra por **firewall**: la Security List de OCI y el `iptables` interno solo aceptan tráfico al puerto 8000 desde la subred interna (`10.0.0.0/24`) — nunca desde internet. El resultado de seguridad es equivalente al aislamiento de red original.

![Arquitectura de red con las dos máquinas en OCI](docs/images/arquitectura_oci_dos_maquinas.png)

> 🔧 Proceso completo de configuración de infraestructura (paso a paso, decisiones y problemas resueltos) en [`oci/README.md`](../oci/README.md).

---

## 🛠️ Tecnologías utilizadas

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 21 (Virtual Threads / LTS) |
| Framework | Spring Boot 3.3.x |
| Integración externa | Spring Web Client / RestClient (comunicación con API Python) |
| Herramienta de Construcción | Maven |
| Documentación | Swagger / OpenAPI 3 |
| Control de versiones | Git + GitHub (feature/hpg-backend-java) |
| Infraestructura | Oracle Cloud Infrastructure (OCI) — 2 VM Compute (Free Tier) |
| Persistencia (Fase Futura) | PostgreSQL + TimescaleDB / Flyway |

---

## 📁 Estructura del proyecto

```
energiai-api/
├── .gitignore
├── pom.xml
├── README.md
├── docs/
│   └── images/                              # Diagramas de arquitectura y flujo, referenciados en este README
├── postman/                                 # Colección de Postman para probar los endpoints (ver sección Postman)
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── energiai/
    │   │           └── api/
    │   │               ├── client/          # Clientes para consumir la API de Python (con Mock-Fallback)
    │   │               ├── config/          # Configuraciones globales (CORS, Beans, RestClient)
    │   │               ├── controller/      # Endpoints REST anémicos
    │   │               ├── exception/       # Manejador global de excepciones
    │   │               ├── model/
    │   │               │   ├── dto/
    │   │               │   │   ├── request/  # DTOs de entrada
    │   │               │   │   └── response/ # DTOs de salida
    │   │               │   └── entity/       # Entidades de dominio (fase futura)
    │   │               ├── repository/      # Capa de acceso a datos (fase futura)
    │   │               ├── service/         # Interfaces de lógica de negocio
    │   │               │   └── impl/        # Implementación del negocio
    │   │               └── EnergiaiApiApplication.java
    │   └── resources/
    │       ├── application.properties
    │       └── db/
    │           └── migration/               # Scripts de Flyway (Fase Futura)
    └── test/                                # Tests unitarios con JUnit 5 y Mockito
```

---

## ⚙️ Instalación y ejecución local

### 1. Cloná el repositorio y posicionate en tu rama

```bash
git clone https://github.com/No-Country-simulation/G9-LATAM-Team-57.git
cd energiai-api
git checkout feature/hpg-backend-java
```

### 2. Compilá e iniciá la aplicación

En Windows (PowerShell / CMD):
```bash
mvnw.cmd spring-boot:run
```

En Linux / macOS / Git Bash:
```bash
./mvnw spring-boot:run
```

La aplicación estará escuchando en: `http://localhost:8080`

---

## 📊 Contrato de datos con el modelo (Data Science)

El equipo de Ciencia de Datos entrena el modelo con un dataset propio. Estas son las variables **ya confirmadas y cerradas** por el equipo de Data Science, y cómo se traducen al backend en Java.

| Columna (Python) | Dtype | Tipo en Java | Descripción |
|---|---|---|---|
| `Household_Size` | int64 | `Integer` | Cantidad de personas en el hogar |
| `Has_AC` | int64 | `Integer` | Si el hogar cuenta con aire acondicionado |
| `Home_Office` | bool | `Boolean` | Si se realiza home office en la vivienda |
| `Housing_Type` | object | `HousingType` (enum) | Tipo de vivienda |
| `Equipment_Count` | int64 | `Integer` | Cantidad de equipos eléctricos |
| `Avg_Energy_Consumption_kWh` | float64 | `Double` | Consumo energético promedio diario, calculado por Java (ver sección siguiente) |
| `Peak_Usage_Level` | object | `PeakUsageLevel` (enum) | Nivel de uso en horario pico |

Detalle completo del dataset y la metodología de entrenamiento en [`data-science/README.md`](../data-science/README.md).

### Enums

Estos dos campos tienen valores fijos y cerrados, confirmados por Data Science, por lo que se modelan como `enum` en vez de `String` libre — así el backend rechaza automáticamente cualquier valor inválido, sin depender de validación manual.

```java
package com.energiai.api.model.dto.request;

public enum HousingType {
    CASA,
    DEPARTAMENTO,
    MONOAMBIENTE
}
```

```java
package com.energiai.api.model.dto.request;

public enum PeakUsageLevel {
    LOW,
    MEDIUM,
    HIGH
}
```

### DTO de entrada (`ConsumoEnergeticoRequest`)

El usuario **no envía** `Avg_Energy_Consumption_kWh` directamente — envía el **consumo total del mes anterior**, y Java calcula el promedio diario (ver sección siguiente).

```java
package com.energiai.api.model.dto.request;

public class ConsumoEnergeticoRequest {

    private Integer householdSize;
    private Integer hasAc;
    private Boolean homeOffice;
    private HousingType housingType;
    private Integer equipmentCount;
    private Double consumoTotalMesAnterior; // consumo total en kWh, no el promedio diario
    private PeakUsageLevel peakUsageLevel;

    // getters, setters y validaciones (Bean Validation)
}
```

---

## 🧮 Cálculo del consumo energético promedio diario

El usuario **no ingresa directamente** el promedio diario (`Avg_Energy_Consumption_kWh`) — ingresa el **consumo total del mes anterior**. Java se encarga de calcularlo antes de mandarlo al modelo.

**Lógica:**

1. Java obtiene la fecha actual del sistema.
2. Calcula cuál fue el **mes anterior** al actual (no se puede tomar el mes en curso, porque todavía no finalizó y el consumo real no está cerrado).
3. Determina **cuántos días tuvo ese mes anterior** (considerando meses de 28, 29, 30 o 31 días, usando `java.time.YearMonth`, que resuelve años bisiestos automáticamente).
4. Divide el consumo total ingresado por esa cantidad de días, obteniendo el promedio diario.

```
Avg_Energy_Consumption_kWh = consumoTotalMesAnterior / díasDelMesAnterior
```

Esta lógica se implementa en el **`service`** (`AnalisisEnergeticoServiceImpl`), **antes** de invocar al `client` que se comunica con la API de Python — es lógica de negocio pura, no debe vivir en el `controller` ni en el `client`.

---

## 🔄 Mock-Fallback (Client Layer)

La capa `client/` se comunica con la API de Machine Learning en Python. Como ese servicio puede no estar disponible (todavía no existe, está caído, o tarda demasiado en responder), se implementa un **mecanismo de fallback**:

- Si la API de Python responde correctamente → se usa la predicción real del modelo.
- Si la API de Python falla o no responde → el backend en Java devuelve una respuesta simulada (mock), generada con reglas simples predefinidas (ej: umbrales de consumo), para que el servicio nunca quede completamente caído.

Esto garantiza disponibilidad del servicio aunque la precisión de la respuesta sea menor en ese caso puntual.

---

## 📄 Endpoints del MVP

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/v1/health` | Estado del servicio backend |
| POST | `/analisis-energetico` | Recibe el consumo del usuario, devuelve categoría, probabilidad, recomendaciones y costo estimado mensual |

### Ejemplo de request

```json
POST /analisis-energetico
{
  "householdSize": 4,
  "hasAc": 1,
  "homeOffice": true,
  "housingType": "CASA",
  "equipmentCount": 10,
  "consumoTotalMesAnterior": 420,
  "peakUsageLevel": "HIGH"
}
```

### Ejemplo de response

```json
{
  "categoria": "Ineficiente",
  "probabilidad": 0.81,
  "recomendaciones": [
    "Reducir el uso de equipos durante los horarios pico",
    "Evaluar equipos con alto consumo energético",
    "Distribuir las actividades de mayor consumo a lo largo del día"
  ],
  "costoEstimadoMensual": 315.00
}
```

---

## 📮 Postman

En la carpeta [`/postman`](../postman) se comparte la colección con las requests ya armadas para probar todos los endpoints de la API (incluyendo ejemplos de body para `POST /analisis-energetico`).

Para usarla:
1. Abrí Postman → **Import**
2. Seleccioná el archivo `.json` dentro de la carpeta `postman/`
3. Ejecutá las requests contra `http://localhost:8080` (entorno local) o `http://163.176.43.143:8080` (entorno desplegado, cuando el despliegue esté completo)

Esta colección es también el canal principal de prueba mientras no exista un front-end propio: tanto el consumo desde una eventual web como desde Postman le pegan al mismo endpoint REST.

---

## 🚀 Despliegue en OCI

| Detalle | Valor |
|---|---|
| VM Java (IP pública) | `163.176.43.143` |
| VM Java (IP privada) | `10.0.0.213` |
| VM Python (IP privada, para `MlModelClientImpl`) | `10.0.0.164` |
| VCN | `vcn-energiai` (`10.0.0.0/16`) |
| Subred | `subnet-energiai-public` (`10.0.0.0/24`) |

Security Lists configuradas para que la VM Python solo acepte tráfico al puerto 8000 desde la subred interna (`10.0.0.0/24`), no desde internet.

🔧 **Pendiente:** desplegar el `.jar` de esta API en la VM Java y configurarla como servicio `systemd` persistente (mismo patrón ya aplicado exitosamente en la VM Python — ver [`oci/README.md`](../oci/README.md)).

---

## 👥 Equipo Backend

| Nombre | Rol |
|---|---|
| [Pablo Graff](https://www.linkedin.com/in/hector-pablo-graff/) | Backend Developer |
| [Agustina Lerda](https://www.linkedin.com/in/agustina-lerda/) | Backend Developer |
| [Annie Lehmann](https://www.linkedin.com/in/annie-lehmann/) | Backend Developer |
| [Frank Mijhael Bendezu Hinostroza](https://www.linkedin.com/in/frankm01) | Full Stack Developer |

⬅️ Volver al [README principal del proyecto](../README.md)

---

Developed 💻 from 🇦🇷 and 🇵🇪

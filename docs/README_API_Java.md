30/7/26, 20:33 

README.md 

# ☕ EnergIAi API — Backend Service 

_API REST desacoplada y de alta performance para el procesamiento, análisis e integración del consumo energético en tiempo real._ 



<!-- Start of picture text -->
Spring Boot<br><!-- End of picture text -->



<!-- Start of picture text -->
Maven<br><!-- End of picture text -->



<!-- Start of picture text -->
Oracle Cloud<br><!-- End of picture text -->



<!-- Start of picture text -->
Infrastructure<br><!-- End of picture text -->

**Java 21 Spring Boot 3.3.x Build Maven Oracle Cloud Infrastructure** 

⬅ Volver al README principal del proyecto 

## 📖 Índice 

- Descripción del problema 

- Arquitectura de la aplicación 

- Arquitectura de infraestructura (OCI) 

- Tecnologías utilizadas 

- Estructura del proyecto 

- Instalación y ejecución local 

- Contrato de datos con el modelo 

- Cálculo del consumo promedio diario 

- Mock-Fallback (Client Layer) 

- Endpoints del MVP 

- Postman 

- Despliegue en OCI 

- Equipo Backend 

## 📋 Descripción del problema 

El monitoreo de consumo energético requiere un backend robusto capaz de gestionar peticiones, validar entradas y comunicarse de forma segura con modelos predictivos externos. 

Este servicio actúa como el **orquestador central** : recibe las solicitudes de consumo, procesa la lógica de negocio en Java y se conecta de forma agnóstica con servicios externos (como el modelo de Machine Learning desarrollado en Python). 

## 🏗 Arquitectura de la aplicación 

`┌──────────────┐     HTTP REST     ┌────────────────────────┐ │   Postman /  │ ────────────────` ▶ `│  EnergIAi API (Java)   │ │   Frontend   │` ◀ `──────────────── │  Spring Boot 3.3 +     │ └──────────────┘                   │  Virtual Threads       │ └───────────┬────────────┘ │ ┌──────────────┴──────────────┐ ▼                             ▼ ┌──────────────────┐          ┌──────────────────┐` 

127.0.0.1:5500/backend-java/README.html 

1/8 

30/7/26, 20:33 README.md `│ Client Layer     │          │ Persistencia BDD │ │ (Python ML Model │          │ (PostgreSQL /    │ │ / Fallback Mock) │          │ TimescaleDB)*    │ └──────────────────┘          └──────────────────┘` 

- Nota: La capa de persistencia en base de datos está planificada como un hito incremental futuro. 

📌 El recorrido completo de una petición, capa por capa, con datos reales de ejemplo: 



Flujo real de una petición en EnergIAi 

## ☁ Arquitectura de infraestructura (OCI) 

El proyecto se despliega sobre **dos máquinas virtuales (Oracle Cloud Infrastructure — Free Tier)** , separadas por motivos de seguridad y de responsabilidad: 

|**Máquina**|**Rol**|**Acceso**|**Puerto**|
|---|---|---|---|
|**VM Java**|Backend principal (Spring Boot)|Pública — accesible desde internet|8080|
|**VM**|Servicio de Machine Learning|Privada —**solo accesible desde la VM**|8000|
|**Python**|(FastAPI + modelo`.pkl`)|**Java**, dentro de la misma VCN||



La VM de Python **no tiene IP pública ni acceso desde internet** . Solo responde a peticiones que provienen de la IP privada de la VM Java, dentro de la misma **VCN (Virtual Cloud Network)** de OCI. Esto reduce la superficie de ataque: nadie externo puede consultar directamente el modelo de IA, únicamente a través de nuestra API en Java, que valida y orquesta cada solicitud. 



Arquitectura de red con las dos máquinas en OCI 

⚠ Las IPs reales de cada máquina se completan una vez desplegadas. Ver sección Despliegue en OCI. 

## 🛠 Tecnologías utilizadas 

|**Componente**|**Tecnología**|
|---|---|
|Lenguaje|Java 21 (Virtual Threads / LTS)|
|Framework|Spring Boot 3.3.x|
|Integración externa|Spring Web Client / RestClient (comunicación con API Python)|
|Herramienta de Construcción|Maven|
|Documentación|Swagger / OpenAPI 3|
|Control de versiones|Git + GitHub (feature/hpg-backend-java)|
|Infraestructura|Oracle Cloud Infrastructure (OCI) — 2 VM Compute (Free Tier)|
|Persistencia (Fase Futura)|PostgreSQL + TimescaleDB / Flyway|



127.0.0.1:5500/backend-java/README.html 

2/8 

30/7/26, 20:33 

README.md 

## 📁 Estructura del proyecto 



<!-- Start of picture text -->
energiai-api/<br>├── .gitignore<br>├── pom.xml<br>├── README.md<br>├── docs/<br>│   └── images/                              # Diagramas de arquitectura y flujo,<br>referenciados en este README<br>├── postman/                                 # Colección de Postman para probar<br>los endpoints (ver sección Postman)<br>└── src/<br>    ├── main/<br>    │   ├── java/<br>    │   │   └── com/<br>    │   │       └── energiai/<br>    │   │           └── api/<br>    │   │               ├── client/          # Clientes para consumir la API de<br>Python (con Mock-Fallback)<br>    │   │               ├── config/          # Configuraciones globales (CORS,<br>Beans, RestClient)<br>    │   │               ├── controller/      # Endpoints REST anémicos<br>    │   │               ├── exception/       # Manejador global de excepciones<br>    │   │               ├── model/<br>    │   │               │   ├── dto/<br>    │   │               │   │   ├── request/  # DTOs de entrada<br>    │   │               │   │   └── response/ # DTOs de salida<br>    │   │               │   └── entity/       # Entidades de dominio (fase<br>futura)<br>    │   │               ├── repository/      # Capa de acceso a datos (fase<br>futura)<br>    │   │               ├── service/         # Interfaces de lógica de negocio<br>    │   │               │   └── impl/        # Implementación del negocio<br>    │   │               └── EnergiaiApiApplication.java<br>    │   └── resources/<br>    │       ├── application.properties<br>    │       └── db/<br>    │           └── migration/               # Scripts de Flyway (Fase Futura)<br>    └── test/                                # Tests unitarios con JUnit 5 y<br>Mockito<br><!-- End of picture text -->

## ⚙ Instalación y ejecución local 

### 1. Cloná el repositorio y posicionate en tu rama 

```
git clone https://github.com/No-Country-simulation/G9-LATAM-Team-57.git
cd energiai-api
```

```
git checkout feature/hpg-backend-java
```

127.0.0.1:5500/backend-java/README.html 

3/8 

30/7/26, 20:33 

README.md 

### 2. Compilá e iniciá la aplicación 

En Windows (PowerShell / CMD): 

```
mvnw.cmd spring-boot:run
```

En Linux / macOS / Git Bash: 

```
./mvnw spring-boot:run
```

La aplicación estará escuchando en: `http://localhost:8080` 

## 📊 Contrato de datos con el modelo (Data Science) 

El equipo de Ciencia de Datos entrena el modelo con un dataset propio. Estas son las variables **ya confirmadas y cerradas** por el equipo de Data Science, y cómo se traducen al backend en Java. 

|**Columna (Python)**|**Dtype**|**Tipo en Java**|**Descripción**|
|---|---|---|---|
|`Household_Size`|int64|`Integer`|Cantidad de personas en el hogar|
|`Has_AC`|int64|`Integer`|Si el hogar cuenta con aire<br>acondicionado|
|`Home_Office`|bool|`Boolean`|Si se realiza home office en la<br>vivienda|
|`Housing_Type`|object|`HousingType`<br>(enum)|Tipo de vivienda|
|`Equipment_Count`|int64|`Integer`|Cantidad de equipos eléctricos|
|`Avg_Energy_Consumption_kWh`|float64|`Double`|Consumo energético promedio<br>diario, calculado por Java (ver<br>sección siguiente)|
|`Peak_Usage_Level`|object|`PeakUsageLevel`<br>(enum)|Nivel de uso en horario pico|



Detalle completo del dataset y la metodología de entrenamiento en `data-science/README.md` . 

### Enums 

Estos dos campos tienen valores fijos y cerrados, confirmados por Data Science, por lo que se modelan como `enum` en vez de `String` libre — así el backend rechaza automáticamente cualquier valor inválido, sin depender de validación manual. 

127.0.0.1:5500/backend-java/README.html 

4/8 

30/7/26, 20:33 

README.md 

```
package com.energiai.api.model.dto.request;
publicenumHousingType {
    CASA,
    DEPARTAMENTO,
    MONOAMBIENTE
}
```

```
package com.energiai.api.model.dto.request;
publicenumPeakUsageLevel {
    LOW,
    MEDIUM,
    HIGH
}
```

### DTO de entrada ( `ConsumoEnergeticoRequest` ) 

El usuario **no envía** `Avg_Energy_Consumption_kWh` directamente — envía el **consumo total del mes anterior** , y Java calcula el promedio diario (ver sección siguiente). 

```
package com.energiai.api.model.dto.request;
publicclassConsumoEnergeticoRequest {
private Integer householdSize;
private Integer hasAc;
private Boolean homeOffice;
private HousingType housingType;
private Integer equipmentCount;
private Double consumoTotalMesAnterior; // consumo total en kWh, no el
promedio diario
private PeakUsageLevel peakUsageLevel;
// getters, setters y validaciones (Bean Validation)
}
```

## 🧮 Cálculo del consumo energético promedio diario 

El usuario **no ingresa directamente** el promedio diario ( `Avg_Energy_Consumption_kWh` ) — ingresa el **consumo total del mes anterior** . Java se encarga de calcularlo antes de mandarlo al modelo. 

#### **Lógica:** 

1. Java obtiene la fecha actual del sistema. 

127.0.0.1:5500/backend-java/README.html 

5/8 

30/7/26, 20:33 

README.md 

2. Calcula cuál fue el **mes anterior** al actual (no se puede tomar el mes en curso, porque todavía no finalizó y el consumo real no está cerrado). 

3. Determina **cuántos días tuvo ese mes anterior** (considerando meses de 28, 29, 30 o 31 días, usando `java.time.YearMonth` , que resuelve años bisiestos automáticamente). 

4. Divide el consumo total ingresado por esa cantidad de días, obteniendo el promedio diario. 

```
Avg_Energy_Consumption_kWh = consumoTotalMesAnterior / díasDelMesAnterior
```

Esta lógica se implementa en el **`service`** ( `AnalisisEnergeticoServiceImpl` ), **antes** de invocar al `client` que se comunica con la API de Python — es lógica de negocio pura, no debe vivir en el `controller` ni en el `client` . 

## 🔄 Mock-Fallback (Client Layer) 

La capa `client/` se comunica con la API de Machine Learning en Python. Como ese servicio puede no estar disponible (todavía no existe, está caído, o tarda demasiado en responder), se implementa un **mecanismo de fallback** : 

- Si la API de Python responde correctamente → se usa la predicción real del modelo. 

- Si la API de Python falla o no responde → el backend en Java devuelve una respuesta simulada (mock), generada con reglas simples predefinidas (ej: umbrales de consumo), para que el servicio nunca quede completamente caído. 

Esto garantiza disponibilidad del servicio aunque la precisión de la respuesta sea menor en ese caso puntual. 

## 📄 Endpoints del MVP 

|**Método**|**Endpoint**|**Descripción**|
|---|---|---|
|GET|`/api/v1/health`|Estado del servicio backend|
|POST|`/analisis-`<br>`energetico`|Recibe el consumo del usuario, devuelve categoría, probabilidad,<br>recomendaciones y costo estimado mensual|



### Ejemplo de request 

```
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

127.0.0.1:5500/backend-java/README.html 

6/8 

30/7/26, 20:33 

README.md 

### Ejemplo de response 

```
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

## 📮 Postman 

En la carpeta `/postman` se comparte la colección con las requests ya armadas para probar todos los endpoints de la API (incluyendo ejemplos de body para `POST /analisis-energetico` ). 

Para usarla: 

1. Abrí Postman → **Import** 

2. Seleccioná el archivo `.json` dentro de la carpeta `postman/` 

3. Ejecutá las requests contra `http://localhost:8080` (entorno local) o la IP pública de la VM Java (entorno desplegado) 

Esta colección es también el canal principal de prueba mientras no exista un front-end propio: tanto el consumo desde una eventual web como desde Postman le pegan al mismo endpoint REST. 

## 🚀 Despliegue en OCI 

- 🔧 Sección en construcción — se completa a medida que se aprovisionan las máquinas. 

   - **VM Java** : IP pública `pendiente` 

   - **VM Python** : IP privada `pendiente` 

   - VCN: `pendiente` 

Security Lists configuradas para que la VM Python solo acepte tráfico desde la VM Java. 

## 👥 Equipo Backend 

|**Nombre**|**Rol**|
|---|---|
|Pablo Graff|Backend Developer|
|Agustina Lerda|Backend Developer|
|Annie Lehmann|Backend Developer|
|Frank Mijhael Bendezu Hinostroza|Full Stack Developer|



127.0.0.1:5500/backend-java/README.html 

7/8 

30/7/26, 20:33 

README.md 

⬅ Volver al README principal del proyecto 

Developed 💻 from 🇦🇷 and 🇵🇪 

127.0.0.1:5500/backend-java/README.html 

8/8 


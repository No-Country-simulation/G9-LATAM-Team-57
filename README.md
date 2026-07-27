# ⚡ EnergIAi API — Backend Service

API REST desacoplada y de alta performance para el procesamiento, análisis e integración del consumo energético en tiempo real.

Este backend está diseñado bajo una arquitectura por capas, garantizando resiliencia y separación limpia de responsabilidades frente a los modelos de inteligencia artificial y consumo de clientes.

---

## 📋 Descripción del problema

El monitoreo de consumo energético requiere un backend robusto capaz de gestionar peticiones, validar entradas y comunicarse de forma segura con modelos predictivos externos.

Este servicio actúa como el orquestador central: recibe las solicitudes de consumo, procesa la lógica de negocio en Java y se conecta de forma agnóstica con servicios externos (como los modelos de Machine Learning desarrollados en Python).

---

## 🏗️ Arquitectura

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
| Persistencia (Fase Futura) | PostgreSQL + TimescaleDB / Flyway |

---

## 📁 Estructura del proyecto

```
energiai-api/
├── .gitignore
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── energiai/
    │   │           └── api/
    │   │               ├── client/          # Clientes para consumir la API de Python (con Mock-Fallback)
    │   │               ├── config/          # Configuraciones globales (CORS, Beans)
    │   │               ├── controller/      # Endpoints REST anémicos
    │   │               ├── exception/       # Manejador global de excepciones
    │   │               ├── model/
    │   │               │   ├── dto/         # Request y Response Data Transfer Objects
    │   │               │   └── entity/      # Entidades de dominio
    │   │               ├── repository/      # Capa de acceso a datos
    │   │               ├── service/         # Interfaces de lógica de negocio
    │   │               │   └── impl/        # Implementación agnóstica del negocio
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

La aplicación estará escuchando en: http://localhost:8080

---

## 📊 Contrato de datos con el modelo (Data Science)

El equipo de Ciencia de Datos entrena el modelo con un dataset propio. Estas son las variables que recibimos de ellos y cómo se traducen al backend en Java.

> ⚠️ **Importante**: este contrato puede cambiar mientras Data Science ajusta su modelo. Cualquier cambio en estas variables debe ser comunicado y actualizado en esta tabla antes de modificar el código.

| Columna (Python) | Dtype | Tipo en Java | Descripción |
|---|---|---|---|
| `Household_Size` | int64 | `Integer` | Cantidad de personas en el hogar |
| `Has_AC` | int64 | `Integer` (o `Boolean` si se confirma que es 0/1) | Si el hogar cuenta con aire acondicionado |
| `Home_Office` | bool | `Boolean` | Si se realiza home office en la vivienda |
| `Housing_Type` | object | `String` | Tipo de vivienda (ej: "Casa", "Departamento", "Monoambiente") |
| `Equipment_Count` | int64 | `Integer` | Cantidad de equipos eléctricos |
| `Avg_Energy_Consumption_kWh` | float64 | `Double` | Consumo energético promedio en kWh |
| `Peak_Usage_Level` | object | `String` | Nivel de uso en horario pico ("Low", "Medium", "High") |

**DTO correspondiente:**

```java
package com.energiai.api.model.dto.request;

public class ConsumoEnergeticoRequest {

    private Integer householdSize;
    private Integer hasAc;
    private Boolean homeOffice;
    private String housingType;
    private Integer equipmentCount;
    private Double avgEnergyConsumptionKwh;
    private String peakUsageLevel;

    // getters y setters
}
```

### Nota sobre el encoding del modelo

El modelo de Data Science no recibe `Housing_Type` ni `Peak_Usage_Level` como texto simple. Internamente usa **One-Hot Encoding**: cada valor posible se convierte en su propia columna con 1 o 0 (ej: `Peak_Usage_Level_Low`, `Peak_Usage_Level_Medium`, `Peak_Usage_Level_High`).

Esto significa que el usuario le sigue mandando a nuestra API un texto simple (`"Low"`, `"Medium"`, `"High"`), pero **antes de reenviarlo al modelo Python**, alguien tiene que transformar ese texto en las columnas 0/1 que el modelo espera. Esta transformación todavía no está definida quién la hace (Java o el propio servicio Python) — pendiente de definir con el equipo de Data Science.

---

## 🔄 Mock-Fallback (Client Layer)

La capa `client/` se comunica con la API de Machine Learning en Python. Como ese servicio puede no estar disponible (todavía no existe, está caído, o tarda demasiado en responder), se implementa un **mecanismo de fallback**:

- Si la API de Python responde correctamente → se usa la predicción real del modelo.
- Si la API de Python falla o no responde → el backend en Java devuelve una respuesta simulada (mock), generada con reglas simples predefinidas (ej: umbrales de consumo), para que el servicio nunca quede completamente caído.

Esto garantiza disponibilidad del servicio aunque la precisión de la respuesta sea menor en ese caso puntual.

---

## 📄 Endpoints del MVP (Fase 1)

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/health | Estado del servicio backend |

---

## 👤 Autor

## 👥 Equipo Backend

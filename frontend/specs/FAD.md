# Frontend Architecture Document (FAD)

## Proyecto
**EnergIAi – Inteligencia para el Consumo Energético**

**Versión:** 1.0  
**Estado:** Draft  
**Framework:** Angular 20 (Standalone Components)

---

# Historial de Versiones

| Versión | Fecha | Autor | Descripción |
|---------|------|--------|-------------|
| 1.0 | Julio 2026 | Frontend Team | Primera versión |

---

# Tabla de Contenido

1. Objetivo
2. Arquitectura General
3. Stack Tecnológico
4. Estructura del Proyecto
5. Arquitectura por Capas
6. Módulos Funcionales
7. Componentes
8. Servicios
9. DTOs
10. Manejo de Estado
11. Routing
12. Gestión de Errores
13. Consumo de API
14. Convenciones
15. Roadmap

---

# 1. Objetivo

Este documento define la arquitectura técnica del Frontend de EnergIAi.

Su propósito es estandarizar la estructura del proyecto Angular para facilitar el desarrollo, mantenimiento y escalabilidad.

---

# 2. Arquitectura General

```

Usuario

↓

Angular

↓

Spring Boot API

↓

FastAPI

↓

Pipeline ML

```

El Frontend nunca consumirá directamente FastAPI.

Toda comunicación será realizada exclusivamente mediante la API desarrollada en Spring Boot.

---

# 3. Stack Tecnológico

| Tecnología | Uso |
|------------|-----|
| Angular 20 | Framework |
| TypeScript | Lenguaje |
| Angular Material | Componentes UI |
| Angular Signals | Estado Local |
| Reactive Forms | Formularios |
| HttpClient | Consumo REST |
| RxJS | Programación reactiva |
| SCSS | Estilos |
| Angular Router | Navegación |
| ngx-echarts | Gráficos (opcional) |
| SweetAlert2 | Alertas |
| ngx-spinner | Indicador de carga |

---

# 4. Estructura del Proyecto

```

src/

│

├── app/

│

├── core/

│ ├── config/

│ ├── interceptors/

│ ├── services/

│ ├── models/

│ └── guards/

│

├── features/

│

│ ├── home/

│ │ ├── pages/

│ │ ├── components/

│ │ └── services/

│ │

│ ├── analysis/

│ │ ├── pages/

│ │ ├── components/

│ │ ├── models/

│ │ ├── services/

│ │ └── store/

│ │

│ └── shared/

│ ├── components/

│ ├── directives/

│ └── pipes/

│

├── assets/

├── environments/

└── styles/

```

---

# 5. Arquitectura por Capas

## Presentación

Responsable de la interfaz.

Contiene:

- Pages
- Components

---

## Aplicación

Responsable de la lógica de interacción.

Contiene:

- Services
- State

---

## Infraestructura

Responsable de la comunicación con el Backend.

Contiene:

- HttpClient
- Interceptors

---

# 6. Módulos Funcionales

## Home

Responsabilidad

Página principal.

---

## Analysis

Responsabilidad

Formulario de análisis.

Resultados.

Comunicación con la API.

---

## Shared

Responsabilidad

Componentes reutilizables.

---

# 7. Componentes

## HomePageComponent

Landing principal.

---

## NavbarComponent

Barra superior.

---

## FooterComponent

Pie de página.

---

## EnergyFormComponent

Formulario principal.

Responsabilidades

- Captura de datos.
- Validaciones.
- Construcción del DTO.

---

## LoadingComponent

Spinner.

---

## ResultCardComponent

Visualización del resultado.

---

## RecommendationCardComponent

Lista de recomendaciones.

---

## CostCardComponent

Costo estimado.

---

## ErrorComponent

Visualización de errores.

---

# 8. Servicios

## EnergyAnalysisService

Responsabilidades

- Consumir POST /analisis-energetico.
- Retornar Observable<ResponseDTO>.

Métodos

analyzeConsumption()

---

## NotificationService

Mostrar mensajes.

---

## LoadingService

Administrar spinner global.

---

# 9. DTOs

## Request

```typescript
export interface EnergyAnalysisRequest{

    householdSize:number;

    hasAc:number;

    homeOffice:boolean;

    housingType:string;

    equipmentCount:number;

    consumoTotalMesAnterior:number;

    peakUsageLevel:string;

}
```

---

## Response

```typescript
export interface EnergyAnalysisResponse{

    categoria:string;

    probabilidad:number;

    costoEstimadoMensual:number;

    recomendaciones:string[];

}
```

---

# 10. Manejo de Estado

Se utilizarán Angular Signals.

Estado global mínimo.

Signals propuestos

```

loadingSignal

analysisResultSignal

errorSignal

```

No se utilizará NgRx.

No se utilizará Akita.

El proyecto no requiere un Store complejo.

---

# 11. Routing

```

/

↓

Home

-------------------

/analysis

↓

Formulario

↓

Resultado

```

Configuración

```typescript
const routes: Routes = [

{

path: '',

component: HomePageComponent

},

{

path:'analysis',

component:AnalysisPageComponent

}

];
```

---

# 12. Gestión de Errores

Todos los errores HTTP serán interceptados mediante un HttpInterceptor.

Casos considerados

400

Mostrar validaciones.

---

404

Servicio no encontrado.

---

500

Error interno.

---

503

Servicio temporalmente no disponible.

---

Timeout

Mostrar mensaje de conexión.

---

# 13. Integración con Backend

Endpoint principal

```

POST /analisis-energetico

```

Headers

```

Content-Type

application/json

```

Request

EnergyAnalysisRequest

Response

EnergyAnalysisResponse

---

# 14. Convenciones

## Componentes

PascalCase

Ejemplo

EnergyFormComponent

---

## Servicios

Sufijo Service

Ejemplo

EnergyAnalysisService

---

## Interfaces

Prefijo I no utilizado.

Ejemplo

EnergyAnalysisRequest

---

## Variables

camelCase

---

## Carpetas

kebab-case

---

# 15. Estrategia de Validación

Reactive Forms.

Validators.required

Validators.min()

Validators.max()

Validators.pattern()

---

# 16. Seguridad

Nunca almacenar información sensible.

No persistir resultados en LocalStorage.

Consumir únicamente HTTPS en producción.

---

# 17. Responsive

Desktop

≥1200 px

---

Tablet

768–1199 px

---

Mobile

<768 px

---

# 18. Rendimiento

Lazy Loading para futuras funcionalidades.

Standalone Components.

OnPush Change Detection.

Signals para minimizar renders.

---

# 19. Escalabilidad

Preparado para incorporar

- Dashboard.
- Historial.
- Autenticación.
- Comparación entre períodos.
- Exportación PDF.
- Procesamiento CSV.

Sin modificar la arquitectura base.

---

# 20. Roadmap Técnico

Sprint 1

- Configuración Angular.
- Angular Material.
- Layout.

Sprint 2

- Formulario.
- Validaciones.
- DTOs.

Sprint 3

- Integración Spring Boot.
- Interceptor.
- Manejo de errores.

Sprint 4

- Resultados.
- Recomendaciones.
- Indicadores.

Sprint 5

- Responsive.
- Optimización.
- Pruebas.

---

# Anexo A – Flujo Técnico

```

Usuario

↓

EnergyFormComponent

↓

Reactive Form

↓

EnergyAnalysisService

↓

HttpClient

↓

Spring Boot API

↓

Response DTO

↓

Signal

↓

ResultCardComponent

```

---

# Anexo B – Principios Arquitectónicos

- Single Responsibility Principle (SRP).
- Separation of Concerns (SoC).
- Component-Based Architecture.
- Standalone Components.
- Stateless UI Components cuando sea posible.
- Comunicación exclusivamente mediante servicios.
- Estado mínimo compartido mediante Signals.
- Contratos tipados con DTOs.
- El Frontend no contiene lógica de negocio del dominio ni lógica del modelo de Machine Learning.

# TODO – EnergIAi Frontend

## Referencias
- [SRS](specs/SRS.md) – Software Requirements Specification
- [FAD](specs/FAD.md) – Frontend Architecture Document
- [ADG](specs/ADG.md) – Angular Coding Guidelines
- [AIC](specs/AIC.md) – API Integration Contract
- [UI/UX](specs/UI_UX_SPECS.md) – UI/UX Specification
- [FFS](specs/FFS.md) – Frontend Functional Specification

---

## Sprint 1 – Configuración y Layout Base

### Configuración del Proyecto
- [x] Crear proyecto Angular 20 con Standalone Components
- [x] Configurar SCSS como preprocesador de estilos
- [x] Instalar y configurar Angular Material (tema personalizado con paleta verde/azul)
- [x] Instalar dependencias adicionales: SweetAlert2, ngx-spinner, ngx-echarts (opcional)
- [x] Configurar ESLint + Prettier
- [x] Configurar Husky + lint-staged para pre-commit hooks
- [x] Configurar `environments/` con `apiUrl` para desarrollo y producción
- [x] Definir estructura de carpetas: `core/`, `features/`, `shared/`, `assets/`, `styles/`

### Layout Global
- [x] Crear `NavbarComponent` (shared) – Logo + nombre "EnergIAi"
- [x] Crear `FooterComponent` (shared) – Pie de página
- [x] Configurar tipografía (Inter / Roboto)
- [x] Definir variables SCSS globales (colores primario, secundario, error, advertencia, fondo)
- [x] Implementar layout responsive base (Desktop ≥1200px, Tablet 768–1199px, Mobile <768px)

### Routing
- [x] Configurar Angular Router con rutas por feature
- [x] Ruta `/` → HomePageComponent
- [x] Ruta `/analysis` → AnalysisPageComponent

---

## Sprint 2 – Formulario y Validaciones

### DTOs y Modelos
- [x] Crear `EnergyAnalysisRequest` interface en `features/analysis/models/`
- [x] Crear `EnergyAnalysisResponse` interface en `features/analysis/models/`
- [x] Crear enums: `HousingType` (CASA, DEPARTAMENTO, MONOAMBIENTE)
- [x] Crear enum: `PeakUsageLevel` (LOW, MEDIUM, HIGH)

### Formulario (EnergyFormComponent)
- [x] Crear `EnergyFormComponent` con Reactive Forms
- [x] Campo: Personas en la vivienda (Input Number, min: 1, obligatorio)
- [x] Campo: Aire acondicionado (Radio Button: Sí/No → mapeo a 1/0)
- [x] Campo: Home Office (Radio Button: Sí/No → mapeo a true/false)
- [x] Campo: Tipo de vivienda (Select: Casa, Departamento, Monoambiente → CASA, DEPARTAMENTO, MONOAMBIENTE)
- [x] Campo: Equipos eléctricos (Input Number, min: 0, obligatorio)
- [x] Campo: Consumo último recibo (Input Number, min: >0, unidad kWh, obligatorio)
- [x] Campo: Horas uso simultáneo (Radio Button: 0–2h, 3–5h, >5h → LOW, MEDIUM, HIGH)
- [x] Implementar validaciones con Validators (required, min, max, pattern)
- [x] Mostrar mensajes de error por campo (mat-error)
- [x] Botón "Analizar consumo" deshabilitado si formulario inválido
- [x] Agregar labels, placeholders y mensajes de ayuda por accesibilidad
- [x] Implementar método de construcción del Request DTO desde el formulario

### Landing Page
- [x] Tener la landing page en base al design system definido.
---

Design System (Figma)
        ↓
Wireframes
        ↓
Prototipo de Alta Fidelidad
        ↓
Figma MCP
        ↓
Angular Material Components
        ↓
Integración con Spring Boot


## Sprint 3 – Integración con Backend

### Servicios
- [x] Crear `EnergyAnalysisService` en `features/analysis/services/`
  - Método `analyzeConsumption(request: EnergyAnalysisRequest): Observable<EnergyAnalysisResponse>`
  - Consumir `POST /analisis-energetico` (ruta canónica expuesta por Java)
  - Usar `HttpClient` con URL desde `environment.apiUrl`
- [x] Crear `NotificationService` en `core/services/` (wrapper de SweetAlert2)
- [x] Crear `LoadingService` en `core/services/` (administrar spinner global)

### Interceptor de Errores
- [x] Crear `HttpErrorInterceptor` en `core/interceptors/`
  - Manejar 400: mostrar validaciones del backend
  - Manejar 404: servicio no encontrado
  - Manejar 500: error interno
  - Manejar 503: servicio no disponible
  - Manejar timeout: mensaje de conexión
- [x] Registrar interceptor en la configuración de la app

### Signals (Estado)
- [x] Implementar `loading = signal(false)` en el componente/servicio
- [x] Implementar `result = signal<EnergyAnalysisResponse | null>(null)`
- [x] Implementar `error = signal<string | null>(null)`

---

## Sprint 4 – Pantalla de Resultados

### Componentes de Resultado
- [x] Crear `AnalysisPageComponent` (page container)
- [x] Crear `ResultCardComponent` – Muestra categoría + probabilidad con indicador visual
  - 🟢 Eficiente (verde)
  - 🟡 Moderado (amarillo)
  - 🔴 Ineficiente (rojo)
  - Barra de progreso con porcentaje de probabilidad
- [x] Crear `CostCardComponent` – Muestra costo estimado mensual (formato moneda)
- [x] Crear `RecommendationCardComponent` – Lista de recomendaciones con íconos ✓
- [x] Crear `ErrorComponent` – Tarjeta de error con mensaje + botón "Reintentar"
- [x] Crear `LoadingComponent` – Spinner + mensaje "Analizando consumo energético..."

### Flujo Completo
- [x] Integrar formulario → servicio → resultados en AnalysisPageComponent
- [x] Implementar transición entre estados: formulario → loading → resultado/error
- [x] Botón "Nuevo análisis" que resetea formulario y vuelve al estado inicial
- [x] Prevenir envío múltiple de solicitudes simultáneas
- [x] Animaciones: Fade In (200ms) entre pantallas

---

## Sprint 5 – Responsive, Optimización y Pruebas

### Responsive
- [x] Verificar y ajustar formulario en Mobile (<768px)
- [x] Verificar y ajustar resultados en Tablet (768–1199px)
- [x] Verificar y ajustar layout general en Desktop (≥1200px)
- [ ] Testear navegación y usabilidad en todos los breakpoints

### Optimización
- [x] Aplicar OnPush Change Detection en todos los componentes
- [x] Verificar Lazy Loading para rutas futuras
- [x] Minimizar re-renders con Signals
- [x] Verificar bundle size

### Pruebas
- [ ] Verificar que el formulario se completa en menos de 2 minutos
- [ ] Verificar todas las validaciones funcionan correctamente
- [ ] Verificar integración con API (request JSON correcto)
- [ ] Verificar respuesta se muestra correctamente
- [ ] Verificar manejo de errores (400, 500, 503, timeout)
- [ ] Verificar responsive en dispositivos reales o DevTools
- [ ] Verificar accesibilidad (labels, keyboard navigation)

### Calidad de Código
- [x] Verificar: no existen `console.log()`
- [x] Verificar: no se usa `any`
- [x] Verificar: todos los formularios usan Reactive Forms
- [x] Verificar: componentes son Standalone
- [x] Verificar: servicios consumen HttpClient (no fetch)
- [x] Verificar: no hay URLs hardcodeadas
- [x] Verificar: estructura del proyecto respetada
- [x] Verificar: código formateado (ESLint + Prettier)

---

## Sprint 5.5 – Validación Local Pre-Integración

### 5.5.1 Punto de Partida y Aislamiento Git

- [x] Ejecutar `git fetch origin main frontend`.
- [x] Crear el worktree `../G9-LATAM-Team-57-integration` desde `origin/main`.
- [x] Crear la rama local `integration/frontend-api`.
- [x] Aplicar los commits completos del frontend mediante `cherry-pick` en orden cronológico hasta `a51d1a5`.
- [x] Resolver los conflictos `modify/delete` conservando los archivos del frontend.
- [x] Verificar que el worktree contiene `backend-java/`, `backend-python/`, `oci/` y `frontend/energiai/`.
- [x] Mantener `integration/frontend-api` únicamente local y sin cambios pendientes.

### 5.5.2 Levantamiento Local de la Arquitectura

- [x] Crear el virtualenv `/tmp/energiai-sprint55-py313` con Python 3.13.
- [x] Instalar `backend-python/requirements.txt` correctamente.
- [x] Levantar FastAPI en `http://localhost:8000`.
- [x] Confirmar `GET /health` con `modelo_cargado: true`.
- [x] Confirmar `POST /predict` con HTTP 200.
- [ ] Levantar Spring Boot en `http://localhost:8080`; el puerto estaba ocupado por un contenedor Docker existente.
- [x] Levantar Spring Boot temporalmente en `http://localhost:18080`.
- [x] Confirmar `GET /api/v1/health` con HTTP 200 y `status: UP`.
- [x] Ejecutar `npm ci` con `--legacy-peer-deps` debido a la incompatibilidad Angular 20/22 existente.
- [x] Ejecutar `npx ng test --watch=false` con 4/4 pruebas exitosas.
- [x] Ejecutar `npx eslint src/app/` sin errores.
- [x] Ejecutar `npx ng build` correctamente.
- [x] Levantar Angular con `useMock=true` y validar el flujo mock en `http://localhost:4200`.

### 5.5.3 Validación Java → Python

- [x] Ejecutar `POST /analisis-energetico` contra Spring Boot local con HTTP 200.
- [x] Confirmar respuesta con categoría, probabilidad, costo y recomendaciones.
- [x] Confirmar `fuenteDatos: "IA_PYTHON_REAL"` y uso efectivo del modelo Python.
- [x] Probar datos inválidos y errores controlados.
- [x] Confirmar `householdSize=0` con HTTP 400 y `ErrorResponse.message` utilizable.
- [x] Confirmar indisponibilidad de Python con HTTP 200 y `fuenteDatos: "MOCK_FALLBACK"`.
- [ ] Corregir en Backend la aceptación de `consumoTotalMesAnterior=0` (actualmente responde HTTP 200; el contrato exige `> 0`).
- [ ] Corregir en Backend el tratamiento de enums inválidos (actualmente responde HTTP 500; debe responder HTTP 400).

---

## Pre-integración Local – Frontend · Java · Python

> **Objetivo:** validar de forma iterativa que el frontend funciona contra Spring Boot y FastAPI en local, sin copiar Angular a `backend-java/src/main/resources/static`, sin generar el JAR final y sin publicar cambios.

### Alcance y aislamiento

- [x] Trabajar exclusivamente en `../G9-LATAM-Team-57-integration`.
- [x] Mantener intactas las ramas `frontend` y `main`.
- [x] Identificar claramente cualquier cambio temporal de configuración.
- [x] No publicar ni hacer push de la rama de integración durante estas pruebas.

### Configuración de integración local

- [x] Configurar temporalmente `useMock=false` en `environment.integration.ts`.
- [x] Configurar un proxy Angular local hacia Spring Boot.
- [x] Mantener la API del navegador bajo `/analisis-energetico` mediante el origen Angular.
- [x] Mantener FastAPI interno en `localhost:8000` y accesible únicamente desde Java.
- [x] Levantar Angular, Spring Boot y FastAPI simultáneamente.

### Smoke test desde navegador

- [ ] Confirmar URL, método, headers y payload en DevTools.
- [x] Confirmar que el proxy Angular recibe HTTP 200 desde Java.
- [ ] Confirmar loading, categoría, probabilidad, costo y recomendaciones reales.
- [ ] Confirmar el botón `Nuevo análisis` después de una respuesta real.
- [ ] Confirmar que el navegador no llama directamente a FastAPI.

### Errores y resiliencia

- [ ] Probar validación frontend antes de enviar datos inválidos.
- [x] Probar HTTP 400 a través del proxy y recibir mensaje de validación.
- [x] Probar HTTP 500 a través del proxy.
- [ ] Probar HTTP 503 y timeout controlados.
- [x] Probar fallback Java cuando FastAPI no está disponible.
- [ ] Confirmar mensajes comprensibles y botón `Reintentar`.

### Cambios de contrato tras merge de `origin/main`

- [x] Adaptar `hasAc` de `number` (1/0) a `boolean` en request y formulario.
- [x] Añadir `simulado?: boolean` a la respuesta del frontend.
- [x] Adaptar el interceptor al nuevo cuerpo de error `{campo: mensaje}` y `{error}`.
- [x] Confirmar que `costoPorKwh` es opcional y el backend usa su default.
- [ ] Actualizar AIC/FFS para reflejar el nuevo request (`hasAc` booleano, `costoPorKwh`) y response (`simulado`).

### Hallazgos reportados al equipo

- [ ] Backend: los tests no compilan (`PredictControllerTest` y `MlModelClientImplTest` usan `ResultadoPrediccion` de forma incompatible).
- [ ] Backend: `consumoTotalMesAnterior=0` sigue devolviendo HTTP 200; el contrato exige `> 0`.
- [ ] Backend: ahora requiere variables de entorno `FASTAPI_BASE_URL`, `DIAS_MES`, `PRECIO_KWH`.
- [x] Backend: el enum inválido ya devuelve HTTP 400 (corregido en `main`).

### Iteración funcional y cierre

- [ ] Registrar cada hallazgo como ajuste frontend, backend o requisito del Sprint 6.
- [ ] Implementar únicamente ajustes necesarios para completar el flujo local.
- [ ] Repetir tests, ESLint y build después de cada ajuste.
- [ ] Revisar responsive y accesibilidad del flujo integrado.
- [ ] Restaurar o dejar identificados los cambios temporales antes del cierre.
- [ ] Emitir decisión Go/No-Go para iniciar el empaquetado del Sprint 6.

---

## Sprint 5.6 – Adecuación del Frontend al Nuevo Contrato Backend

> **Objetivo:** adaptar el frontend a los cambios de contrato introducidos en `main` (tarifa, fallback) y añadir las mejoras visuales acordadas: campo de tarifa, sección corporativa y estado en vivo de la API.

### 5.6.1 Contrato y estado compartido

- [x] Cambiar `hasAc` de `number` (1/0) a `boolean` en interfaz, formulario, HTML y mock.
- [x] Añadir `simulado?: boolean` a `EnergyAnalysisResponse`.
- [x] Adaptar el interceptor a los formatos de error `{campo: mensaje}` y `{error}`.
- [x] Crear `AnalysisStatusService` en `core/services/` con señal `'conectado' | 'fallback' | 'idle'`.

### 5.6.2 Formulario: tarifa y sección corporativa

- [x] Agregar `costoPorKwh?: number` a `EnergyAnalysisRequest`.
- [x] Agregar campo "Tarifa por kWh" (input number, default `0.75`).
- [x] Separar visualmente "Datos del hogar" de "Parámetros de la distribuidora".
- [x] Incluir `costoPorKwh` en la construcción del request.

### 5.6.3 Indicador de estado en vivo (navbar)

- [x] Mostrar `🟢 API CONECTADA` cuando `simulado=false`.
- [x] Mostrar `🔴 MODO FALLBACK` (titilante) cuando `simulado=true`.
- [x] Estado neutral al inicio y reset en "Nuevo análisis"/loading.
- [x] Accesible (`aria-live`) y responsive.

### 5.6.4 Pruebas

- [x] Test del formulario: mapeo `hasAc` boolean + `costoPorKwh`.
- [x] Test del servicio: respuesta con `simulado`.
- [x] Test del interceptor: `{campo: mensaje}` y `{error}`.
- [x] Test de `AnalysisStatusService`.
- [x] Ejecutar `ng test`, `eslint` y `ng build`.

### Criterio de Salida del Sprint 5.6

- [x] E2E local contra Java real: categoría, costo con tarifa, recomendaciones e indicador de estado.
- [x] Errores 400 con el nuevo formato visibles.

---

## Sprint 6 – Integración Angular · Spring Boot (MVP same-origin)

> **Decisión del equipo:** Angular se compila como contenido estático dentro de Spring Boot en `backend-java/src/main/resources/static`. La SPA y `POST /analisis-energetico` se publican bajo el mismo origen. Java es el único backend público y Python/ML permanece interno.
>
> **Objetivo:** entregar un único JAR Spring Boot con API + SPA, build reproducible, navegación Angular funcional y sin CORS en producción.
>
> **Alcance de esta iteración:** fases A–D (hasta JAR local validado). OCI/HTTPS queda documentado como fase E posterior.

### Fase A — Prerrequisitos Backend

- [x] Corregir `PredictControllerTest.java` para usar `ResultadoPrediccion` en el mock.
- [x] Corregir `MlModelClientImplTest.java` para usar `ResultadoPrediccion.response()`.
- [x] Cambiar `consumoTotalMesAnterior` de `@PositiveOrZero` a `@Positive`.
- [x] Eliminar los HTML demo de `backend-java/src/main/resources/static/`.
- [x] Implementar fallback SPA (`WebConfig`) reenviando rutas de cliente a `index.html`, excluyendo `/api/**`, `/analisis-energetico`, `/error`, `/v3/api-docs`, `/swagger-ui**`, `/actuator/**` y archivos con extensión.
- [x] Verificar que no quedan `System.out.println` en controller/client.

### Fase B — Build reproducible

- [x] Crear `build.sh` en la raíz: `npm ci` + `ng build --configuration production` → limpiar `static/` → copiar `dist/energiai/browser/*` → `./mvnw clean package`.
- [x] Verificar que el JAR contiene la SPA en `classpath:/static/`.

### Fase C — Adaptación final del Frontend

- [x] `environment.production.ts` con `apiUrl` relativo (`''`).
- [x] `EnergyAnalysisService` construye `/analisis-energetico` relativo cuando `apiUrl` está vacío.
- [x] Verificar `base href="/"` y recarga de `/analysis`.

### Fase D — Validación local del JAR

- [x] Arrancar el JAR con `FASTAPI_BASE_URL`, `DIAS_MES`, `PRECIO_KWH`.
- [x] `GET /` sirve Angular.
- [x] `GET /analysis` recarga → SPA (no 404).
- [x] `POST /analisis-energetico` desde navegador → 200 con categoría, costo, recomendaciones y `simulado`.
- [x] Errores 400/500/503/timeout y fallback (`MODO FALLBACK`).

### Fase E — OCI / Origen único HTTPS (pendiente, fuera de esta iteración)

- [ ] Dominio + DNS + TLS (Nginx/Caddy/LB) reenviando a `127.0.0.1:8080`.
- [ ] Restringir acceso público a `:8080`; Python solo en red interna `10.0.0.0/24`.
- [ ] `systemd` + health check.
- [ ] Actualizar `oci/README.md` y README raíz.

### Criterio de Salida (esta iteración)

- [x] `./build.sh` genera un JAR reproducible con la SPA.
- [x] El JAR sirve Angular + `/analisis-energetico` + `/api/v1/health` desde el mismo origen.
- [x] `/analysis` tras recarga funciona por fallback sin afectar rutas de API.
- [x] Producción no depende de CORS; desarrollo usa proxy local.
- [x] `useMock=false` funciona contra el JAR local, con indicador de estado conectado/fallback.

---

## Criterios de Aceptación Global (SRS + FFS + UI/UX)

- [ ] El usuario puede completar el formulario sin conocimiento técnico
- [ ] El sistema valida correctamente todos los campos
- [ ] El Backend recibe el JSON esperado según el contrato AIC
- [ ] La respuesta se muestra con categoría, probabilidad, costo y recomendaciones
- [ ] La interfaz es responsive (Desktop, Tablet, Mobile)
- [ ] Los errores son comprensibles para el usuario
- [ ] El usuario nunca visualiza términos internos (LOW, MEDIUM, HIGH, Pipeline, etc.)
- [ ] La experiencia transmite una solución moderna, simple y orientada a eficiencia energética
- [ ] Tiempo de respuesta percibido < 3 segundos (con spinner)

---

## Futuras Mejoras (Post-MVP)

- [ ] Dashboard con historial
- [ ] Autenticación de usuarios
- [ ] Comparación entre períodos
- [ ] Exportación PDF
- [ ] Procesamiento CSV masivo
- [ ] Modo oscuro
- [ ] Notificaciones
- [ ] Panel administrativo
- [ ] Gráficos con ngx-echarts

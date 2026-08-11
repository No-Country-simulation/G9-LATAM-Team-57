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
- [ x ] Crear proyecto Angular 20 con Standalone Components
- [ x ] Configurar SCSS como preprocesador de estilos
- [ x] Instalar y configurar Angular Material (tema personalizado con paleta verde/azul)
- [ x] Instalar dependencias adicionales: SweetAlert2, ngx-spinner, ngx-echarts (opcional)
- [ x ] Configurar ESLint + Prettier
- [ x ] Configurar Husky + lint-staged para pre-commit hooks
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

## Sprint 6 – Integración Angular · Spring Boot · OCI (MVP same-origin)

> **Decisión del equipo:** para el MVP, Angular se compilará como contenido estático de Spring Boot en `backend-java/src/main/resources/static`. La SPA y `POST /analisis-energetico` se publicarán bajo el mismo origen HTTPS. Java continuará como único backend público y Python/ML permanecerá interno.
>
> **Objetivo:** entregar un único artefacto Spring Boot que contenga API y SPA, con despliegue reproducible, navegación Angular funcional y sin dependencia de CORS en producción.

### Estado Auditado (2026-08-09)

- [x] Verificar API Java OCI: `GET http://163.176.43.143:8080/api/v1/health` responde `200 {"status":"UP"}`.
- [x] Verificar flujo Java → Python/ML: `POST /analisis-energetico` responde `200` con `fuenteDatos: "IA_PYTHON_REAL"`.
- [x] Definir la ruta canónica de análisis conforme a Java: `POST /analisis-energetico`.
- [x] Adoptar la arquitectura MVP same-origin: Angular estático dentro de Spring Boot; no habrá un subdominio público independiente para la API.

### Bloqueos y Decisiones Pendientes

- [ ] **Historias Git separadas:** `frontend` y `main` no tienen merge-base; la consolidación debe usar una rama desde `origin/main` y `git cherry-pick` selectivo.
- [ ] **Empaquetado ausente:** aún no existe un proceso automatizado que ejecute el build Angular, copie sus artefactos a `backend-java/src/main/resources/static` y empaquete después el JAR.
- [ ] **SPA fallback ausente:** Spring Boot debe servir `index.html` al recargar rutas Angular, por ejemplo `/analysis`, sin interceptar rutas de API.
- [ ] **Configuración frontend pendiente:** producción todavía depende de una URL absoluta; debe consumir `POST /analisis-energetico` como ruta relativa al mismo origen.
- [ ] **HTTPS pendiente:** el servicio operativo está expuesto por HTTP en `:8080`; falta dominio público y TLS válido para el origen único de la aplicación.
- [ ] **CORS solo afecta desarrollo separado:** el preflight desde `http://localhost:4200` hoy devuelve `403`. No bloqueará producción same-origin; para desarrollo se debe habilitar CORS limitado o configurar proxy Angular.

### 6.1 Consolidación del Repositorio (Equipo / Integración)

- [ ] Crear rama `integration/frontend-api` a partir de `origin/main`.
- [ ] Incorporar los commits de `frontend` mediante `git cherry-pick` selectivo, preservando la aplicación bajo `frontend/energiai`.
- [ ] Verificar que la rama de integración contiene `backend-java/`, `backend-python/`, `oci/` y `frontend/energiai/`.
- [ ] Documentar los comandos de build desde la raíz del repositorio consolidado.
- [ ] Evitar `git merge frontend` sin una revisión explícita de los historiales no relacionados.

### 6.2 Build y Empaquetado Angular dentro de Spring Boot (Equipo Frontend / Backend)

- [ ] Crear `backend-java/src/main/resources/static/` como destino de los artefactos Angular compilados.
- [ ] Identificar y documentar el directorio de salida real de `npx ng build` (`frontend/energiai/dist/energiai/` o su subdirectorio `browser/`, según la configuración activa).
- [ ] Definir un único script o paso de CI que, en este orden:
  1. [ ] Ejecute `npm ci` y `npx ng build` en `frontend/energiai`.
  2. [ ] Limpie los recursos estáticos generados previamente, sin borrar archivos de control necesarios.
  3. [ ] Copie el contenido del build Angular —`index.html`, JS, CSS y assets— a `backend-java/src/main/resources/static/`.
  4. [ ] Ejecute `./mvnw clean package` en `backend-java` después de la copia.
  5. [ ] Verifique que el JAR resultante contiene los recursos estáticos de Angular.
- [ ] No versionar `dist/` ni usar copia manual como mecanismo de despliegue; el empaquetado debe ser reproducible mediante script, Maven o CI.
- [ ] Definir cómo se limpian los artefactos generados al finalizar el build local para no contaminar cambios de código fuente.

### 6.3 Spring Boot: SPA y Contrato HTTP (Equipo Backend)

- [ ] Confirmar que Spring Boot sirve automáticamente `index.html` y los assets desde `classpath:/static/`.
- [ ] Implementar fallback de SPA para rutas de cliente, incluyendo `GET /analysis`, que reenvíe a `index.html`.
  - [ ] Excluir explícitamente `POST /analisis-energetico`, `GET /api/v1/health`, otras rutas `/api/**`, `/error` y archivos estáticos existentes.
  - [ ] Mantener los errores HTTP reales de API; el fallback nunca debe transformar un `404` de API en HTML.
- [ ] Mantener la ruta pública de análisis sin versión: `POST /analisis-energetico`.
- [ ] Mantener `GET /api/v1/health` como health check público.
- [ ] Cambiar la validación de `consumoTotalMesAnterior` de `@PositiveOrZero` a `@Positive`, conforme a AIC y frontend (`> 0`).
- [ ] Confirmar que `ErrorResponse.message` se conserva para que el interceptor frontend muestre errores comprensibles.
- [ ] Definir si `fuenteDatos` y `detalleFuente` son parte del contrato público.
  - [ ] Si se publican, documentarlos como campos opcionales en AIC/OpenAPI.
  - [ ] No exponer al usuario final causas técnicas del fallback de ML.
- [ ] Mantener CORS parametrizable solo para desarrollo separado (`http://localhost:4200`) o sustituirlo por un proxy Angular local. Producción same-origin no debe depender de CORS.
- [ ] Eliminar `System.out.println` de controller/client y usar logging estructurado.
- [ ] Actualizar Swagger/OpenAPI y colección Postman con la ruta, request, response y errores definitivos.

### 6.4 Adaptación Final del Frontend (Equipo Frontend)

- [x] Mover `useMock` a `environment` (`true` en desarrollo aislado, `false` en integración/producción).
- [x] Añadir `timeout(10_000)` al `HttpClient.post` para activar el manejo existente de timeout en el interceptor.
- [ ] Cambiar la construcción de URL de producción para invocar la ruta relativa `POST /analisis-energetico`; eliminar la dependencia de `api.energiai.cloud` y de cualquier URL absoluta para el análisis same-origin.
- [ ] Definir configuración de desarrollo:
  - [ ] Mantener mock aislado por defecto, o
  - [ ] Añadir `proxy.conf.json` para reenviar `/analisis-energetico` y `/api/v1` a `http://localhost:8080` sin requerir CORS durante desarrollo.
- [ ] Mantener headers `Content-Type: application/json` y `Accept: application/json` en la solicitud de análisis.
- [ ] Actualizar `EnergyAnalysisResponse` si Backend aprueba los metadatos opcionales de fuente/fallback.
- [ ] Desactivar el mock en el perfil de integración y ejecutar el flujo contra el JAR que sirve la SPA.
- [ ] Validar `ng build`, ESLint y pruebas unitarias antes de entregar los estáticos al empaquetado Java.

### 6.5 OCI: Origen Único HTTPS (Equipo Infraestructura / Backend)

- [ ] Elegir y registrar el dominio público final de la aplicación, sin separar un subdominio de API para este MVP.
- [ ] Configurar DNS del dominio hacia el ingreso de la VM Java en OCI.
- [ ] Configurar Nginx, Caddy u OCI Load Balancer con TLS válido en 443.
  - [ ] Reenviar todas las rutas públicas al Spring Boot local en `127.0.0.1:8080`, que servirá tanto SPA como API.
  - [ ] Conservar método, URI, headers necesarios y respuestas de error al realizar el proxy.
- [ ] Restringir el acceso público directo al puerto 8080 cuando el proxy HTTPS sea el ingreso oficial.
- [ ] Verificar que el servicio Java está administrado por `systemd`, arranca tras reinicio y expone health check.
- [ ] Mantener Python/ML restringido a la red interna OCI (`10.0.0.0/24`); el navegador nunca debe llamar al puerto 8000.
- [ ] Actualizar `oci/README.md` y el README raíz para reflejar Java desplegado, SPA incluida en el JAR y el flujo Java → Python validado.

### 6.6 Pruebas de Integración End-to-End (Todos)

- [ ] Ejecutar el pipeline de empaquetado desde un checkout limpio y verificar que produce un JAR repetible con la SPA.
- [ ] Arrancar el JAR localmente y comprobar `GET /` devuelve el `index.html` de Angular.
- [ ] Comprobar que los JS, CSS, assets y Material icons se cargan correctamente desde Spring Boot.
- [ ] Recargar directamente `GET /analysis` y confirmar que el fallback devuelve la SPA, no un 404.
- [ ] Probar `GET https://<dominio-app>/api/v1/health` y confirmar HTTP 200 con TLS válido.
- [ ] Desde el navegador, enviar el formulario y confirmar que el mismo origen realiza `POST /analisis-energetico` con HTTP 200, categoría, probabilidad, costo y recomendaciones.
- [ ] Confirmar en DevTools que el flujo de producción no requiere ni ejecuta preflight CORS para el análisis same-origin.
- [ ] Probar consumo `0` y demás datos inválidos; confirmar HTTP 400 y `ErrorResponse.message` amigable.
- [ ] Verificar que `useMock=false` muestra loading, resultado real y botón “Nuevo análisis”.
- [ ] Probar 404, 500, 503 y timeout; validar mensajes comprensibles y botón “Reintentar”.
- [ ] Probar fallback Java cuando el servicio ML no está disponible, sin mostrar detalles técnicos al usuario.
- [ ] Verificar que no hay contenido mixto HTTP bajo el dominio HTTPS final.
- [ ] Actualizar AIC, README raíz, documentación OCI, Postman y TODO con el proceso final.

### Criterio de Salida del Sprint 6

- [ ] Un build reproducible genera un JAR de Spring Boot que contiene la SPA Angular en `classpath:/static/`.
- [ ] Un único origen HTTPS sirve Angular y las rutas Java `POST /analisis-energetico` y `GET /api/v1/health`.
- [ ] Las rutas Angular, incluido `/analysis` tras recarga, funcionan mediante fallback SPA sin afectar las rutas de API.
- [ ] Producción no requiere CORS para que el navegador consuma el análisis; desarrollo separado cuenta con CORS restringido o proxy local documentado.
- [ ] El DTO enviado y la respuesta recibida coinciden con AIC/OpenAPI/Postman.
- [ ] `useMock=false` funciona desde navegador contra el JAR desplegado en OCI.
- [ ] Health check, navegación SPA y análisis válido están verificados con evidencias reproducibles.

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

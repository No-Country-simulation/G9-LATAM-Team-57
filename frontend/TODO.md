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
- [ ] Crear `NavbarComponent` (shared) – Logo + nombre "EnergIAi"
- [ ] Crear `FooterComponent` (shared) – Pie de página
- [ ] Configurar tipografía (Inter / Roboto)
- [ ] Definir variables SCSS globales (colores primario, secundario, error, advertencia, fondo)
- [ ] Implementar layout responsive base (Desktop ≥1200px, Tablet 768–1199px, Mobile <768px)

### Routing
- [ ] Configurar Angular Router con rutas por feature
- [ ] Ruta `/` → HomePageComponent
- [ ] Ruta `/analysis` → AnalysisPageComponent

---

## Sprint 2 – Formulario y Validaciones

### DTOs y Modelos
- [ ] Crear `EnergyAnalysisRequest` interface en `features/analysis/models/`
- [ ] Crear `EnergyAnalysisResponse` interface en `features/analysis/models/`
- [ ] Crear enums: `HousingType` (CASA, DEPARTAMENTO, MONOAMBIENTE)
- [ ] Crear enum: `PeakUsageLevel` (LOW, MEDIUM, HIGH)

### Formulario (EnergyFormComponent)
- [ ] Crear `EnergyFormComponent` con Reactive Forms
- [ ] Campo: Personas en la vivienda (Input Number, min: 1, obligatorio)
- [ ] Campo: Aire acondicionado (Radio Button: Sí/No → mapeo a 1/0)
- [ ] Campo: Home Office (Radio Button: Sí/No → mapeo a true/false)
- [ ] Campo: Tipo de vivienda (Select: Casa, Departamento, Monoambiente → CASA, DEPARTAMENTO, MONOAMBIENTE)
- [ ] Campo: Equipos eléctricos (Input Number, min: 0, obligatorio)
- [ ] Campo: Consumo último recibo (Input Number, min: >0, unidad kWh, obligatorio)
- [ ] Campo: Horas uso simultáneo (Radio Button: 0–2h, 3–5h, >5h → LOW, MEDIUM, HIGH)
- [ ] Implementar validaciones con Validators (required, min, max, pattern)
- [ ] Mostrar mensajes de error por campo (mat-error)
- [ ] Botón "Analizar consumo" deshabilitado si formulario inválido
- [ ] Agregar labels, placeholders y mensajes de ayuda por accesibilidad
- [ ] Implementar método de construcción del Request DTO desde el formulario

---

## Sprint 3 – Integración con Backend

### Servicios
- [ ] Crear `EnergyAnalysisService` en `features/analysis/services/`
  - Método `analyzeConsumption(request: EnergyAnalysisRequest): Observable<EnergyAnalysisResponse>`
  - Consumir `POST /api/v1/analisis-energetico`
  - Usar `HttpClient` con URL desde `environment.apiUrl`
- [ ] Crear `NotificationService` en `core/services/` (wrapper de SweetAlert2)
- [ ] Crear `LoadingService` en `core/services/` (administrar spinner global)

### Interceptor de Errores
- [ ] Crear `HttpErrorInterceptor` en `core/interceptors/`
  - Manejar 400: mostrar validaciones del backend
  - Manejar 404: servicio no encontrado
  - Manejar 500: error interno
  - Manejar 503: servicio no disponible
  - Manejar timeout: mensaje de conexión
- [ ] Registrar interceptor en la configuración de la app

### Signals (Estado)
- [ ] Implementar `loading = signal(false)` en el componente/servicio
- [ ] Implementar `result = signal<EnergyAnalysisResponse | null>(null)`
- [ ] Implementar `error = signal<string | null>(null)`

---

## Sprint 4 – Pantalla de Resultados

### Componentes de Resultado
- [ ] Crear `AnalysisPageComponent` (page container)
- [ ] Crear `ResultCardComponent` – Muestra categoría + probabilidad con indicador visual
  - 🟢 Eficiente (verde)
  - 🟡 Moderado (amarillo)
  - 🔴 Ineficiente (rojo)
  - Barra de progreso con porcentaje de probabilidad
- [ ] Crear `CostCardComponent` – Muestra costo estimado mensual (formato moneda)
- [ ] Crear `RecommendationCardComponent` – Lista de recomendaciones con íconos ✓
- [ ] Crear `ErrorComponent` – Tarjeta de error con mensaje + botón "Reintentar"
- [ ] Crear `LoadingComponent` – Spinner + mensaje "Analizando consumo energético..."

### Flujo Completo
- [ ] Integrar formulario → servicio → resultados en AnalysisPageComponent
- [ ] Implementar transición entre estados: formulario → loading → resultado/error
- [ ] Botón "Nuevo análisis" que resetea formulario y vuelve al estado inicial
- [ ] Prevenir envío múltiple de solicitudes simultáneas
- [ ] Animaciones: Fade In (200ms) entre pantallas

---

## Sprint 5 – Responsive, Optimización y Pruebas

### Responsive
- [ ] Verificar y ajustar formulario en Mobile (<768px)
- [ ] Verificar y ajustar resultados en Tablet (768–1199px)
- [ ] Verificar y ajustar layout general en Desktop (≥1200px)
- [ ] Testear navegación y usabilidad en todos los breakpoints

### Optimización
- [ ] Aplicar OnPush Change Detection en todos los componentes
- [ ] Verificar Lazy Loading para rutas futuras
- [ ] Minimizar re-renders con Signals
- [ ] Verificar bundle size

### Pruebas
- [ ] Verificar que el formulario se completa en menos de 2 minutos
- [ ] Verificar todas las validaciones funcionan correctamente
- [ ] Verificar integración con API (request JSON correcto)
- [ ] Verificar respuesta se muestra correctamente
- [ ] Verificar manejo de errores (400, 500, 503, timeout)
- [ ] Verificar responsive en dispositivos reales o DevTools
- [ ] Verificar accesibilidad (labels, keyboard navigation)

### Calidad de Código
- [ ] Verificar: no existen `console.log()`
- [ ] Verificar: no se usa `any`
- [ ] Verificar: todos los formularios usan Reactive Forms
- [ ] Verificar: componentes son Standalone
- [ ] Verificar: servicios consumen HttpClient (no fetch)
- [ ] Verificar: no hay URLs hardcodeadas
- [ ] Verificar: estructura del proyecto respetada
- [ ] Verificar: código formateado (ESLint + Prettier)

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

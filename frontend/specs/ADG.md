# Angular Coding Guidelines

## Proyecto
**EnergIAi – Inteligencia para el Consumo Energético**

**Versión:** 1.0  
**Framework:** Angular 20  
**Estado:** Draft

---

# Historial de Versiones

| Versión | Fecha | Autor | Descripción |
|---------|------|--------|-------------|
| 1.0 | Julio 2026 | Frontend Team | Primera versión |

---

# Tabla de Contenido

1. Objetivo
2. Principios Generales
3. Estructura del Proyecto
4. Convenciones de Nombres
5. Componentes
6. Servicios
7. DTOs y Modelos
8. Signals
9. Reactive Forms
10. HttpClient
11. Angular Material
12. Routing
13. Estilos
14. Manejo de Errores
15. Buenas Prácticas
16. Código Prohibido
17. Checklist de Pull Request

---

# 1. Objetivo

Este documento establece los estándares de desarrollo para el Frontend Angular del proyecto EnergIAi.

Todos los desarrolladores deberán seguir estas convenciones para garantizar consistencia, mantenibilidad y escalabilidad.

---

# 2. Principios Generales

El proyecto seguirá los siguientes principios:

- SOLID
- Separation of Concerns
- Single Responsibility Principle
- Clean Code
- Component First
- Feature Based Architecture

---

# 3. Estructura del Proyecto

```

src/

└── app/

├── core/

├── features/

├── shared/

├── assets/

├── environments/

└── styles/

```

Cada funcionalidad deberá vivir dentro de **features/**.

Ejemplo

```

features/

analysis/

home/

dashboard/

```

Nunca colocar componentes de negocio dentro de **shared/**.

---

# 4. Convenciones de Nombres

## Componentes

PascalCase

Ejemplo

```

EnergyFormComponent

ResultCardComponent

```

Archivos

```

energy-form.component.ts

result-card.component.ts

```

---

## Servicios

Todos terminarán en

```

Service

```

Ejemplo

```

EnergyAnalysisService

NotificationService

```

---

## Interfaces

No utilizar prefijo "I".

Correcto

```

EnergyAnalysisRequest

EnergyAnalysisResponse

```

Incorrecto

```

IEnergyAnalysisRequest

```

---

## Variables

camelCase

Correcto

```

householdSize

equipmentCount

loadingSignal

```

---

## Constantes

UPPER_SNAKE_CASE

```

DEFAULT_TIMEOUT

MAX_EQUIPMENT

```

---

# 5. Componentes

Todos los componentes serán **Standalone Components**.

Ejemplo

```typescript
@Component({
  standalone: true
})
```

No crear nuevos NgModules para funcionalidades.

Cada componente debe tener una única responsabilidad.

Ejemplo

✔ EnergyFormComponent

✔ ResultCardComponent

✔ LoadingComponent

✘ AnalysisComponent con 1000 líneas de código.

---

# 6. Servicios

Toda comunicación HTTP deberá realizarse desde Services.

Nunca desde Components.

Correcto

```

Component

↓

Service

↓

Backend

```

Incorrecto

```

Component

↓

HttpClient

```

Los servicios deberán utilizar `inject()` en lugar de constructor cuando sea apropiado.

Ejemplo

```typescript
private http = inject(HttpClient);
```

---

# 7. DTOs y Modelos

Todos los DTOs deberán ubicarse en

```

features/analysis/models

```

Ejemplo

```typescript
export interface EnergyAnalysisRequest {

householdSize:integer;

hasAc:integer;

homeOffice:boolean;

housingType:string (enum);

equipmentCount:integer;

energyConsumptionKwh:double;

peakUsageLevel:string. (enum);

}
```

Nunca utilizar `any`.

---

# 8. Signals

Angular Signals será el mecanismo principal para el estado local.

Ejemplo

```typescript
loading = signal(false);

result = signal<EnergyAnalysisResponse | null>(null);

error = signal<string | null>(null);
```

No utilizar BehaviorSubject para estado local.

Signals deberán utilizarse para:

- Loading
- Resultado
- Error
- Estado visual

---

# 9. Reactive Forms

Todos los formularios deberán implementarse con Reactive Forms.

No utilizar Template Forms.

Ejemplo

```typescript
form = this.fb.group({

householdSize:[1,[Validators.required]],

equipmentCount:[0,[Validators.required]]

});
```

Las validaciones deberán definirse dentro del formulario.

Nunca en el HTML.

---

# 10. HttpClient

Toda llamada HTTP deberá realizarse mediante HttpClient.

No utilizar fetch().

Los endpoints deberán centralizarse.

Ejemplo

```typescript
private readonly api =
environment.apiUrl;
```

Nunca escribir URLs directamente.

Incorrecto

```

http://localhost:8080

```

---

# 11. Angular Material

Todos los componentes visuales deberán utilizar Angular Material.

Ejemplos

- MatCard
- MatButton
- MatInput
- MatSelect
- MatRadio
- MatProgressSpinner
- MatSnackBar
- MatToolbar

No mezclar Angular Material con Bootstrap.

---

# 12. Routing

El routing deberá organizarse por feature.

Ejemplo

```

analysis.routes.ts

home.routes.ts

```

No colocar todas las rutas en un único archivo.

---

# 13. Estilos

Se utilizará SCSS.

No utilizar estilos inline.

Correcto

```

energy-form.component.scss

```

Incorrecto

```html
<div style="color:red">
```

Los colores deberán obtenerse del Design System.

---

# 14. Manejo de Errores

Todos los errores HTTP serán interceptados mediante un HttpInterceptor.

El componente no deberá conocer códigos HTTP.

Correcto

```

Component

↓

Service

↓

Interceptor

↓

Backend

```

---

# 15. Buenas Prácticas

✔ Componentes pequeños.

✔ Servicios reutilizables.

✔ DTOs tipados.

✔ Signals para estado.

✔ Reactive Forms.

✔ Código documentado cuando sea necesario.

✔ Funciones cortas.

✔ Máximo 40 líneas por método.

✔ Máximo 250 líneas por componente.

---

# 16. Código Prohibido

❌ any

❌ Template Forms

❌ Lógica HTTP en Components

❌ Variables globales

❌ Duplicación de código

❌ Magic Numbers

❌ URLs hardcodeadas

❌ Estilos inline

❌ Comentarios innecesarios

---

# 17. Checklist para Pull Request

Antes de crear un Pull Request verificar:

- [ ] El código compila sin errores.
- [ ] No existen `console.log()`.
- [ ] No se utiliza `any`.
- [ ] Todos los formularios usan Reactive Forms.
- [ ] Los componentes son Standalone.
- [ ] Los servicios consumen HttpClient.
- [ ] Se utilizan Signals cuando corresponde.
- [ ] No existen URLs hardcodeadas.
- [ ] Se respetó la estructura del proyecto.
- [ ] Se agregaron tipos a todos los DTOs.
- [ ] El código fue formateado correctamente.

---

# Anexo A – Flujo recomendado

```

Usuario

↓

Component

↓

Reactive Form

↓

Service

↓

HttpClient

↓

Spring Boot

↓

DTO

↓

Signal

↓

UI

```

---

# Anexo B – Principios del Proyecto

El Frontend deberá cumplir las siguientes reglas:

- No implementar lógica del modelo de Machine Learning.
- No calcular reglas de negocio.
- No conocer el funcionamiento interno del Pipeline.
- Consumir exclusivamente la API Spring Boot.
- Mantener tipado estricto en toda la aplicación.
- Favorecer componentes reutilizables y desacoplados.

---

# Anexo C – ESLINT + PRETTIER

No dejaría el formato del código a criterio de cada desarrollador. Configuraría:
- ESLint para reglas de calidad.
- Prettier para formato automático.
- Husky + lint-staged para ejecutar estas validaciones antes de cada commit.

Así el código mantiene un estilo uniforme sin depender de revisiones manuales.

---

# Anexo D – CONVENTIONAL COMMITS

Definiría un estándar para los mensajes de commit. Por ejemplo:
feat(frontend): add energy analysis form

fix(api): handle timeout response

refactor(shared): simplify loading component

docs(ui): update coding guidelines

style(layout): improve responsive navbar
Esto hace que el historial del repositorio sea mucho más claro y profesional, especialmente cuando varias personas trabajan sobre el mismo proyecto.


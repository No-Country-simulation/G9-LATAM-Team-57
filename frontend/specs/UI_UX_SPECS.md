# UI/UX Specification
## Proyecto: EnergIAi – Inteligencia para el Consumo Energético

**Versión:** 1.0  
**Estado:** Draft  
**Responsable:** Frontend Team  
**Fecha:** Julio 2026

---

# 1. Objetivo

Este documento define la experiencia de usuario (UX) y la interfaz de usuario (UI) del proyecto EnergIAi.

Su finalidad es establecer una experiencia intuitiva para que cualquier usuario pueda analizar el consumo energético de su vivienda sin conocimientos técnicos.

No define lógica de negocio ni funcionamiento del modelo de Machine Learning.

---

# 2. Principios de Diseño

La interfaz deberá cumplir los siguientes principios:

- Simplicidad.
- Claridad.
- Accesibilidad.
- Rapidez.
- Diseño Responsive.
- Enfoque Mobile First.
- Lenguaje amigable.

El usuario nunca deberá conocer conceptos internos como:

- Machine Learning
- Logistic Regression
- Pipeline
- One Hot Encoding
- LOW
- MEDIUM
- HIGH

---

# 3. Perfil del Usuario

## Usuario objetivo

Personas que desean conocer si su consumo energético es eficiente.

No se requiere conocimiento técnico.

---

# 4. Flujo General

```

Landing

↓

Formulario

↓

Validación

↓

Análisis

↓

Resultados

↓

Nuevo análisis

```

---

# 5. Mapa de Pantallas

```

+----------------------+
| Landing              |
+----------+-----------+
           |
           ▼
+----------------------+
| Formulario           |
+----------+-----------+
           |
           ▼
+----------------------+
| Analizando...        |
+----------+-----------+
           |
           ▼
+----------------------+
| Resultado            |
+----------+-----------+
           |
           ▼
+----------------------+
| Nuevo análisis       |
+----------------------+

```

---

# 6. Landing Page

## Objetivo

Explicar el propósito del sistema.

## Componentes

### Navbar

- Logo
- Nombre EnergIAi

---

### Hero

Título

> Analiza tu consumo energético en segundos

Descripción

Conoce el nivel de eficiencia energética de tu vivienda y recibe recomendaciones para ahorrar energía.

---

### CTA

Botón

```
Comenzar análisis
```

---

### Beneficios

Tres tarjetas

⚡ Analiza tu consumo

🌱 Reduce desperdicios

💰 Estima costos

---

# 7. Pantalla de Formulario

## Objetivo

Recolectar información necesaria para el análisis.

---

## Distribución

```

+--------------------------------------+

Información General

--------------------------------------

Personas

[____]

Tipo de vivienda

[v]

Aire acondicionado

( ) Sí

( ) No

Home Office

( ) Sí

( ) No

Equipos eléctricos

[____]

Consumo del último recibo

[____] kWh

Horas de uso simultáneo

( ) 0–2

( ) 3–5

( ) Más de 5

--------------------------------------

[ Analizar consumo ]

+--------------------------------------+

```

---

# 8. Componentes

## Personas

Tipo

Input Number

Placeholder

```
Ejemplo: 4
```

---

## Aire acondicionado

Tipo

Radio Button

Opciones

Sí

No

---

## Home Office

Tipo

Radio Button

---

## Tipo de vivienda

Tipo

Select

Opciones

Casa

Departamento

Monoambiente

---

## Equipos

Tipo

Input Number

Ayuda

Incluya televisores, computadoras, refrigeradora, microondas, lavadora, etc.

---

## Consumo

Tipo

Input Number

Placeholder

```
420
```

Ayuda

Ingrese el consumo total indicado en su último recibo eléctrico.

---

## Horas simultáneas

Tipo

Radio Button

Opciones

○ 0–2 horas

○ 3–5 horas

○ Más de 5 horas

El usuario nunca visualizará

LOW

MEDIUM

HIGH

---

# 9. Validaciones

## Personas

Debe ser mayor que cero.

---

## Equipos

No puede ser negativo.

---

## Consumo

Debe ser mayor que cero.

---

## Tipo vivienda

Obligatorio.

---

## Horas

Obligatorio.

---

## Botón Analizar

Permanecerá deshabilitado mientras existan errores.

---

# 10. Pantalla de Carga

Mostrar

```
🔄 Analizando consumo energético...
```

Spinner.

Tiempo esperado

1–3 segundos.

El usuario no podrá enviar múltiples solicitudes simultáneamente.

---

# 11. Pantalla de Resultados

## Diseño

```

+--------------------------------------+

Resultado del análisis

--------------------------------------

Categoría

Moderado

████████░░

82 %

--------------------------------------

Costo estimado

315 USD

--------------------------------------

Recomendaciones

✓ Reducir uso en horario pico

✓ Revisar equipos antiguos

✓ Distribuir actividades

--------------------------------------

[ Nuevo análisis ]

+--------------------------------------+

```

---

# 12. Indicadores Visuales

## Categoría

Eficiente

🟢

---

Moderado

🟡

---

Ineficiente

🔴

---

# 13. Estados

## Inicial

Formulario vacío.

---

## Loading

Spinner.

---

## Success

Resultados.

---

## Error

Tarjeta de error.

Mensaje

```
No fue posible realizar el análisis.
Intente nuevamente.
```

Botón

```
Reintentar
```

---

# 14. Responsive

La aplicación deberá funcionar correctamente en:

Desktop

Tablet

Mobile

---

# 15. Accesibilidad

Todos los campos deberán tener:

- Label.
- Placeholder.
- Mensaje de ayuda.
- Mensaje de error.

Los botones deberán ser accesibles mediante teclado.

---

# 16. Paleta de Colores

## Primario

Verde

Representa sostenibilidad.

---

## Secundario

Azul

Representa tecnología.

---

## Error

Rojo.

---

## Advertencia

Amarillo.

---

## Fondo

Blanco o Gris muy claro.

---

# 17. Tipografía

Fuente recomendada

Inter

Alternativa

Roboto

---

# 18. Iconografía

(Alguna libreria que sea comun en el desarrollo en el ecosistema de angular)

Íconos principales

Home

Zap

Leaf

DollarSign

BarChart3

Lightbulb

AlertTriangle

CheckCircle

LoaderCircle

---

# 19. Animaciones

Transición entre pantallas.

Fade In.

Duración

200 ms.

Botones con efecto Hover.

Cards con sombra ligera.

---

# 20. Tecnologías

Angular 20

TypeScript

Angular Material

Angular Signals

Reactive Forms

HttpClient

RxJS

SCSS

Angular Router

ngx-echarts (gráficos)

SweetAlert2

ngx-spinner

# 21. Futuras Mejoras

Dashboard.

Historial.

Comparación mensual.

Exportación PDF.

CSV.

Modo oscuro.

Autenticación.

Notificaciones.

Panel administrativo.

---

# 22. Criterios de Aceptación

✔ El usuario comprende todas las preguntas sin ayuda técnica.

✔ El formulario puede completarse en menos de 2 minutos.

✔ Los resultados son claros y fáciles de interpretar.

✔ La interfaz es responsive.

✔ La navegación requiere el menor número posible de clics.

✔ Los errores se muestran de forma comprensible.

✔ La experiencia transmite una solución moderna, simple y orientada a la eficiencia energética.
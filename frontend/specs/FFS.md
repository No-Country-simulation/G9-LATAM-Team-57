# Frontend Functional Specification (FFS)
## Proyecto: EnergIAi – Inteligencia para el Consumo Energético

**Versión:** 1.0
**Estado:** Draft
**Responsable:** Frontend Team
**Fecha:** Julio 2026

---

# 1. Objetivo

Este documento define la especificación funcional del Frontend del proyecto EnergIAi.

Su propósito es establecer un contrato funcional entre Frontend, Backend (Spring Boot) y Ciencia de Datos, definiendo:

- Información solicitada al usuario.
- Validaciones.
- Flujo de navegación.
- Transformación hacia el contrato REST.
- Presentación de resultados.

Este documento NO define la lógica interna del modelo de Machine Learning.

---

# 2. Arquitectura Funcional

```
Usuario
    │
    ▼
Frontend
    │
    ▼
Spring Boot API
    │
    ▼
FastAPI ML Service
    │
    ▼
Pipeline + Modelo
```

El Frontend únicamente interactúa con la API REST de Spring Boot.

Nunca se comunica directamente con el servicio de Machine Learning.

---

# 3. Flujo del Usuario

```
Inicio

↓

Formulario de análisis

↓

Validación

↓

POST /analisis-energetico

↓

Esperando respuesta...

↓

Resultados

↓

Nuevo análisis
```

---

# 4. Requerimientos Funcionales

## RF-01

El sistema deberá permitir ingresar la información energética de una vivienda.

---

## RF-02

El sistema deberá validar la información antes de enviarla.

---

## RF-03

El sistema deberá consumir el endpoint

POST /analisis-energetico

---

## RF-04

El sistema deberá mostrar

- Categoría energética
- Probabilidad
- Costo estimado
- Recomendaciones

---

## RF-05

El sistema deberá mostrar errores provenientes del backend.

---

## RF-06

El sistema deberá mostrar un indicador de carga mientras se procesa el análisis.

---

## RF-07

El usuario podrá realizar múltiples análisis sin recargar la aplicación.

---

# 5. Formulario

## Campo 1

### Personas en la vivienda

Tipo

Number

Pregunta

¿Cuántas personas viven en la vivienda?

Validaciones

- Obligatorio
- Entero positivo
- Mínimo 1

DTO

householdSize

---

## Campo 2

### Aire acondicionado

Tipo

Radio Button

Pregunta

¿La vivienda cuenta con aire acondicionado?

Opciones

- Sí
- No

DTO

hasAc

Valores enviados

1 (true)
0 (false)

---

## Campo 3

### Home Office

Tipo

Radio Button

Pregunta

¿Algún integrante trabaja regularmente desde casa?

Opciones

- Sí
- No

DTO

homeOffice

---

## Campo 4

### Tipo de vivienda

Tipo

Select

Opciones

Casa

Departamento

Monoambiente

DTO

housingType

Valores enviados

CASA

DEPARTAMENTO

MONOAMBIENTE

---

## Campo 5

### Equipos eléctricos

Tipo

Number

Pregunta

¿Cuántos equipos eléctricos utiliza frecuentemente la vivienda?

Ejemplos

- Refrigeradora
- Televisor
- Microondas
- Lavadora
- Aire acondicionado
- Computadoras
- Etc.

DTO

equipmentCount

---

## Campo 6

### Consumo del último recibo

Tipo

Number

Pregunta

Ingrese el consumo total indicado en su último recibo eléctrico.

Unidad

kWh

DTO

consumoTotalMesAnterior

---

## Campo 7

### Uso simultáneo de equipos

Tipo

Radio Button

Pregunta

¿Cuántas horas al día utiliza varios equipos eléctricos al mismo tiempo?

Opciones

○ 0–2 horas

○ 3–5 horas

○ Más de 5 horas

Mapeo

0–2 horas

↓

LOW

3–5 horas

↓

MEDIUM

Más de 5 horas

↓

HIGH

DTO

peakUsageLevel

---

# 6. JSON enviado al Backend

```json
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

---

# 7. Responsabilidades

## Frontend

Responsable de

- Mostrar el formulario.
- Validar campos.
- Transformar Peak Usage.
- Consumir la API.
- Mostrar resultados.

No conoce

- Machine Learning
- OneHotEncoder
- Pipeline
- Modelo

---

## Backend

Responsable de

- Validar negocio.
- Calcular consumo promedio diario.
- Consumir FastAPI.
- Calcular costo.
- Generar respuesta final.

---

## Machine Learning

Responsable de

- Cargar Pipeline.
- Ejecutar inferencia.
- Devolver probabilidades.

---

# 8. Estados de la Interfaz

## Estado inicial

Formulario vacío.

---

## Estado cargando

Spinner.

Mensaje

Analizando consumo energético...

---

## Estado éxito

Mostrar

Categoría

Probabilidad

Costo

Recomendaciones

Botón

Nuevo análisis

---

## Estado error

Mostrar mensaje recibido por la API.

Ejemplo

No fue posible analizar el consumo.

---

# 9. Diseño de Resultados

## Categoría

Ejemplo

Moderado

---

## Probabilidad

82 %

---

## Costo estimado

315 USD

---

## Recomendaciones

✓ Reducir consumo en horario pico.

✓ Revisar equipos antiguos.

✓ Distribuir actividades de alto consumo.

---

# 10. Validaciones

| Campo | Regla |
|---------|--------|
| householdSize | >=1 |
| equipmentCount | >=0 |
| consumoTotalMesAnterior | >0 |
| housingType | Obligatorio |
| peakUsageLevel | Obligatorio |

---

# 11. Consideraciones de UX

El usuario nunca visualizará términos internos del modelo como:

- LOW
- MEDIUM
- HIGH
- OneHotEncoder
- Pipeline
- Logistic Regression

La interfaz utilizará preguntas naturales y realizará internamente el mapeo hacia el contrato definido por el Backend.

---

# 12. Dependencias

## Backend

Debe mantener estable el contrato REST.

---

## Data Science

Debe entregar un Pipeline serializado compatible con el contrato definido.

En caso de que únicamente se entregue el modelo entrenado, el preprocesamiento será responsabilidad del servicio FastAPI, manteniendo inalterado el contrato entre Frontend y Backend.

---

# 13. Pendientes por resolver

- Definir el rango máximo permitido para `equipmentCount`. (implementar un rango logico)
- Definir el rango máximo permitido para `consumoTotalMesAnterior` (implementar un rango logico).
- Confirmar el contenido final del artefacto `.pkl` (Pipeline completo o solo modelo).
- Definir los mensajes de error estándar de la API (implementar unos mensajes logicos).
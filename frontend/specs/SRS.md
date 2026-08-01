# Software Requirements Specification (SRS)

## Proyecto
**EnergIAi – Inteligencia para el Consumo Energético**

---

# Historial de versiones

| Versión | Fecha | Autor | Descripción |
|---------|------|--------|-------------|
| 1.0 | Julio 2026 | Equipo EnergIAi | Primera versión |

---

# Tabla de contenido

1. Introducción
2. Descripción General
3. Stakeholders
4. Alcance del Sistema
5. Arquitectura General
6. Casos de Uso
7. Requisitos Funcionales
8. Requisitos No Funcionales
9. Reglas de Negocio
10. Requisitos de Integración
11. Restricciones
12. Supuestos
13. Criterios de Aceptación
14. Glosario

---

# 1. Introducción

## 1.1 Propósito

Este documento define los requisitos funcionales y no funcionales del sistema **EnergIAi**, una plataforma inteligente para el análisis del consumo energético residencial mediante técnicas de Ciencia de Datos y Machine Learning.

El propósito del sistema es permitir que los usuarios ingresen información relacionada con el consumo energético de una vivienda y reciban un análisis sobre su nivel de eficiencia, recomendaciones de optimización y una estimación del costo energético.

Este documento constituye la referencia funcional para los equipos de Frontend, Backend y Ciencia de Datos.

---

## 1.2 Alcance

El sistema permitirá:

- Registrar información de consumo energético.
- Analizar el perfil energético de una vivienda.
- Clasificar el nivel de eficiencia energética.
- Generar recomendaciones personalizadas.
- Estimar el costo energético mensual.
- Mostrar los resultados mediante una interfaz web.
- Consumir una API REST desarrollada en Spring Boot.

---

## 1.3 Definiciones

| Término | Definición |
|----------|------------|
| API | Interfaz de Programación de Aplicaciones |
| ML | Machine Learning |
| OCI | Oracle Cloud Infrastructure |
| DTO | Data Transfer Object |
| Pipeline | Flujo serializado de preprocesamiento + modelo |
| kWh | Kilovatio-hora |

---

# 2. Descripción General

## Objetivo del Sistema

EnergIAi ayuda a los usuarios a comprender su consumo energético mediante Inteligencia Artificial.

El sistema analiza información de una vivienda y devuelve:

- Categoría energética.
- Probabilidad.
- Recomendaciones.
- Estimación financiera.

---

## Actores

### Usuario

Persona interesada en conocer la eficiencia energética de su vivienda.

---

### Backend

API Spring Boot encargada de validar, orquestar y procesar la solicitud.

---

### Servicio ML

Servicio FastAPI responsable de ejecutar la inferencia del modelo.

---

# 3. Stakeholders

| Stakeholder | Responsabilidad |
|--------------|----------------|
| Usuario | Utilizar la plataforma |
| Frontend | Interfaz gráfica |
| Backend | API REST |
| Data Science | Modelo ML |
| Oracle Cloud | Infraestructura |

---

# 4. Alcance Funcional

El MVP incluirá:

- Formulario de ingreso.
- Validaciones.
- Consumo de API REST.
- Resultados.
- Recomendaciones.
- Estimación financiera.

No incluirá:

- Autenticación.
- Usuarios.
- Historial.
- Dashboard.
- Exportación PDF.
- Procesamiento masivo.

---

# 5. Arquitectura General

```

Usuario

↓

Angular

↓

Spring Boot

↓

FastAPI

↓

Pipeline ML

```

---

# 6. Casos de Uso

## UC-01

Analizar consumo energético.

Actor

Usuario.

Flujo principal

1. Ingresa información.
2. Presiona Analizar.
3. Sistema valida.
4. Sistema consulta API.
5. Sistema muestra resultados.

---

# 7. Requisitos Funcionales

## RF-001

El sistema deberá permitir registrar la información energética de una vivienda.

---

## RF-002

El sistema deberá validar los datos ingresados antes de enviarlos.

---

## RF-003

El sistema deberá consumir el endpoint

POST /analisis-energetico

---

## RF-004

El sistema deberá mostrar la categoría energética obtenida.

---

## RF-005

El sistema deberá mostrar la probabilidad asociada a la clasificación.

---

## RF-006

El sistema deberá mostrar las recomendaciones enviadas por la API.

---

## RF-007

El sistema deberá mostrar el costo estimado mensual.

---

## RF-008

El sistema deberá permitir realizar un nuevo análisis.

---

## RF-009

El sistema deberá mostrar mensajes de error provenientes del backend.

---

## RF-010

El sistema deberá mostrar un indicador de carga durante el procesamiento.

---

# 8. Requisitos No Funcionales

## RNF-001

La aplicación deberá ser responsive.

---

## RNF-002

La interfaz deberá funcionar correctamente en:

- Desktop
- Tablet
- Mobile

---

## RNF-003

El tiempo de respuesta esperado será inferior a 3 segundos (dependiendo de la API).

---

## RNF-004

La aplicación deberá consumir exclusivamente la API Spring Boot.

---

## RNF-005

La aplicación no deberá comunicarse directamente con el servicio FastAPI.

---

## RNF-006

El código deberá desarrollarse utilizando Angular.

---

## RNF-007

La interfaz deberá ser intuitiva para usuarios sin conocimientos técnicos.

---

## RNF-008

Los formularios deberán utilizar Reactive Forms.

---

## RNF-009

La aplicación deberá implementar validaciones tanto visuales como funcionales.

---

## RNF-010

Los errores deberán mostrarse mediante mensajes claros para el usuario.

---

# 9. Reglas de Negocio

## RN-001

El usuario ingresará el consumo correspondiente al último recibo eléctrico.

---

## RN-002

El Backend calculará el consumo promedio diario.

---

## RN-003

La categoría energética será calculada exclusivamente por el modelo ML.

---

## RN-004

El costo estimado será calculado utilizando la tarifa configurada por el Backend.

---

## RN-005

El usuario nunca visualizará variables internas del modelo.

Ejemplo

LOW

MEDIUM

HIGH

---

## RN-006

Las horas de uso simultáneo serán transformadas de la siguiente manera:

0–2 horas

↓

LOW

3–5 horas

↓

MEDIUM

Más de 5 horas

↓

HIGH

---

# 10. Requisitos de Integración

Frontend

↓

Spring Boot

↓

JSON

Formato

application/json

Método

POST

Endpoint

/analisis-energetico

---

# 11. Restricciones

- Angular como framework Frontend.
- Spring Boot como Backend.
- FastAPI como servicio ML.
- OCI como infraestructura.
- JSON como formato de intercambio.

---

# 12. Supuestos

- El Backend expondrá un contrato REST estable.
- El Pipeline ML será compatible con el contrato definido.
- La API estará disponible durante el análisis.

---

# 13. Criterios de Aceptación

✔ El usuario puede completar el formulario.

✔ El sistema valida correctamente.

✔ El Backend recibe el JSON esperado.

✔ La respuesta se muestra correctamente.

✔ La interfaz es responsive.

✔ Los errores son comprensibles.

---

# 14. Glosario

| Concepto | Descripción |
|-----------|-------------|
| Categoría | Resultado del modelo ML |
| Probabilidad | Confianza del modelo |
| Pipeline | Preprocesamiento + Modelo |
| Consumo | Energía utilizada durante el último período |


# 15. Data Dictionary (Diccionario de Datos)
| Campo UI                | Campo API                 | Tipo    | Obligatorio | Ejemplo | Regla              |
| ----------------------- | ------------------------- | ------- | ----------- | ------- | ------------------ |
| Personas en la vivienda | `householdSize`           | Integer | Sí          | 4       | > 0                |
| Aire acondicionado      | `hasAc`                   | Integer | Sí          | 1   | Sí/No              |
| Home Office             | `homeOffice`              | Boolean | Sí          | `false` | Sí/No              |
| Tipo de vivienda        | `housingType`             | Enum    | Sí          | `CASA`  | Valores permitidos |
| Equipos eléctricos      | `equipmentCount`          | Integer | Sí          | 10      | ≥ 0                |
| Consumo último recibo   | `consumoTotalMesAnterior` | Double  | Sí          | 420.5   | > 0                |
| Horas de uso simultáneo | `peakUsageLevel`          | Enum    | Sí          | `HIGH`  | Mapeo 0–2, 3–5, >5 |

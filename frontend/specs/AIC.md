# API Integration Contract (AIC)

## Proyecto
**EnergIAi – Inteligencia para el Consumo Energético**

**Versión:** 1.0  
**Estado:** Draft  
**Responsables:** Frontend Team · Backend Team

---

# Historial de Versiones

| Versión | Fecha | Autor | Descripción |
|---------|------|--------|-------------|
| 1.0 | Julio 2026 | Equipo EnergIAi | Primera versión |

---

# Tabla de Contenido

1. Objetivo
2. Alcance
3. Arquitectura de Integración
4. Convenciones Generales
5. Endpoint de Análisis
6. Request DTO
7. Response DTO
8. Códigos HTTP
9. Errores
10. Reglas de Validación
11. Mapeo Frontend → Backend
12. Responsabilidades
13. Versionado
14. Casos de Prueba

---

# 1. Objetivo

Este documento define el contrato de integración entre el Frontend desarrollado en Angular y la API REST desarrollada en Spring Boot.

Su objetivo es garantizar que ambos equipos trabajen con exactamente la misma estructura de datos.

---

# 2. Alcance

Este documento únicamente cubre la comunicación entre:

Angular

↓

Spring Boot

No contempla la comunicación entre Spring Boot y FastAPI.

---

# 3. Arquitectura

```

Usuario

↓

Angular

↓

Spring Boot REST API

↓

FastAPI

↓

Pipeline ML

```

Angular nunca consumirá directamente FastAPI.

---

# 4. Convenciones Generales

## Protocolo

HTTPS

---

## Formato

application/json

---

## Codificación

UTF-8

---

## Método

POST

---

## Endpoint

```

POST /analisis-energetico

```

---

## Headers

```http
Content-Type: application/json
Accept: application/json
```

---

# 5. Endpoint

## POST /analisis-energetico

Descripción

Analiza el perfil energético de una vivienda y devuelve la clasificación, recomendaciones y costo estimado.

---

# 6. Request DTO

## JSON

```json
{
  "householdSize": 4,
  "hasAc": 1,
  "homeOffice": false,
  "housingType": "CASA",
  "equipmentCount": 10,
  "consumoTotalMesAnterior": 420,
  "peakUsageLevel": "HIGH"
}
```

---

## Definición de Campos

| Campo | Tipo | Obligatorio | Regla | Ejemplo |
|--------|------|-------------|--------|----------|
| householdSize | Integer | Sí | >= 1 | 4 |
| hasAc | Integer | Sí | 0/1 | 1 = true |
| homeOffice | Boolean | Sí | true/false | false |
| housingType | Enum | Sí | CASA, DEPARTAMENTO, MONOAMBIENTE | CASA |
| equipmentCount | Integer | Sí | >= 0 | 10 |
| consumoTotalMesAnterior | Double | Sí | > 0 | 420 |
| peakUsageLevel | Enum | Sí | LOW, MEDIUM, HIGH | HIGH |

---

## Valores Permitidos

### housingType

```
CASA
DEPARTAMENTO
MONOAMBIENTE
```

---

### peakUsageLevel

```
LOW
MEDIUM
HIGH
```

---

# 7. Response DTO

## JSON

> Puede estar pendiente a cambios....

```json
{
  "categoria": "Moderado",
  "probabilidad": 0.82,
  "costoEstimadoMensual": 315.00,
  "recomendaciones": [
    "Reducir el uso de equipos durante horarios pico.",
    "Evaluar equipos de alto consumo.",
    "Distribuir actividades de mayor consumo."
  ]
}
```

---

## Campos

| Campo | Tipo | Descripción |
|---------|------|-------------|
| categoria | String | Categoría energética |
| probabilidad | Double | Probabilidad de clasificación |
| costoEstimadoMensual | Double | Costo estimado |
| recomendaciones | Array<String> | Lista de recomendaciones |

---

# 8. Códigos HTTP

## 200 OK

Solicitud procesada correctamente.

---

## 400 Bad Request

Error de validación.

Ejemplo

```json
{
  "message":"equipmentCount debe ser mayor o igual a cero."
}
```

---

## 404 Not Found

Endpoint inexistente.

---

## 500 Internal Server Error

Error interno.

---

## 503 Service Unavailable

Servicio temporalmente no disponible.

---

# 9. Reglas de Validación

| Campo | Validación |
|---------|------------|
| householdSize | Obligatorio |
| hasAc | Obligatorio |
| homeOffice | Obligatorio |
| housingType | Obligatorio |
| equipmentCount | >= 0 |
| consumoTotalMesAnterior | > 0 |
| peakUsageLevel | Obligatorio |

---

# 10. Mapeo Frontend → Backend

## Personas

```
Input Number

↓

householdSize
```

---

## Aire acondicionado

```
Sí

↓

1 (true)

No

↓

0 (false)
```

---

## Home Office

```
Sí

↓

true

No

↓

false
```

---

## Tipo vivienda

```
Casa

↓

CASA

Departamento

↓

DEPARTAMENTO

Monoambiente

↓

MONOAMBIENTE
```

---

## Horas de uso simultáneo

```
0–2 horas

↓

LOW

3–5 horas

↓

MEDIUM

Más de 5 horas

↓

HIGH
```

---

# 11. Responsabilidades

## Frontend

Responsable de:

- Validar datos básicos.
- Construir el Request DTO.
- Consumir la API.
- Mostrar resultados.

No calcula:

- Promedio diario.
- Recomendaciones.
- Costos.

---

## Backend

Responsable de:

- Validación de negocio.
- Cálculo del promedio diario.
- Comunicación con FastAPI.
- Cálculo del costo.
- Construcción del Response DTO.

---

## Machine Learning

Responsable de:

- Ejecutar inferencia.
- Devolver categoría.
- Devolver probabilidades.

---

# 12. Versionado

Versión inicial

```
v1
```

Toda modificación del contrato deberá generar una nueva versión.

Ejemplo

```
v2
```

---

# 13. Casos de Prueba

## Caso 1

Entrada

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

Resultado esperado

```json
{
  "categoria": "Moderado",
  "probabilidad": 0.82,
  "costoEstimadoMensual": 315.00,
  "recomendaciones": [
    "Reducir el uso de equipos durante horarios pico.",
    "Evaluar equipos de alto consumo.",
    "Distribuir actividades de mayor consumo."
  ]
}
```

---

## Caso 2

Entrada inválida

```json
{
  "householdSize": 0,
  "equipmentCount": -2
}
```

Resultado esperado

```
400 Bad Request
```

---

# 14. Compatibilidad

Este contrato deberá mantenerse estable durante todo el desarrollo del MVP.

Cualquier cambio deberá ser comunicado y aprobado por:

- Frontend
- Backend
- Data Science

antes de su implementación.

---

# Anexo A – DTO TypeScript

```typescript
export interface EnergyAnalysisRequest {
  householdSize: number;
  hasAc: number;
  homeOffice: boolean;
  housingType: HousingType;
  equipmentCount: number;
  consumoTotalMesAnterior: number;
  peakUsageLevel: PeakUsageLevel;
}
```

```typescript
export interface EnergyAnalysisResponse {
  categoria: string;
  probabilidad: number;
  costoEstimadoMensual: number;
  recomendaciones: string[];
}
```

---

# Anexo B – DTO Java

```java
public class ConsumoEnergeticoRequest {

    private Integer householdSize;

    private Integer hasAc;

    private Boolean homeOffice;

    private HousingType housingType;

    private Integer equipmentCount;

    private Double consumoTotalMesAnterior;

    private PeakUsageLevel peakUsageLevel;

}
```

---

# Anexo C – Criterios de Integración

Se consideran cumplidos los criterios de integración cuando:

- Frontend genera un JSON válido.
- Backend acepta el Request sin modificaciones.
- Backend responde con el Response definido.
- Frontend interpreta correctamente la respuesta.
- No existen conversiones manuales fuera del contrato acordado.
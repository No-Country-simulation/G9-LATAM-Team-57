







30/7/26, 20:36 

README.md 

# 🔬 Metodología 

El flujo de trabajo seguido por el equipo fue: 

1. **Exploración (EDA):** identificación de valores nulos, outliers y distribución de las variables de consumo. 

2. **Limpieza y transformación:** normalización de tipos de vivienda y niveles de uso pico, tratamiento de variables categóricas. 

3. **Definición de categorías:** el equipo definió y justificó los criterios utilizados para caracterizar los perfiles de eficiencia energética (Eficiente / Moderado / Ineficiente). 

4. **Entrenamiento:** comparación entre distintos modelos supervisados. 

5. **Evaluación:** selección del modelo con mejor desempeño según métricas de clasificación (F1-score). 

6. **Serialización:** exportación del modelo final en formato `.pkl` para ser consumido por el servicio de FastAPI. 

# 🧬 Variables del modelo 

|**Variable (dataset)**|**Tipo**|**Descripción**|
|---|---|---|
|`Household_Size`|int64|Cantidad de personas en el hogar|
|`Has_AC`|int64|Si el hogar cuenta con aire acondicionado (0/1)|
|`Home_Office`|bool|Si se realiza home office en la vivienda|
|`Housing_Type`|object|Tipo de vivienda (`Casa`,`Departamento`,`Monoambiente`)|
|`Equipment_Count`|int64|Cantidad de equipos eléctricos|
|`Avg_Energy_Consumption_kWh`|float64|Consumo energético promedio diario|
|`Peak_Usage_Level`|object|Nivel de uso en horario pico (`Low`,`Medium`,`High`)|



El detalle de cómo estas variables se traducen y validan en el backend de Java está documentado en `backend-java/README.md` . 

# 🏷 Categorías de clasificación 

El modelo clasifica cada vivienda en una de tres categorías de eficiencia energética: 

## **Eficiente Moderado** 

## **Ineficiente** 

Estas categorías fueron definidas y justificadas por el equipo con base en los patrones de consumo observados durante el EDA, y son la salida principal que consume el resto del sistema. 

# 🤖 Modelo entrenado 

**Algoritmo seleccionado:** Regresión Logística. 

**Métrica de evaluación:** F1-score = **0.85** . 

- **Alternativas evaluadas:** Random Forest, Árboles de Decisión. 

127.0.0.1:5500/data-science/README.html 

2/4 

30/7/26, 20:36 

README.md 

**Salida del modelo:** categoría predicha + probabilidad de cada una de las tres clases. 

```
{
  "categoria": "Moderado",
  "probabilidad": 0.5743,
  "probabilidades": {
    "Eficiente": 0.424,
    "Moderado": 0.5743,
    "Ineficiente": 0.0016
  }
}
```

# 💡 Recomendaciones generadas 

A partir de la categoría predicha y los patrones detectados, se generan recomendaciones orientadas a reducir el desperdicio energético, por ejemplo: 

- Reducir el uso de equipos durante los horarios pico. 

- Evaluar equipos con alto consumo energético. Distribuir las actividades de mayor consumo a lo largo del día. 

# 💰 Estimación financiera 

Como diferencial, se estima el impacto financiero mensual del consumo utilizando una tarifa de referencia estandarizada de **$0.75 USD/kWh** : 

```
{
  "costo_estimado_mensual": 315.00
}
```

# 🛠 Tecnologías 

|**Área**|**Tecnología**|
|---|---|
|Lenguaje|Python|
|Análisis de datos|Pandas|
|Modelado|Scikit-Learn|
|Modelos evaluados|Regresión Logística, Random Forest, Árboles de Decisión|
|Serialización|Pickle (`.pkl`)|
|Entorno de trabajo|Jupyter Notebook|



📁 Estructura 

127.0.0.1:5500/data-science/README.html 

3/4 

30/7/26, 20:36 📁 

README.md 

```
data-science/
├── notebooks/
```

- `│   └── analisis_consumo_energetico.ipynb   # EDA, entrenamiento y evaluación ├── data/` 

- `│   └── household_energy_consumption.csv    # Dataset base (Kaggle) ├── model/` 

```
│   └── energy_profile_model.pkl            # Modelo entrenado y serializado
└── README.md
```

# 🔄 Entrega hacia el servicio de Machine Learning 

El artefacto final de este componente es el archivo `energy_profile_model.pkl` , que se copia al servicio de inferencia ( `backend-python/model/` ) sin que ese servicio necesite acceso al dataset original ni a la lógica de entrenamiento. 

```
data-science/notebooks  →  entrena y evalúa  →  energy_profile_model.pkl  →
backend-python/model/
```

Este desacoplamiento permite reentrenar o mejorar el modelo sin tocar el código del servicio de inferencia ni del backend principal. 

# 👥 Equipo Data Science 

|**Nombre**|**Rol**|**Aporte**|
|---|---|---|
|**Jonathan Marino**|Data Analyst|Exploración y limpieza de datos (EDA)|
|**Hernán Pérez Melgar**|Data Scientist|Entrenamiento del modelo, serialización (`.pkl`)|



- ⬅ Volver al README principal del proyecto 

Developed 💻 from 🇦🇷 and 🇵🇪 

127.0.0.1:5500/data-science/README.html 

4/4 


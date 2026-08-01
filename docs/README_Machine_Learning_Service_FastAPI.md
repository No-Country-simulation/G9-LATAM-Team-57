







30/7/26, 20:37 

README.md 

```
                     Usuario
                        │
                        ▼
          Spring Boot API (Java 21)
                 OCI VM Pública
                        │
                  HTTP REST
                 POST /predict
                        │
                        ▼
          FastAPI ML Service
            OCI VM Privada
                        │
                        ▼
          energy_profile_model.pkl
      Logistic Regression (F1 = 0.85)
```

La API de FastAPI **no expone servicios al exterior** . Su acceso queda restringido únicamente a la VM donde se ejecuta el backend Java mediante la red privada (VCN) de Oracle Cloud Infrastructure. 

# ☁ Infraestructura 

|**Máquina**|**Rol**|**Acceso**|**Puerto**|
|---|---|---|---|
|VM Java|Backend principal|Pública|8080|
|VM Python|Servicio ML|Privada|8000|



La VM Python no posee IP pública. Todas las peticiones deben llegar desde la API Java. 

# 🛠 Tecnologías 

|**Componente**|**Tecnología**|
|---|---|
|Lenguaje|Python 3.12|
|Framework|FastAPI|
|Validación|Pydantic|
|Modelo|Scikit-Learn|
|Servidor ASGI|Uvicorn|
|Serialización|Pickle (.pkl)|
|Documentación|Swagger / OpenAPI|
|Infraestructura|Oracle Cloud Infrastructure|
|Containerización|Docker|



127.0.0.1:5500/backend-python/README.html 

2/5 

30/7/26, 20:37 

README.md 

# 📄 Endpoints 

## GET /health 

Permite verificar que el servicio está disponible y que el modelo fue cargado correctamente. 

```
{
    "status": "ok",
    "modelo_cargado": true
}
```

## POST /predict 

Ejecuta una predicción utilizando el modelo entrenado. 

Swagger interactivo: 

```
http://localhost:8000/docs
```

# 📥 Contrato de entrada 

```
{
  "household_size": 4,
  "has_ac": 1,
  "home_office": true,
  "housing_type": "CASA",
  "equipment_count": 10,
  "avg_energy_consumption_kwh": 14.0,
  "peak_usage_level": "HIGH"
}
```

## Housing Type 

Valores aceptados: `CASA` , `DEPARTAMENTO` , `MONOAMBIENTE` . 

Internamente el servicio realiza la traducción hacia las categorías utilizadas durante el entrenamiento del modelo (por ejemplo, `CASA → Casa` ). 

## Peak Usage Level 

Valores admitidos: `LOW` , `MEDIUM` , `HIGH` . También son traducidos internamente antes de invocar el modelo. 

## Avg Energy Consumption 

El valor **no es calculado por FastAPI** . Debe ser enviado por el backend Java luego de calcular: 

127.0.0.1:5500/backend-python/README.html 

3/5 

30/7/26, 20:37 

README.md 

```
Consumo total del mes anterior
```

```
──────────────────────────────
Cantidad de días del mes
```

Esta decisión mantiene completamente desacoplada la lógica de negocio del servicio de Machine Learning. 

# 📤 Respuesta 

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

# 🔄 Integración con Java 

El backend Java consume este servicio mediante `MlModelClientImpl` . 

Flujo de ejecución: 

```
Usuario → Spring Boot → POST /predict → FastAPI → Modelo ML → Respuesta → Spring
Boot → Usuario
```

Si la comunicación falla por timeout, conexión rechazada o el modelo no se encuentra disponible ( `503` ), el backend Java activa automáticamente el mecanismo de **Mock-Fallback** , garantizando la continuidad del servicio. Ver detalle en `backend-java/README.md` . 

# 🚀 Ejecución local 

Instalar dependencias: 

```
pip install -r requirements.txt
```

Ejecutar: 

```
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

127.0.0.1:5500/backend-python/README.html 

4/5 

30/7/26, 20:37 

README.md 

La documentación estará disponible en `http://localhost:8000/docs` . 

# 🐳 Docker 

Construcción: 

```
docker build -t energiai-ml .
```

Ejecución: 

```
docker run -p 8000:8000 energiai-ml
```

# 📁 Estructura 

```
fastapi_service/
├── app/
│   ├── main.py
│   ├── model.py
│   └── schemas.py
├── model/
│   └── energy_profile_model.pkl
├── Dockerfile
├── requirements.txt
└── README.md
```

# 📚 Responsabilidades del servicio 

Este microservicio tiene una única responsabilidad: 

Cargar el modelo entrenado. 

- Validar el contrato de entrada. 

- Traducir los enums utilizados por Java. Ejecutar la inferencia. Devolver las probabilidades del modelo. 

**No** implementa lógica de negocio, **no** calcula consumos, **no** realiza persistencia y **no** conoce reglas funcionales del dominio. Todo ese comportamiento permanece centralizado en la API desarrollada en Java, respetando el principio de **Single Responsibility** y favoreciendo el desacoplamiento entre ambas aplicaciones. 

⬅ Volver al README principal del proyecto 

Developed 💻 from 🇦🇷 and 🇵🇪 

127.0.0.1:5500/backend-python/README.html 

5/5 


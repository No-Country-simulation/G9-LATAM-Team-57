<mark>he ht</mark> ~~<u>st</u>~~ =) <u><mark>— a</mark></u> 







e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

e 

30/7/26, 20:35 

README.md 

información clara y accionable, aplicando Ciencia de Datos para: 

- Analizar patrones de consumo eléctrico de una vivienda. 

- Clasificar el perfil energético en categorías (Eficiente / Moderado / Ineficiente). 

- Generar recomendaciones concretas para reducir el desperdicio energético. 

- Estimar el impacto financiero del consumo, usando una tarifa de referencia de **$0.75 USD/kWh** . 

## 🎯 Necesidad del cliente 

La solución permite a un usuario residencial: 

- Comprender su perfil de consumo energético. 

- Identificar posibles fuentes de desperdicio. 

- Recibir recomendaciones de mejora personalizadas. 

- Estimar el costo asociado a su consumo. 

- Hacer seguimiento de sus indicadores de eficiencia a lo largo del tiempo. 

El objetivo es transformar datos de consumo en información clara y útil para apoyar decisiones más conscientes. 

## 📈 Validación de mercado 

La preocupación por la eficiencia energética y la sostenibilidad crece continuamente en distintos sectores de la sociedad. Empresas, gobiernos y consumidores buscan soluciones capaces de: 

- Reducir costos operativos. 

- Mejorar indicadores de sostenibilidad. 

- Incentivar el consumo consciente. 

- Monitorear patrones de uso de energía. 

- Apoyar estrategias de eficiencia energética con datos, no solo con intuición. 

Incluso las soluciones simples pueden generar valor al proporcionar análisis y recomendaciones personalizadas con base en los datos de los usuarios. 

# 🎯 Objetivos 

### Desarrollar un **MVP funcional** que: 

- ✅ Analice patrones de consumo energético. 

- ✅ Clasifique el perfil de eficiencia energética mediante un modelo de Machine Learning. 

- ✅ Genere recomendaciones de mejora. 

- ✅ Estime el impacto financiero con base en una tarifa de referencia. 

- ✅ Exponga los resultados mediante una API REST documentada. 

- ✅ Utilice al menos un servicio de OCI como parte de la arquitectura. 

# 🏗 Arquitectura 

```
                 Usuario
                    │
```

127.0.0.1:5500/README.html 

2/6 

30/7/26, 20:35 

README.md 

```
                    ▼
             API Java (Spring Boot)
             Oracle Cloud (VM Pública)
                    │
                 HTTP REST
                    ▼
        API Machine Learning (FastAPI)
          Oracle Cloud (VM Privada)
                    │
                    ▼
      Modelo entrenado (Scikit-Learn)
```

El backend en **Java** actúa como orquestador central: recibe la solicitud de consumo, ejecuta la lógica de negocio (cálculo del promedio diario), valida los datos y se comunica de forma agnóstica con el modelo de Machine Learning servido en **Python** . 

📌 El detalle completo del recorrido de una petición, capa por capa y con datos reales de ejemplo, está documentado en `backend-java/README.md` . 

# ☁ Arquitectura de infraestructura (OCI) 

El proyecto se despliega sobre **dos máquinas virtuales de Oracle Cloud Infrastructure (Free Tier)** , separadas por motivos de seguridad y de responsabilidad: 

|**Máquina**|**Rol**|**Acceso**|**Puerto**|
|---|---|---|---|
|**VM Java**|Backend principal (Spring Boot)|Pública — accesible desde internet|8080|
|**VM**|Servicio de Machine Learning|Privada — solo accesible desde la VM Java,|8000|
|**Python**|(FastAPI + modelo`.pkl`)|dentro de la misma VCN||



La VM de Python **no tiene IP pública** . Solo acepta tráfico desde la IP privada de la VM Java, dentro de la misma **VCN (Virtual Cloud Network)** . Esto reduce la superficie de ataque: nadie externo puede consultar el modelo de IA directamente, únicamente a través de la API en Java, que valida y orquesta cada solicitud. 

🔧 IPs y VCN se completan una vez desplegadas las máquinas. Detalle en `backend-java/README.md` . 

# 📦 Componentes 

|**Carpeta**|**Responsabilidad**|**Documentación**|
|---|---|---|
|`backend-java/`|API principal, orquestación y lógica de negocio|README|
|`backend-python/`|Servicio de Machine Learning (inferencia)|README|
|`data-science/`|Entrenamiento, evaluación y serialización del modelo|README|
|`docs/`|Diagramas de arquitectura y flujo|—|
|`postman/`|Colección de Postman para probar los endpoints|—|



127.0.0.1:5500/README.html 

3/6 

30/7/26, 20:35 

README.md 

Cada componente cuenta con su propio README técnico. Juntos, los cuatro documentos cuentan una sola historia: del dato crudo a la recomendación accionable. 

# 🛠 Tecnologías 

|**Área**|**Tecnología**|
|---|---|
|Backend|Java 21 (Virtual Threads) + Spring Boot 3.3.x|
|Machine Learning|Python 3.12 + FastAPI|
|Ciencia de Datos|Pandas + Scikit-Learn|
|Infraestructura|Oracle Cloud Infrastructure (2 VM Compute, Free Tier)|
|Documentación de API|Swagger / OpenAPI 3|
|Control de versiones|Git + GitHub|
|Persistencia (fase futura)|PostgreSQL + TimescaleDB / Flyway|



# 📊 Dataset 

El modelo de clasificación fue entrenado con el dataset público **Household Energy Consumption** (Kaggle), que registra consumo diario de energía, temperatura y niveles de uso en horario pico por hogar. 

El equipo de Data Science procesó este dataset (EDA, limpieza, entrenamiento y evaluación) y exportó el modelo entrenado en formato `.pkl` , listo para ser servido desde la API en Python. 

Detalle completo de variables, metodología y métricas en `data-science/README.md` . 

# 👥 Equipo 

|**Nombre**|**Rol**|**Aporte**|
|---|---|---|
|**Jonathan Marino**|Data Analyst|Exploración y limpieza de datos (EDA)|
|**Hernán Pérez Melgar**|Data Scientist|Entrenamiento del modelo, serialización (`.pkl`)|
|**Pablo Graff**|Backend<br>Developer|API Java/Spring Boot, API de Machine Learning<br>(FastAPI) e infraestructura OCI|
|**Agustina Lerda**|Backend<br>Developer|API Java/Spring Boot|
|**Annie Lehmann**|Backend<br>Developer|API Java/Spring Boot|
|**Frank Mijhael Bendezu**|Full Stack|Ft-d / t tl|
|**Hinostroza**|Developer|ronen  sopore ransversa|



# ⚙ Cómo ejecutar el proyecto 

127.0.0.1:5500/README.html 

4/6 

30/7/26, 20:35 

README.md 

## 1. Cloná el repositorio 

```
git clone https://github.com/No-Country-simulation/G9-LATAM-Team-57.git
cd energiai
```

## 2. Levantá el servicio de Machine Learning (Python) 

```
cd backend-python
pip install -r requirements.txt
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

## 3. Levantá la API principal (Java) 

En Windows (PowerShell / CMD): 

```
cd backend-java
mvnw.cmd spring-boot:run
```

En Linux / macOS / Git Bash: 

```
cd backend-java
./mvnw spring-boot:run
```

La API principal queda disponible en `http://localhost:8080` y consume internamente al servicio de Python en `http://localhost:8000` . 

Si el servicio de Python no está disponible, la API Java activa automáticamente un mecanismo de **Mock-Fallback** para no interrumpir el servicio. Ver detalle en `backend-java/README.md` . 

# 📚 Documentación 

`backend-java/README.md` — API principal, arquitectura, DTOs, endpoints, Mock-Fallback y despliegue en OCI. 

- `backend-python/README.md` — Servicio de Machine Learning, contrato de inferencia y ejecución con Docker. 

`data-science/README.md` — Dataset, metodología, modelo entrenado y métricas de evaluación. `postman/` — Colección de Postman con requests listas para probar la API. 

# 🗺 Roadmap 

Persistencia de resultados con PostgreSQL + TimescaleDB (historial de análisis por usuario/vivienda). Migraciones versionadas con Flyway. 

127.0.0.1:5500/README.html 

5/6 

30/7/26, 20:35 

README.md 

- Dashboard de seguimiento y comparación entre períodos. 

- Procesamiento por lotes vía CSV. 

- Containerización completa con Docker. 

- Tests automatizados end-to-end. 

- Alertas de alto consumo. Front-end de carga y visualización de resultados. 

# 🙌 Créditos 

Proyecto desarrollado en el marco del **Hackathon ONE — Proyectos G9 | Alura + Oracle** . 

Developed 💻 from 🇦🇷 and 🇵🇪. 

127.0.0.1:5500/README.html 

6/6 


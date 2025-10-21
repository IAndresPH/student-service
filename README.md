# student-service

Microservicio responsable de la gestión de estudiantes y de los servicios centrados en el rendimiento académico del estudiante. Expondrá endpoints para CRUD de estudiantes, consultas de resumen académico y operaciones necesarias para el cálculo y consulta de calificaciones.

---

## Descripción del repositorio

Este repositorio contiene únicamente la implementación del **Student Service**. Su responsabilidad principal es:

* Gestionar la entidad **Student** (registro, actualización, asociación a `subject` y `program`).
* Exponer endpoints de consulta centrados en la vista del estudiante (resumen académico, promedios, historial).
* Proveer endpoints de salud para despliegue y monitoreo básico.
* Integrarse con otros microservicios (Auth, Teacher, Notification, University) por API o eventos.

Tecnologías principales:

* Java 21
* Spring Boot 3.5.5
* Build: Maven
* Tests: JUnit 5
* Base de datos: PostgreSQL
* Contenerización prevista (Docker)

---

## Estructura del repositorio

Al inicio, la rama `main` solo contendrá el **proyecto base** generado desde Spring Initializr, con lo esencial para compilar y ejecutar.

Ejemplo (alto nivel):

```
/
├─ src/
│  ├─ main/
│  │  ├─ java/         → código fuente
│  │  └─ resources/    → application.properties
│  └─ test/            → pruebas unitarias con JUnit 5
├─ docs/                → diagramas, contratos API (OpenAPI), ADRs
├─ Dockerfile
├─ pom.xml
├─ .gitignore
└─ README.md
```

---

## Políticas de rama y flujo de trabajo

Ramas principales en el proyecto:

* **main**
  Contiene únicamente código listo para producción. Al inicio, tendrá solo el proyecto base de Spring Initializr.
  No se permite commit directo; todo se integra vía PR desde ramas `release/*` o `hotfix/*`.

* **release/** (ej. `release.s.2025.09`)
  Ramas de estabilización previas a producción. Se usan para pruebas y preparación antes de hacer merge a `main`.

* **qa**
  Rama utilizada como fuente para despliegues en el entorno de QA. Se actualiza desde `develop`.

* **develop**
  Rama de integración diaria. Aquí se mergean PRs desde `feature/*` y `bugfix/*`.

Flujo típico:
`feature/*` → PR → `develop` → merge a `qa` → validación → `release/*` → `main`.

---

## Perfiles / properties (con Maven)

Se usarán archivos de configuración basados en `application-*.properties`, diferenciados por perfil:

* **application.properties** → Configuración base compartida.
* **application-dev.properties** → Configuración para desarrollo local (PostgreSQL local, logging en debug).
* **application-qa.properties** → Configuración para entorno QA (DB de QA, logging info).
* **application-prod.properties** → Configuración optimizada para producción (datasource pool, logging reducido, seguridad).

---

## API (versionado)

Se empleará versionado en la ruta base: `/api/v1/...`.

Endpoints principales previstos:

* `GET  /api/v1/students/{id}` → Obtener datos del estudiante.
* `POST /api/v1/students` → Crear estudiante.
* `PUT  /api/v1/students/{id}` → Actualizar estudiante.
* `DELETE /api/v1/students/{id}` → Eliminar estudiante.
* `GET /api/v1/students/{id}/summary` → Resumen académico.

---

## Dockerización (concepto)

El servicio se diseñará para ejecutarse en contenedor. Puntos a considerar:

* Imagen base: JDK compatible con Java 21.
* Multi-stage build para optimizar el tamaño.
* Variables de entorno para perfiles y configuraciones.
* Healthcheck en `/actuator/health`.
* Tagging de imágenes por versión (`vX.Y.Z`) y por commit (`sha`).

---

## Enlaces relevantes

* Base repo [university-academic-tracker](https://github.com/IAndresPH/university-academic-tracker.git)

---

## Colaboración y convención de commits

* Convencional commits: `feat(student): add student summary endpoint`
* PRs deben:

  * Referenciar HU o issue en Jira.
  * Incluir descripción clara.
  * Ser revisadas antes de merge.
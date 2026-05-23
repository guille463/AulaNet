# AulaNet — Sistema de Gestión Escolar

## Descripción

**AulaNet** es una aplicación web de gestión escolar para educación primaria (1º a 6º). Permite administrar de forma completa los cuatro pilares del centro: alumnos, profesores, aulas y asignaturas, así como las relaciones entre ellos.

### Qué gestiona

**Alumnos**
Registro completo con nombre, apellido y fecha de nacimiento. Cada alumno se asigna a un aula (y por tanto a un curso). Al crearlo, el sistema genera automáticamente su código identificador (`ALUM-{id}`) y lo matricula en todas las asignaturas correspondientes a su curso.

**Profesores**
Registro con nombre, apellido, email único y especialidad docente. Las especialidades disponibles son: `GENERAL`, `EDUCACION_FISICA`, `INGLES`, `MUSICA`, `LOGOPEDA` y `RELIGION`. Cada profesor recibe un código `PROF-{id}` al ser creado.

**Aulas**
Cada aula combina un curso (1º–6º) y un grupo (A o B), generando un código único automático como `1ºA` o `3ºB`. Tiene una capacidad máxima de 30 alumnos y puede tener un profesor asignado como tutor.

**Asignaturas**
Cada asignatura pertenece a un curso concreto y tiene un número de horas semanales (1–6). Se identifica con un código `ASG-{id}`.

### Relaciones que gestiona

- Un alumno pertenece a un aula y está matriculado en las asignaturas de su curso. Cada matrícula (`MTR-{id}`) almacena la nota del alumno en esa asignatura.
- Un profesor puede impartir varias asignaturas, y una asignatura puede ser impartida por varios profesores. Cada relación almacena las horas semanales dedicadas.
- Un aula puede tener un profesor tutor, que se puede asignar y revocar de forma independiente.

### Comportamiento automático al arrancar

Al iniciar la aplicación se carga una base de datos de prueba completa en el siguiente orden: profesores → aulas → alumnos → asignaturas → relaciones profesor-asignatura → matrículas alumno-asignatura.

### Tecnología

Backend en **Spring Boot** con base de datos **H2 en memoria** y frontend en **HTML/CSS/JavaScript puro**, sin frameworks de cliente. La comunicación entre capas es íntegramente mediante una API REST bajo `/api/v1`.

---

## Requisitos del sistema

| Herramienta | Versión  |
|-------------|----------|
| Java (JDK)  | 17       |
| Maven       | 3.8      |

No se necesita ninguna base de datos externa. H2 se levanta automáticamente en memoria al arrancar la aplicación.

---

## Ejecutar


```bash
cd colegio
```

```bash
mvn spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

### Ejecutar los tests

```bash
mvn test
```

---

## Acceso a la consola H2

Con la aplicación en marcha:

- URL: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** *(vacia)*

Permite inspeccionar las tablas y ejecutar SQL en tiempo real.

---

## Estructura del proyecto

### Backend — `src/main/java/com/colegio/`

#### `config/`
Configuracion del proyercto. `InicializadorConfig` expone la propiedad `colegio.inicializacion.numero-alumnos` para controlar cuántos alumnos se generan al arrancar. `ManejoErrores` captura los `RuntimeException` lanzados por los servicios y devuelve respuestas HTTP con el codigo y mensaje adecuados.
```
config/
├── InicializadorConfig.java
└── ManejoErrores.java
```

#### `model/`
Entidades JPA que se mapean a tablas en la base de datos. Cada entidad tiene sus reglas mediante anotaciones (`nullable`, `unique`). Los métodos `@Transient` calculan valores sin persistirlos, como obtener el curso de un alumno a través de su aula.

```
model/
├── Alumno.java
├── Asignatura.java
├── Aula.java
├── Profesor.java
├── AlumnoAsignatura.java      # Relación N:M matricula
├── ProfesorAsignatura.java    # Relación N:M impartir
├── Curso.java                 # Enum: PRIMERO-SEXTO
├── Grupo.java                 # Enum: grupos A, B
└── Especialidad.java          # Enum: especialidades dee los docentes
```

#### `repository/`
Interfaces que extienden `JpaRepository`. Declaran las consultas necesarias mediante nombres de metodo derivados que Spring Data JPA traduce a SQL automáticamente. No contienen logica.
```
repository/
├── AlumnoRepository.java
├── AsignaturaRepository.java
├── AulaRepository.java
├── ProfesorRepository.java
├── AlumnoAsignaturaRepository.java
└── ProfesorAsignaturaRepository.java
```

#### `service/`
Logica de negocio. Valida los datos de entrada, genera los codigos identificadores (`ALUM-`, `PROF-`, `ASG-`, `MTR-`), aplica las reglas (comopor ejemplo, matricular automáticamente a un alumno en todas las asignaturas de su curso al crearlo) y lanza `RuntimeException` cuando alguna restriccion no se cumple.

```
service/
├── AlumnoService.java
├── AsignaturaService.java
├── AulaService.java
├── ProfesorService.java
├── AlumnoAsignaturaService.java
└── ProfesorAsignaturaService.java
```

#### `util/`
Constantes con los prefijos de los codigos y los inicializadores de datos. `DbInitializater` mantiene el orden de ejecucion de los inicializadores: profesores → aulas → alumnos → asignaturas → relaciones profesor-asignatura → matrículas.

```
util/
├── Constantes.java
├── DbInitializater.java
├── AlumnoInitializater.java
├── AulaInitializater.java
├── Asignaturainitializater.java
├── Profesorinitializater.java
├── Alumnoasignaturainitializater.java
└── Profesorasignaturainitializater.java
```

#### `controller/`
Capa REST. Recibe las peticiones HTTP, delega en el servicio correspondiente y devuelve la respuesta. No contiene logica de negocio. Cada controlador mapea un recurso bajo `/api/v1`.

```
controller/
├── AlumnoController.java
├── AsignaturaController.java
├── AulaController.java
├── ProfesorController.java
├── AlumnoAsignaturaController.java
├── ProfesorAsignaturaController.java
└── StatusController.java
```

---


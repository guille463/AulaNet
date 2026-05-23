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


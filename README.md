[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/fgfGl4k_)

# AulaNet — Sistema de Gestión Escolar

## Descripción

**AulaNet** es una aplicación web de gestión escolar para educación primaria **(1º a 6º)**. Permite administrar de forma completa los cuatro pilares del centro: **alumnos, profesores, aulas y asignaturas**, así como las relaciones entre ellos.

## ¿Qué gestiona?
### **Alumnos**

Registro completo con nombre, apellido y fecha de nacimiento. Cada alumno se asigna a un aula (y por tanto a un curso). 
- Al crearlo, el sistema genera automáticamente su código identificador **(ALUM-{id})** y lo matricula en todas las asignaturas correspondientes a su curso.

### **Profesores**
Registro con nombre, apellido, email único y especialidad docente. 

- Las especialidades disponibles son: **GENERAL, EDUCACION_FISICA, INGLES, MUSICA, LOGOPEDA y RELIGION**. Cada profesor recibe un código **PROF-{id}** al ser creado.

### **Aulas**

- Cada aula combina un curso **(1º–6º)** y un grupo **(A o B)**, generando un código único automático como **1ºA o 3ºB**. Tiene una capacidad máxima de **30 alumnos** y puede tener un profesor asignado como tutor.


###  **Asignaturas** 
- Cada asignatura pertenece a un curso concreto y tiene un número de horas semanales **(1–6)**. Se identifica con un código **ASG-{id}**.
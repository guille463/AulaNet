package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Coordinador de la inicializacion de datos del colegio.
 *
 * <p>
 * El orden de ejecucion es obligatorio por dependencias entre entidades:
 * profesores → aulas → alumnos → asignaturas → profesor-asignatura →
 * alumno-asignatura.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Component
public class DbInitializater implements CommandLineRunner {

    @Autowired
    private Profesorinitializater inicializadorDeProfesor;

    @Autowired
    private AulaInitializater inicializadorDeAula;

    @Autowired
    private AlumnoInitializater inicializadorDeAlumno;

    @Autowired
    private Asignaturainitializater inicializadorDeAsignatura;

    @Autowired
    private Profesorasignaturainitializater inicializadorDeProfesorAsignatura;

    @Autowired
    private Alumnoasignaturainitializater inicializadorDeAlumnoAsignatura;

    @Override
    public void run(String... args) {
        inicializarBaseDeDatos();
    }

    private void inicializarBaseDeDatos() {
        System.out.println("Iniciando la carga de datos iniciales");
        inicializadorDeProfesor.iniciarProfesores();
        inicializadorDeAula.iniciarAulas();
        inicializadorDeAlumno.iniciarAlumnos();
        inicializadorDeAsignatura.iniciarAsignaturas();
        inicializadorDeProfesorAsignatura.iniciarProfesorAsignaturas();
        inicializadorDeAlumnoAsignatura.iniciarAlumnoAsignaturas();
        System.out.println("Carga completada");
    }
}

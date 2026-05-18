package com.colegio.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Alumno;
import com.colegio.model.AlumnoAsignatura;
import com.colegio.model.Asignatura;
import com.colegio.repository.AlumnoAsignaturaRepository;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.service.AlumnoAsignaturaService;
import com.github.javafaker.Faker;

/**
 * Inicializador de relaciones alumno-asignatura del colegio.
 *
* <p>
 * Genera las relaciones de los alumnos con las asignaturas usando {@link Faker} y los
 * guarda a traves de {@link AlumnoAsignaturaService}. Solo se ejecuta si no hay alumnos y asignaturas
 * en la base de datos.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AlumnoAsignatura
 */
@Component
public class Alumnoasignaturainitializater {

    @Autowired
    private AlumnoAsignaturaService alumnoAsignaturaService;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AlumnoAsignaturaRepository alumnoAsignaturaRepository;

    /**
     * Inicializa las erlaciones entre los alumnos y las asignaturas si la base de datos esta vacia 
     * 
     * 
     */
    public void iniciarAlumnoAsignaturas() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        for (Alumno alumno : alumnos) {
            List<Asignatura> asignaturas = asignaturaRepository.findByCurso(alumno.getCurso());
            for (Asignatura asignatura : asignaturas) {
                if (!alumnoAsignaturaRepository.existsByAlumnoAndAsignatura(alumno, asignatura)) {
                    AlumnoAsignatura aa = new AlumnoAsignatura(0.0, alumno, asignatura);
                    alumnoAsignaturaService.guardar(aa);
                }
            }
        }
    }
}

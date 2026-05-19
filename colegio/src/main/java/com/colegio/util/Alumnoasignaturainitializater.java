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

/**
 * Inicializador de relaciones alumno-asignatura del colegio.
 *
 * <p>
 * Recorre todos los alumnos y los matricula en las asignaturas de su curso a
 * traves de {@link AlumnoAsignaturaService}. Solo crea la matricula si no
 * existe ya.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AlumnoAsignaturaService
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

    // ============================================================
    // METODOS PUBLICOS
    // ============================================================
    /**
     * Inicializa las relaciones entre los alumnos y las asignaturas.
     *
     * <p>
     * Para cada alumno busca las asignaturas de su curso y crea la matricula si
     * no existe ya.</p>
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

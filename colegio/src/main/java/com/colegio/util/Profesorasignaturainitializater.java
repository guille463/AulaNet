package com.colegio.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Asignatura;
import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;
import com.colegio.service.ProfesorAsignaturaService;

/**
 * Inicializador de relaciones profesor-asignatura del colegio.
 *
 * <p>
 * Asigna cada profesor a las asignaturas que corresponden a su especialidad en
 * todos los cursos.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Component
public class Profesorasignaturainitializater {

    @Autowired
    private ProfesorAsignaturaService profesorAsignaturaService;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    public void iniciarProfesorAsignaturas() {
        asignarPorEspecialidad(Especialidad.GENERAL, "Matematicas");
        asignarPorEspecialidad(Especialidad.GENERAL, "Lengua Castellana y Literatura");
        asignarPorEspecialidad(Especialidad.GENERAL, "Ciencias de la Naturaleza");
        asignarPorEspecialidad(Especialidad.GENERAL, "Ciencias Sociales");
        asignarPorEspecialidad(Especialidad.GENERAL, "Plastica");
        asignarPorEspecialidad(Especialidad.EDUCACION_FISICA, "Educacion Fisica");
        asignarPorEspecialidad(Especialidad.INGLES, "Ingles");
        asignarPorEspecialidad(Especialidad.MUSICA, "Musica");
        asignarPorEspecialidad(Especialidad.RELIGION, "Religion");
    }

    private void asignarPorEspecialidad(Especialidad especialidad, String nombreAsignatura) {
        List<Profesor> profesores = profesorRepository.findByEspecialidad(especialidad);
        if (!profesores.isEmpty()) {
            Profesor profesor = profesores.get(0);
            List<Asignatura> asignaturas = asignaturaRepository.findAll()
                    .stream()
                    .filter(a -> a.getNombre().equals(nombreAsignatura))
                    .toList();
            for (Asignatura asignatura : asignaturas) {
                if (!profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                    ProfesorAsignatura pa = new ProfesorAsignatura(asignatura.getHorasSemana(), profesor, asignatura);
                    profesorAsignaturaService.guardar(pa);
                }
            }
        }
    }
}

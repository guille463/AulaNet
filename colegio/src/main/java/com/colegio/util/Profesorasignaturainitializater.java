package com.colegio.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
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
 * Asigna cada profesor {@link Especialidad#GENERAL} a las asignaturas de su
 * curso correspondiente. Los profesores especialistas se asignan a su
 * asignatura en todos los cursos.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see ProfesorAsignaturaService
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

    // ============================================================
    // METODOS PUBLICOS
    // ============================================================
    /**
     * Inicializa las relaciones profesor-asignatura.
     *
     * <p>
     * Asigna a cada profesor {@link Especialidad#GENERAL} las asignaturas del
     * curso que le corresponde por posicion. Despues asigna los profesores
     * especialistas a su asignatura en todos los cursos.
     * </p>
     */
    public void iniciarProfesorAsignaturas() {
        List<Profesor> profesoresGenerales = profesorRepository.findByEspecialidad(Especialidad.GENERAL);
        List<Curso> cursos = List.of(Curso.values());

        for (int i = 0; i < profesoresGenerales.size() && i < cursos.size(); i++) {
            Profesor profesor = profesoresGenerales.get(i);
            Curso curso = cursos.get(i);

            List<Asignatura> todasDelCurso = asignaturaRepository.findByCurso(curso);
            List<Asignatura> asignaturas = new ArrayList<>();

            for (Asignatura asignatura : todasDelCurso) {
                String nombre = asignatura.getNombre();
                if (!nombre.equals("Educacion Fisica")
                        && !nombre.equals("Ingles")
                        && !nombre.equals("Musica")
                        && !nombre.equals("Religion")) {
                    asignaturas.add(asignatura);
                }
            }

            for (Asignatura asignatura : asignaturas) {
                if (!profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                    ProfesorAsignatura pa = new ProfesorAsignatura(asignatura.getHorasSemana(), profesor, asignatura);
                    profesorAsignaturaService.guardar(pa);
                }
            }
        }

        asignarPorEspecialidad(Especialidad.EDUCACION_FISICA, "Educacion Fisica");
        asignarPorEspecialidad(Especialidad.INGLES, "Ingles");
        asignarPorEspecialidad(Especialidad.MUSICA, "Musica");
        asignarPorEspecialidad(Especialidad.RELIGION, "Religion");
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Asigna el primer profesor de la especialidad indicada a todas las
     * asignaturas cuyo nombre coincide con el indicado.
     *
     * <p>
     * Solo crea la relacion si no existe ya.
     * </p>
     *
     * @param especialidad     especialidad del profesor a buscar
     * @param nombreAsignatura nombre de la asignatura a asignar
     */
    private void asignarPorEspecialidad(Especialidad especialidad, String nombreAsignatura) {
        List<Profesor> profesores = profesorRepository.findByEspecialidad(especialidad);
        if (!profesores.isEmpty()) {
            Profesor profesor = profesores.get(0);
            List<Asignatura> todasAsignaturas = asignaturaRepository.findAll();
            List<Asignatura> asignaturas = new ArrayList<>();

            for (Asignatura asignatura : todasAsignaturas) {
                if (asignatura.getNombre().equals(nombreAsignatura)) {
                    asignaturas.add(asignatura);
                }
            }

            for (Asignatura asignatura : asignaturas) {
                if (!profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                    ProfesorAsignatura pa = new ProfesorAsignatura(asignatura.getHorasSemana(), profesor, asignatura);
                    profesorAsignaturaService.guardar(pa);
                }
            }
        }
    }
}

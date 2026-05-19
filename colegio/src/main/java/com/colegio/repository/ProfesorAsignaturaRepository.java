package com.colegio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;

/**
 * Repositorio para la entidad {@link ProfesorAsignatura}.
 *
 * <p>
 * Extiende {@link JpaRepository} y define consultas derivadas para filtrar
 * relaciones por profesor, asignatura y curso.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see ProfesorAsignatura
 */
@Repository
public interface ProfesorAsignaturaRepository extends JpaRepository<ProfesorAsignatura, Long> {

    /**
     * Devuelve las relaciones del profesor indicado.
     *
     * @param profesor profesor por el que filtrar
     * @return lista de relaciones del profesor
     */
    List<ProfesorAsignatura> findByProfesor(Profesor profesor);

    /**
     * Devuelve las relaciones del profesor con el id indicado.
     *
     * @param profesorId id del profesor
     * @return lista de relaciones del profesor
     */
    List<ProfesorAsignatura> findByProfesorId(Long profesorId);

    /**
     * Devuelve las relaciones de la asignatura indicada.
     *
     * @param asignatura asignatura por la que filtrar
     * @return lista de relaciones de la asignatura
     */
    List<ProfesorAsignatura> findByAsignatura(Asignatura asignatura);

    /**
     * Devuelve las relaciones de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de relaciones de la asignatura
     */
    List<ProfesorAsignatura> findByAsignaturaId(Long asignaturaId);

    /**
     * Devuelve las relaciones cuya asignatura pertenece al curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de relaciones del curso
     */
    List<ProfesorAsignatura> findByAsignaturaCurso(Curso curso);

    /**
     * Comprueba si existe una relacion para el profesor y asignatura indicados.
     *
     * @param profesor profesor a comprobar
     * @param asignatura asignatura a comprobar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsByProfesorAndAsignatura(Profesor profesor, Asignatura asignatura);
}

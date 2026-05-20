package com.colegio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Alumno;
import com.colegio.model.AlumnoAsignatura;
import com.colegio.model.Asignatura;
import com.colegio.model.Curso;

/**
 * Repositorio para la entidad {@link AlumnoAsignatura}.
 *
 * <p>
 * Extiende {@link JpaRepository} y filtra matriculas por alumno, asignatura y
 * curso.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AlumnoAsignatura
 */
@Repository
public interface AlumnoAsignaturaRepository extends JpaRepository<AlumnoAsignatura, Long> {

    /**
     * Devuelve las matriculas del alumno indicado.
     *
     * @param alumno alumno por el que filtrar
     * @return lista de matriculas del alumno
     */
    List<AlumnoAsignatura> findByAlumno(Alumno alumno);

    /**
     * Devuelve las matriculas del alumno con el id indicado.
     *
     * @param alumnoId id del alumno
     * @return lista de matriculas del alumno
     */
    List<AlumnoAsignatura> findByAlumnoId(Long alumnoId);

    /**
     * Devuelve las matriculas de la asignatura indicada.
     *
     * @param asignatura asignatura por la que filtrar
     * @return lista de matriculas de la asignatura
     */
    List<AlumnoAsignatura> findByAsignatura(Asignatura asignatura);

    /**
     * Devuelve las matriculas de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de matriculas de la asignatura
     */
    List<AlumnoAsignatura> findByAsignaturaId(Long asignaturaId);

    /**
     * Comprueba si ya existe una matricula para el alumno y asignatura
     * indicados.
     *
     * @param alumno     alumno a comprobar
     * @param asignatura asignatura a comprobar
     * @return {@code true} si la matricula ya existe, {@code false} en caso
     *         contrario
     */
    boolean existsByAlumnoAndAsignatura(Alumno alumno, Asignatura asignatura);

    /**
     * Devuelve las matriculas cuya asignatura pertenece al curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de matriculas del curso
     */
    List<AlumnoAsignatura> findByAsignaturaCurso(Curso curso);
}

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
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Repository
public interface AlumnoAsignaturaRepository extends JpaRepository<AlumnoAsignatura, Long> {

    List<AlumnoAsignatura> findByAlumno(Alumno alumno);

    List<AlumnoAsignatura> findByAlumnoId(Long alumnoId);

    List<AlumnoAsignatura> findByAsignatura(Asignatura asignatura);

    List<AlumnoAsignatura> findByAsignaturaId(Long asignaturaId);

    boolean existsByAlumnoAndAsignatura(Alumno alumno, Asignatura asignatura);

    List<AlumnoAsignatura> findByAsignaturaCurso(Curso curso);
}

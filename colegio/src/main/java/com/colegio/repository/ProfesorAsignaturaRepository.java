package com.colegio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Asignatura;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;

/**
 * Repositorio para la entidad {@link ProfesorAsignatura}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Repository
public interface ProfesorAsignaturaRepository extends JpaRepository<ProfesorAsignatura, Long> {

    List<ProfesorAsignatura> findByProfesor(Profesor profesor);

    List<ProfesorAsignatura> findByProfesorId(Long profesorId);

    List<ProfesorAsignatura> findByAsignatura(Asignatura asignatura);

    List<ProfesorAsignatura> findByAsignaturaId(Long asignaturaId);

    boolean existsByProfesorAndAsignatura(Profesor profesor, Asignatura asignatura);
}

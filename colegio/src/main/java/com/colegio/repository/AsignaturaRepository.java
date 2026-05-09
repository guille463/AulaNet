package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;

/**
 * Repositorio para la entidad {@link Asignatura}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    List<Asignatura> findByCurso(Curso curso);

    Optional<Asignatura> findByCodigo(String codigo);

    boolean existsByNombreAndCurso(String nombre, Curso curso);
}

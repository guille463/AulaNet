package com.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.Asignatura;

/**
 * Repositorio para la entidad {@link Asignatura}.
 *
 * <p>
 * Proporciona operaciones CRUD sobre la tabla {@code asignaturas}. La
 * implementacion es generada automaticamente por Spring Data JPA.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

}

package com.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.Profesor;

/**
 * Repositorio para la entidad {@link Profesor}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

}

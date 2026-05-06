package com.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.ProfesorAsignatura;

/**
 * Repositorio para la entidad {@link ProfesorAsignatura}.
 *
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Repository
public interface ProfesorAsignaturaRepository extends JpaRepository<ProfesorAsignatura, Long> {

}

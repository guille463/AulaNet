package com.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.Matricula;

/**
 * Repositorio para la entidad {@link Matricula}.
 *
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

}

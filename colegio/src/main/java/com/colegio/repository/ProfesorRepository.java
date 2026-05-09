package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;

/**
 * Repositorio para la entidad {@link Profesor}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByEmail(String email);

    Optional<Profesor> findByCodigo(String codigo);

    boolean existsByEmail(String email);

    List<Profesor> findByEspecialidad(Especialidad especialidad);
}

package com.colegio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Alumno;

/**
 * Repositorio para la entidad Alumno.
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByEmail(String email);
}

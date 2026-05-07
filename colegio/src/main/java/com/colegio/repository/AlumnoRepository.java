package com.colegio.repository;

import java.util.List;
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

    Optional<Alumno> findByNombre(String nombre);

    List<Alumno> findByCurso(String curso);

    List<Alumno> findByNombreContaining(String nombre);

    List<Alumno> findByNombreAndApellido(String nombre, String apellido);
}

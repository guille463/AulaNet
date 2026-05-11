package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.model.Curso;

/**
 * Repositorio para la entidad {@link Alumno}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Alumno> findByAula(Aula aula);

    List<Alumno> findByAulaId(Long aulaId);

    List<Alumno> findByAulaCurso(Curso curso);

    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    List<Alumno> findByNombreAndApellido(String nombre, String apellido);

    List<Alumno> findByAulaCodigo(String codigo);
}

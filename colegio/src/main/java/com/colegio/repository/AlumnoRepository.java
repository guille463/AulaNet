package com.colegio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.model.Curso;

/**
 * Repositorio para la entidad {@link Alumno}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByAula(Aula aula);

    List<Alumno> findByAulaId(Long aulaId);

    List<Alumno> findByAulaCurso(Curso curso);

    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    List<Alumno> findByNombreAndApellido(String nombre, String apellido);

    List<Alumno> findByAulaCodigo(String codigo);

    List<Alumno> findByFechaNacimiento(LocalDate fechaNacimiento);
}

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

    /**
     * Devuelve todas las asignaturas de un curso.
     *
     * @param curso curso por el que filtrar
     * @return lista de asignaturas del curso
     */
    List<Asignatura> findByCurso(Curso curso);

    /**
     * Busca una asignatura por su codigo identificador.
     *
     * @param codigo codigo con formato {@code ASG-<id>}
     * @return asignatura encontrada, o vacio si no existe
     */
    Optional<Asignatura> findByCodigo(String codigo);

    /**
     * Comprueba si existe una asignatura con el nombre y curso indicados.
     *
     * @param nombre nombre de la asignatura
     * @param curso curso de la asignatura
     * @return {@code true} si ya existe, {@code false} en caso contrario
     */
    boolean existsByNombreAndCurso(String nombre, Curso curso);
}

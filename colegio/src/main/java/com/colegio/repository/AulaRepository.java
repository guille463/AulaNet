package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.model.Grupo;
import com.colegio.model.Profesor;

/**
 * Repositorio para la entidad {@link Aula}.
 *
 * <p>
 * Extiende {@link JpaRepository} y define consultas derivadas y JPQL para
 * filtrar aulas por curso, grupo, tutor y disponibilidad de plazas.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see Aula
 */
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    /**
     * Devuelve el aula con el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return aula encontrada, o vacio si no existe
     */
    Optional<Aula> findByCodigo(String codigo);

    /**
     * Comprueba si existe un aula con el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsByCodigo(String codigo);

    /**
     * Devuelve las aulas del curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de aulas del curso
     */
    List<Aula> findByCurso(Curso curso);

    /**
     * Devuelve las aulas del grupo indicado.
     *
     * @param grupo grupo por el que filtrar
     * @return lista de aulas del grupo
     */
    List<Aula> findByGrupo(Grupo grupo);

    /**
     * Devuelve las aulas que coinciden con el curso y grupo indicados.
     *
     * @param curso curso por el que filtrar
     * @param grupo grupo por el que filtrar
     * @return lista de aulas que coinciden
     */
    List<Aula> findByCursoAndGrupo(Curso curso, Grupo grupo);

    /**
     * Devuelve las aulas sin tutor asignado.
     *
     * @return lista de aulas sin tutor
     */
    List<Aula> findByTutorIsNull();

    /**
     * Devuelve el aula cuyo tutor es el profesor indicado.
     *
     * @param tutor profesor tutor a buscar
     * @return aula encontrada, o vacio si no existe
     */
    Optional<Aula> findByTutor(Profesor tutor);

    /**
     * Comprueba si existe un aula con el tutor indicado.
     *
     * @param tutor profesor tutor a comprobar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsByTutor(Profesor tutor);

    /**
     * Devuelve las aulas donde el numero de alumnos es menor que la capacidad.
     *
     * @return lista de aulas con plazas libres
     */
    @Query("SELECT a FROM Aula a WHERE a.capacidad > "
            + "(SELECT COUNT(al) FROM Alumno al WHERE al.aula = a)")
    List<Aula> findAulasConPlazasLibres();

    /**
     * Devuelve el numero de alumnos asignados al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return numero de alumnos del aula
     */
    @Query("SELECT COUNT(al) FROM Alumno al WHERE al.aula.id = :aulaId")
    int countAlumnosByAulaId(Long aulaId);
}

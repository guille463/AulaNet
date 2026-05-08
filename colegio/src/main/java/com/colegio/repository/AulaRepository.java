package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.colegio.model.Aula;
import com.colegio.model.Profesor;

/**
 * Repositorio para la entidad {@link Aula}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    Optional<Aula> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Aula> findByCurso(String curso);

    List<Aula> findByCursoStartingWith(String curso);

    List<Aula> findByTutorIsNull();

    Optional<Aula> findByTutor(Profesor tutor);

    boolean existsByTutor(Profesor tutor);

    /**
     * Aulas donde el numero de alumnos es menor que la capacidad
     */
    @Query("SELECT a FROM Aula a WHERE a.capacidad > "
            + "(SELECT COUNT(al) FROM Alumno al WHERE al.aula = a)")
    List<Aula> findAulasConPlazasLibres();

    /**
     * Cuenta los alumnos deaula concreta
     */
    @Query("SELECT COUNT(al) FROM Alumno al WHERE al.aula.id = :aulaId")
    int countAlumnosByAulaId(Long aulaId);
}

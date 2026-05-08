package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.colegio.model.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    Optional<Aula> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Aula> findByCurso(String curso);

    List<Aula> findByProfesorIsNull();

    @Query("SELECT aula FROM Aula aula WHERE aula.capacidad > "
            + "(SELECT COUNT(alumno) FROM Alumno alumno WHERE alumno.aula = aula)")
    List<Aula> findAulasConPlazasLibres();

    @Query("SELECT COUNT(alumno) FROM Alumno alumno WHERE alumno.aula.id = ?1")
    int countAlumnosByAulaId(Long aulaId);
}

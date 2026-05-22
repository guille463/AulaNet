package com.colegio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.model.Curso;

/**
 * Repositorio para la entidad {@link Alumno}.
 *
 * <p>
 * Extiende {@link JpaRepository} y las diferentes consultas para filtrar
 * alumnos por aula, curso, nombre y fecha de nacimiento.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see Alumno
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    /**
     * Devuelve los alumnos asignados a un aula concreta.
     *
     * @param aula aula por la que filtrar
     * @return lista de alumnos del aula
     */
    List<Alumno> findByAula(Aula aula);

    /**
     * Devuelve los alumnos asignados al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return lista de alumnos del aula
     */
    List<Alumno> findByAulaId(Long aulaId);

    /**
     * Devuelve los alumnos cuya aula pertenece al curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de alumnos del curso
     */
    List<Alumno> findByAulaCurso(Curso curso);

    /**
     * Devuelve los alumnos cuyo nombre empieza por la cadena indicada, sin
     * distinguir mayusculas.
     *
     * @param nombre prefijo del nombre a buscar
     * @return lista de alumnos que coinciden
     */
    List<Alumno> findByNombreStartingWithIgnoreCase(String nombre);

    /**
     * Devuelve los alumnos cuyo nombre completo empieza por la cadena indicada,
     * sin distinguir mayusculas. Concatena nombre y apellido para la
     * comparacion.
     *
     * @param texto prefijo a buscar sobre el nombre completo
     * @return lista de alumnos que coinciden
     */
    @Query("SELECT a FROM Alumno a WHERE LOWER(CONCAT(a.nombre, ' ', a.apellido)) LIKE LOWER(CONCAT(:texto, '%'))")
    List<Alumno> findByNombreCompletoStartingWithIgnoreCase(@Param("texto") String texto);

    /**
     * Devuelve los alumnos cuyo nombre y apellido empiezan por las cadenas
     * indicadas, sin distinguir mayusculas.
     *
     * @param nombre prefijo del nombre
     * @param apellido prefijo del apellido
     * @return lista de alumnos que coinciden
     */
    List<Alumno> findByNombreStartingWithIgnoreCaseAndApellidoStartingWithIgnoreCase(String nombre, String apellido);

    /**
     * Devuelve los alumnos cuya aula tiene el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return lista de alumnos del aula
     */
    List<Alumno> findByAulaCodigo(String codigo);

    /**
     * Devuelve los alumnos nacidos en la fecha indicada.
     *
     * @param fechaNacimiento fecha de nacimiento a buscar
     * @return lista de alumnos con esa fecha de nacimiento
     */
    List<Alumno> findByFechaNacimiento(LocalDate fechaNacimiento);
}

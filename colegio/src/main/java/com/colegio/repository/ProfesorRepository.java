package com.colegio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;

/**
 * Repositorio para la entidad {@link Profesor}.
 *
 * <p>
 * Extiende {@link JpaRepository} y define consultas para filtrar profesores por
 * email, codigo, nombre y especialidad.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see Profesor
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    /**
     * Devuelve el profesor con el email indicado.
     *
     * @param email email del profesor
     * @return profesor encontrado, o vacio si no existe
     */
    Optional<Profesor> findByEmail(String email);

    /**
     * Devuelve el profesor con el codigo indicado.
     *
     * @param codigo codigo del profesor
     * @return profesor encontrado, o vacio si no existe
     */
    Optional<Profesor> findByCodigo(String codigo);

    /**
     * Devuelve los profesores cuyo nombre empieza por la cadena indicada, sin
     * distinguir mayusculas.
     *
     * @param nombre prefijo del nombre a buscar
     * @return lista de profesores que coinciden
     */
    List<Profesor> findByNombreStartingWithIgnoreCase(String nombre);

    /**
     * Devuelve los profesores cuyo nombre y apellido empiezan por las cadenas
     * indicadas, sin distinguir mayusculas.
     *
     * @param nombre prefijo del nombre
     * @param apellido prefijo del apellido
     * @return lista de profesores que coinciden
     */
    List<Profesor> findByNombreStartingWithIgnoreCaseAndApellidoStartingWithIgnoreCase(String nombre, String apellido);

    /**
     * Devuelve los profesores cuyo nombre completo empieza por la cadena
     * indicada, sin distinguir mayusculas. Concatena nombre y apellido para la
     * comparacion.
     *
     * @param texto prefijo a buscar sobre el nombre completo
     * @return lista de profesores que coinciden
     */
    @Query("SELECT p FROM Profesor p WHERE LOWER(CONCAT(p.nombre, ' ', p.apellido)) LIKE LOWER(CONCAT(:texto, '%'))")
    List<Profesor> findByNombreCompletoStartingWithIgnoreCase(@Param("texto") String texto);

    /**
     * Comprueba si existe un profesor con el email indicado.
     *
     * @param email email a comprobar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * Devuelve los profesores con la especialidad indicada.
     *
     * @param especialidad especialidad por la que filtrar
     * @return lista de profesores con esa especialidad
     */
    List<Profesor> findByEspecialidad(Especialidad especialidad);
}

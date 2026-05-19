package com.colegio.model;

/**
 * Enumeracion que representa las especialidades de los profesores.
 *
 * <p>
 * Determina las asignaturas que se asignan automaticamente a cada
 * {@link Profesor} al ser creado.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see Profesor
 */
public enum Especialidad {

    /**
     * Profesor de aula con asignaturas troncales.
     */
    GENERAL,
    /**
     * Especialista en Educacion Fisica.
     */
    EDUCACION_FISICA,
    /**
     * Especialista en Ingles.
     */
    INGLES,
    /**
     * Especialista en Musica.
     */
    MUSICA,
    /**
     * Especialista en logopedia.
     */
    LOGOPEDA,
    /**
     * Especialista en Religion.
     */
    RELIGION
}

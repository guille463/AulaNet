package com.colegio.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeracion que representa los cursos academicos del colegio.
 *
 * <p>
 * Cada constante tiene una etiqueta usada en la serializacion JSON mediante
 * {@code @JsonValue} y {@code @JsonCreator}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
public enum Curso {

    /**
     * Primer curso de primaria.
     */
    PRIMERO("1º"),
    /**
     * Segundo curso de primaria.
     */
    SEGUNDO("2º"),
    /**
     * Tercer curso de primaria.
     */
    TERCERO("3º"),
    /**
     * Cuarto curso de primaria.
     */
    CUARTO("4º"),
    /**
     * Quinto curso de primaria.
     */
    QUINTO("5º"),
    /**
     * Sexto curso de primaria.
     */
    SEXTO("6º");

    /**
     * Etiqueta legible del curso, por ejemplo {@code 1º}.
     */
    private final String etiqueta;

    /**
     * Crea un curso con su etiqueta legible.
     *
     * @param etiqueta etiqueta del curso
     */
    Curso(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve la etiqueta del curso para la serializacion JSON.
     *
     * @return etiqueta del curso
     */
    @JsonValue
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Crea un {@link Curso} a partir de su etiqueta o nombre.
     *
     * @param valor etiqueta o nombre del curso
     * @return curso correspondiente
     * @throws IllegalArgumentException si el valor no coincide con ningun curso
     */
    @JsonCreator
    public static Curso fromEtiqueta(String valor) {
        for (Curso c : Curso.values()) {
            if (c.etiqueta.equals(valor) || c.name().equals(valor)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Curso no válido: " + valor);
    }
}

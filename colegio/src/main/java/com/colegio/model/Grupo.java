package com.colegio.model;

/**
 * Enumeracion que representa los grupos dentro de un curso.
 *
 * <p>
 * Cada constante tiene una etiqueta usada para generar el codigo del
 * {@link Aula}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see Aula
 */
public enum Grupo {

    /**
     * Grupo A.
     */
    A("A"),
    /**
     * Grupo B.
     */
    B("B");

    /**
     * Etiqueta legible del grupo.
     */
    private final String etiqueta;

    /**
     * Crea un grupo con su etiqueta legible.
     *
     * @param etiqueta etiqueta del grupo
     */
    Grupo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve la etiqueta del grupo.
     *
     * @return etiqueta del grupo
     */
    public String getEtiqueta() {
        return etiqueta;
    }
}

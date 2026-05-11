package com.colegio.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Curso {
    PRIMERO("1º"),
    SEGUNDO("2º"),
    TERCERO("3º"),
    CUARTO("4º"),
    QUINTO("5º"),
    SEXTO("6º");

    private final String etiqueta;

    Curso(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @JsonValue
    public String getEtiqueta() {
        return etiqueta;
    }

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

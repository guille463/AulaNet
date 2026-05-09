package com.colegio.model;

public enum Grupo {
    A("A"),
    B("B");

    private final String etiqueta;

    Grupo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}

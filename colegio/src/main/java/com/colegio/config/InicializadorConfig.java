package com.colegio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de inicializacion del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Configuration
@ConfigurationProperties(prefix = "colegio.inicializacion")
public class InicializadorConfig {

    private int numeroAlumnos = 360;

    public int getNumeroAlumnos() {
        return numeroAlumnos;
    }

    public void setNumeroAlumnos(int numeroAlumnos) {
        this.numeroAlumnos = numeroAlumnos;
    }
}

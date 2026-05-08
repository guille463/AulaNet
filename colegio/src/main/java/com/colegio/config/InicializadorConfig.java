package com.colegio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "colegio.inicializacion")
public class InicializadorConfig {

    private int numeroDeAlumnos = 10;

    public int getNumeroAlumnos() {
        return numeroDeAlumnos;
    }

    public void setNumeroDeAlumnos(int numeroDeAlumnos) {
        this.numeroDeAlumnos = numeroDeAlumnos;
    }

}

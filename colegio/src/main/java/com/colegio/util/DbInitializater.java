package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitializater implements CommandLineRunner {

    @Autowired
    private AlumnoInitializater inicializadorDeAlumno;

    @Override
    public void run(String... args) {
        inicializarBaseDeDatos();

    }

    private void inicializarBaseDeDatos() {
        System.out.println("Iniciando la carga de datos iniciales");
        inicializadorDeAlumno.iniciarAlumnos();
        System.out.println("Carga completada");
    }
}

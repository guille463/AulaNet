package com.colegio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicacion AulaNet.
 *
 * <p>
 * Punto de entrada de la aplicacion Spring Boot para la gestion escolar.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@SpringBootApplication
public class ColegioApplication {

    /**
     * Constructor vacio.
     */
    public ColegioApplication() {
    }

    /**
     * Metodo principal que arranca la aplicacion.
     *
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(ColegioApplication.class, args);
    }

}

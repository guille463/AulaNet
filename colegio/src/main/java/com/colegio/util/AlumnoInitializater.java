package com.colegio.util;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.config.InicializadorConfig;
import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.service.AlumnoService;
import com.colegio.service.AulaService;
import com.github.javafaker.Faker;

/**
 * Inicializador de alumnos del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Component
public class AlumnoInitializater {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private InicializadorConfig config;

    private final Faker faker = new Faker(new Locale("es"));

    public void iniciarAlumnos() {
        if (alumnoService.count() == 0) {
            for (int i = 0; i < config.getNumeroAlumnos(); i++) {
                try {
                    alumnoService.guardarAlumno(crearAlumnoAleatorio());
                } catch (RuntimeException excepcion) {
                    System.out.println("No se pudo crear alumno: " + excepcion.getMessage());
                }
            }
        }
    }

    private Alumno crearAlumnoAleatorio() {
        List<Aula> aulasDisponibles = aulaService.findAulasConPlazasLibres();

        if (aulasDisponibles.isEmpty()) {
            throw new RuntimeException("No hay plazas disponibles en ningún aula");
        }

        Aula aula = aulasDisponibles.get(faker.number().numberBetween(0, aulasDisponibles.size()));

        return new Alumno(
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                aula);
    }
}

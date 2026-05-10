package com.colegio.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.config.InicializadorConfig;
import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.service.AlumnoService;
import com.colegio.service.AulaService;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

/**
 * Inicializador de alumnos del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
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

    @Transactional
    public void iniciarAlumnos() {
        if (alumnoService.count() == 0) {
            for (int i = 0; i < config.getNumeroAlumnos(); i++) {
                alumnoService.guardarAlumno(crearAlumnoAleatorio());
            }
        }
    }

    private Alumno crearAlumnoAleatorio() {
        LocalDate fechaNac = LocalDate.of(
                faker.number().numberBetween(2015, 2020),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28));

        Curso curso = calcularCurso(fechaNac);

        Aula aula = aulaService.findAulasConPlazasLibres()
                .stream()
                .filter(a -> a.getCurso().equals(curso))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay plazas disponibles en el curso " + curso));

        return new Alumno(
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                aula);
    }

    private Curso calcularCurso(LocalDate fechaNac) {
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        if (edad == 6) {
            return Curso.PRIMERO;
        }
        if (edad == 7) {
            return Curso.SEGUNDO;
        }
        if (edad == 8) {
            return Curso.TERCERO;
        }
        if (edad == 9) {
            return Curso.CUARTO;
        }
        if (edad == 10) {
            return Curso.QUINTO;
        }
        if (edad == 11) {
            return Curso.SEXTO;
        }
        throw new IllegalArgumentException("Edad fuera de rango para primaria: " + edad);
    }
}

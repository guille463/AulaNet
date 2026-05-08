package com.colegio.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.config.InicializadorConfig;
import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.service.AlumnoService;
import com.colegio.service.AulaService;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

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
                guardarAlumno(crearAlumnoAleatorio());
            }
        }
    }

    private Alumno crearAlumnoAleatorio() {
        LocalDate fechaNac = LocalDate.of(
                faker.number().numberBetween(2010, 2018),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28)
        );

        String curso = calcularCurso(fechaNac);
        Aula aula = aulaService.findAulasConPlazasLibres()
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay plazas disponibles"));

        return new Alumno(
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                fechaNac,
                curso,
                aula
        );
    }

    private void guardarAlumno(Alumno alumno) {
        Alumno guardado = alumnoService.guardarAlumno(alumno);
        guardado.setCodigo(Constantes.PREFIJO_ALUMNO + guardado.getId());
        alumnoService.guardarAlumno(guardado);
    }

    private String calcularCurso(LocalDate fechaNac) {
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        String curso;
        if (edad == 6) {
            curso = "1º";
        } else if (edad <= 8) {
            curso = "2º";
        } else if (edad <= 9) {
            curso = "3º";
        } else if (edad <= 10) {
            curso = "4º";
        } else if (edad <= 11) {
            curso = "5º";
        } else if (edad <= 13) {
            curso = "6º";
        } else {
            throw new IllegalArgumentException("Edad fuera de rango: " + edad);
        }
        return curso;
    }
}

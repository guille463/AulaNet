package com.colegio.util;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.config.InicializadorConfig;
import com.colegio.model.Alumno;
import com.colegio.service.AlumnoService;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

@Component
public class AlumnoInitializater {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private InicializadorConfig config;

    private final Faker faker = new Faker(new Locale("es"));

    @Transactional
    public void iniciarAlumnos() {
        for (int i = 0; i < config.getNumeroAlumnos(); i++) {
            Alumno alumno = crearAlumnoAleatorio();
            guardarAlumno(alumno);
        }
    }

    private Alumno crearAlumnoAleatorio() {
        String[] cursos = {"1ºA", "2ºA", "3ºA", "4ºA", "5ºA", "6ºA"};
        return new Alumno(
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                LocalDate.of(
                        faker.number().numberBetween(2010, 2018),
                        faker.number().numberBetween(1, 12),
                        faker.number().numberBetween(1, 28)
                ),
                cursos[faker.number().numberBetween(0, cursos.length)]
        );
    }

    private void guardarAlumno(Alumno alumno) {
        Alumno guardado = alumnoService.guardarAlumno(alumno);
        guardado.setCodigo(Constantes.PREFIJO_ALUMNO + guardado.getId());
        alumnoService.guardarAlumno(alumno);
    }

}

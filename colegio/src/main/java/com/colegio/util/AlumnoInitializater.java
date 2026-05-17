package com.colegio.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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

/**
 * Inicializador de alumnos del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 4.0
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
        Date fechaDate = faker.date().birthday(6, 12);
        LocalDate fechaNacimiento = fechaDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();
        Curso curso = obtenerCursoPorEdad(edad);

        List<Aula> aulasDisponibles = aulaService.findAulasConPlazasLibres()
                .stream()
                .filter(a -> a.getCurso().equals(curso))
                .collect(java.util.stream.Collectors.toList());

        if (aulasDisponibles.isEmpty()) {
            throw new RuntimeException("No hay plazas disponibles en " + curso);
        }

        Aula aula = aulasDisponibles.get(faker.number().numberBetween(0, aulasDisponibles.size()));

        return new Alumno(
                faker.name().firstName(),
                faker.name().lastName(),
                fechaNacimiento,
                aula);
    }

    private Curso obtenerCursoPorEdad(int edad) {
        if (edad == 6) {
            return Curso.PRIMERO;
        } else if (edad == 7) {
            return Curso.SEGUNDO;
        } else if (edad == 8) {
            return Curso.TERCERO;
        } else if (edad == 9) {
            return Curso.CUARTO;
        } else if (edad == 10) {
            return Curso.QUINTO;
        } else {
            return Curso.SEXTO;
        }
    }
}

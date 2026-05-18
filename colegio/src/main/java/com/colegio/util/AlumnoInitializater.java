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
 * <p>
 * Genera alumnos aleatorios con datos ficticios usando {@link Faker} y los
 * guarda a traves de {@link AlumnoService}. Solo se ejecuta si no hay alumnos
 * en la base de datos.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 4.0
 * @see AlumnoService
 * @see InicializadorConfig
 */
@Component
public class AlumnoInitializater {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private InicializadorConfig config;

    /**
     * Generador de datos en español.
     */
    private final Faker faker = new Faker(new Locale("es"));

    // ============================================================
    // METODOS PUBLICOS
    // ============================================================
    /**
     * Inicializa los alumnos si la base de datos esta vacia.
     *
     * <p>
     * Genera tantos alumnos como indica
     * {@link InicializadorConfig#getNumeroAlumnos()}. Si un alumno no puede
     * crearse (aula llena, curso sin plazas) se registra el error y se continua
     * con el siguiente.</p>
     */
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

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Crea un alumno aleatorio con nombre, apellido, fecha de nacimiento y aula
     * asignados por {@link Faker}.
     *
     * <p>
     * La edad del alumno determina el curso y se busca un aula con plazas
     * libres en ese curso.</p>
     *
     * @return alumno generado
     * @throws RuntimeException si no hay plazas disponibles en el curso
     * correspondiente
     */
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

    /**
     * Devuelve el curso correspondiente a la edad del alumno.
     *
     * <p>
     * Mapea edades de 6 a 11 años a
     * {@link Curso#PRIMERO} - {@link Curso#SEXTO}. Cualquier edad fuera de ese
     * rango va a {@link Curso#SEXTO}.</p>
     *
     * @param edad edad del alumno en años
     * @return curso correspondiente
     */
    private Curso obtenerCursoPorEdad(int edad) {
        Curso curso;
        if (edad == 6) {
            curso = Curso.PRIMERO;
        } else if (edad == 7) {
            curso = Curso.SEGUNDO;
        } else if (edad == 8) {
            curso = Curso.TERCERO;
        } else if (edad == 9) {
            curso = Curso.CUARTO;
        } else if (edad == 10) {
            curso = Curso.QUINTO;
        } else {
            curso = Curso.SEXTO;
        }
        return curso;
    }
}

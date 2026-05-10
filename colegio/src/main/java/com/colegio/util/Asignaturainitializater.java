package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.service.AsignaturaService;

/**
 * Inicializador de asignaturas del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Component
public class Asignaturainitializater {

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public void iniciarAsignaturas() {
        for (Curso curso : Curso.values()) {
            crearSiNoExiste("Matematicas", curso, 5, "Numeros, calculo y geometria");
            crearSiNoExiste("Lengua Castellana y Literatura", curso, 5, "Lectura, escritura y expresion oral");
            crearSiNoExiste("Ciencias de la Naturaleza", curso, 3, "El cuerpo humano, animales y plantas");
            crearSiNoExiste("Ciencias Sociales", curso, 3, "Geografia, historia y vida en sociedad");
            crearSiNoExiste("Educacion Fisica", curso, 3, "Deportes, salud y expresion corporal");
            crearSiNoExiste("Ingles", curso, 4, "Lengua extranjera");
            crearSiNoExiste("Musica", curso, 2, "Ritmo, audicion e interpretacion");
            crearSiNoExiste("Plastica", curso, 2, "Expresion artistica y visual");
            crearSiNoExiste("Religion", curso, 2, "Valores eticos y religion");
        }
    }

    private void crearSiNoExiste(String nombre, Curso curso, int horasSemana,
            String descripcion) {
        if (!asignaturaRepository.existsByNombreAndCurso(nombre, curso)) {
            asignaturaService.guardarAsignatura(new Asignatura(nombre, curso, horasSemana, descripcion));
        }
    }
}

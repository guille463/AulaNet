package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;
import com.colegio.service.ProfesorService;

/**
 * Inicializador de profesores del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Component
public class Profesorinitializater {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorRepository profesorRepository;

    public void iniciarProfesores() {
        crearSiNoExiste("Ana", "Garcia", "ana.garcia@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Laura", "Torres", "laura.torres@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Miguel", "Jimenez", "miguel.jimenez@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Carmen", "Moreno", "carmen.moreno@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Antonio", "Romero", "antonio.romero@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Isabel", "Navarro", "isabel.navarro@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Luis", "Martinez", "luis.martinez@colegio.com", Especialidad.EDUCACION_FISICA);
        crearSiNoExiste("Maria", "Lopez", "maria.lopez@colegio.com", Especialidad.INGLES);
        crearSiNoExiste("Carlos", "Fernandez", "carlos.fernandez@colegio.com", Especialidad.MUSICA);
        crearSiNoExiste("Sofia", "Ruiz", "sofia.ruiz@colegio.com", Especialidad.LOGOPEDA);
        crearSiNoExiste("Pedro", "Sanchez", "pedro.sanchez@colegio.com", Especialidad.RELIGION);
        crearSiNoExiste("Rosa", "Castillo", "rosa.castillo@colegio.com", Especialidad.INGLES);
    }

    private void crearSiNoExiste(String nombre, String apellido, String email,
            Especialidad especialidad) {
        if (!profesorRepository.existsByEmail(email)) {
            profesorService.guardarProfesor(new Profesor(nombre, apellido, email, especialidad));
        }
    }
}

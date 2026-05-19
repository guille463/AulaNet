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
 * <p>
 * Crea un conjunto de profesores con distintas especialidades a traves de
 * {@link ProfesorService}. Solo crea cada profesor si no existe ya en la base
 * de datos.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see ProfesorService
 */
@Component
public class Profesorinitializater {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorRepository profesorRepository;

    // ============================================================
    // METODOS PUBLICOS
    // ============================================================
    /**
     * Inicializa los profesores si no existen en la base de datos.
     *
     * <p>
     * Crea profesores de especialidad {@code GENERAL},
     * {@code EDUCACION_FISICA}, {@code INGLES}, {@code MUSICA},
     * {@code LOGOPEDA} y {@code RELIGION}.</p>
     */
    public void iniciarProfesores() {
        crearSiNoExiste("Ana", "Garcia", "ana.garcia@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Laura", "Torres", "laura.torres@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Miguel", "Jimenez", "miguel.jimenez@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Carmen", "Moreno", "carmen.moreno@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Antonio", "Romero", "antonio.romero@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Isabel", "Navarro", "isabel.navarro@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Javier", "Molina", "javier.molina@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Patricia", "Vega", "patricia.vega@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Fernando", "Reyes", "fernando.reyes@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Elena", "Campos", "elena.campos@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Roberto", "Perez", "roberto.perez@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Cristina", "Soto", "cristina.soto@colegio.com", Especialidad.GENERAL);
        crearSiNoExiste("Luis", "Martinez", "luis.martinez@colegio.com", Especialidad.EDUCACION_FISICA);
        crearSiNoExiste("Maria", "Lopez", "maria.lopez@colegio.com", Especialidad.INGLES);
        crearSiNoExiste("Carlos", "Fernandez", "carlos.fernandez@colegio.com", Especialidad.MUSICA);
        crearSiNoExiste("Sofia", "Ruiz", "sofia.ruiz@colegio.com", Especialidad.LOGOPEDA);
        crearSiNoExiste("Pedro", "Sanchez", "pedro.sanchez@colegio.com", Especialidad.RELIGION);
        crearSiNoExiste("Rosa", "Castillo", "rosa.castillo@colegio.com", Especialidad.INGLES);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Crea un profesor si no existe ya un registro con el email indicado.
     *
     * @param nombre nombre del profesor
     * @param apellido apellido del profesor
     * @param email email del profesor
     * @param especialidad especialidad del profesor
     */
    private void crearSiNoExiste(String nombre, String apellido, String email,
            Especialidad especialidad) {
        if (!profesorRepository.existsByEmail(email)) {
            profesorService.guardarProfesor(new Profesor(nombre, apellido, email, especialidad));
        }
    }
}

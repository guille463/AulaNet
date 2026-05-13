package com.colegio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los profesores.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor buscarProfesorPorId(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor con id " + id + " no encontrado"));
    }

    public Profesor buscarProfesorPorEmail(String email) {
        return profesorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor con email " + email + " no encontrado"));
    }

    public List<Profesor> buscarPorNombre(String nombre) {
        return profesorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Profesor> buscarPorEspecialidad(Especialidad especialidad) {
        return profesorRepository.findByEspecialidad(especialidad);
    }

    public Profesor guardarProfesor(Profesor profesor) {
        if (profesorRepository.existsByEmail(profesor.getEmail())) {
            throw new RuntimeException("Ya existe un profesor con el email: " + profesor.getEmail());
        }
        Profesor guardado = profesorRepository.save(profesor);
        guardado.setCodigo(Constantes.PREFIJO_PROFESOR + guardado.getId());
        guardado = profesorRepository.save(guardado);

        asignarAsignaturas(guardado);

        return guardado;
    }

    public Profesor actualizarProfesor(Long id, Profesor profesor) {
        Profesor existente = buscarProfesorPorId(id);

        if (!existente.getEmail().equals(profesor.getEmail())
                && profesorRepository.existsByEmail(profesor.getEmail())) {
            throw new RuntimeException("Ya existe un profesor con el email: " + profesor.getEmail());
        }

        existente.setNombre(profesor.getNombre());
        existente.setApellido(profesor.getApellido());
        existente.setEmail(profesor.getEmail());
        existente.setEspecialidad(profesor.getEspecialidad());
        return profesorRepository.save(existente);
    }

    public void borrarProfesor(Long id) {
        buscarProfesorPorId(id);
        profesorAsignaturaRepository.deleteAll(profesorAsignaturaRepository.findByProfesorId(id));
        profesorRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS DE PROSEOR
    // ============================================================
    private void asignarAsignaturas(Profesor profesor) {
        List<String> nombresAsignaturas = new ArrayList<>();

        if (profesor.getEspecialidad().equals(Especialidad.GENERAL)) {
            nombresAsignaturas.add("Matematicas");
            nombresAsignaturas.add("Lengua Castellana y Literatura");
            nombresAsignaturas.add("Ciencias de la Naturaleza");
            nombresAsignaturas.add("Ciencias Sociales");
            nombresAsignaturas.add("Plastica");
        } else if (profesor.getEspecialidad().equals(Especialidad.EDUCACION_FISICA)) {
            nombresAsignaturas.add("Educacion Fisica");
        } else if (profesor.getEspecialidad().equals(Especialidad.INGLES)) {
            nombresAsignaturas.add("Ingles");
        } else if (profesor.getEspecialidad().equals(Especialidad.MUSICA)) {
            nombresAsignaturas.add("Musica");
        } else if (profesor.getEspecialidad().equals(Especialidad.RELIGION)) {
            nombresAsignaturas.add("Religion");
        }

        List<Asignatura> todasAsignaturas = asignaturaRepository.findAll();

        for (Asignatura asignatura : todasAsignaturas) {
            if (nombresAsignaturas.contains(asignatura.getNombre())) {
                if (!profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                    ProfesorAsignatura pa = new ProfesorAsignatura(asignatura.getHorasSemana(), profesor, asignatura);
                    profesorAsignaturaRepository.save(pa);
                }
            }
        }
    }
}

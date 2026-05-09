package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los profesores.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

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

    public List<Profesor> buscarPorEspecialidad(Especialidad especialidad) {
        return profesorRepository.findByEspecialidad(especialidad);
    }

    public Profesor guardarProfesor(Profesor profesor) {
        if (profesorRepository.existsByEmail(profesor.getEmail())) {
            throw new RuntimeException("Ya existe un profesor con el email: " + profesor.getEmail());
        }
        Profesor guardado = profesorRepository.save(profesor);
        guardado.setCodigo(Constantes.PREFIJO_PROFESOR + guardado.getId());
        return profesorRepository.save(guardado);
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
        profesorRepository.deleteById(id);
    }
}

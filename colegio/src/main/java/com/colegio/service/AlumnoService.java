package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AulaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los alumnos.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno buscarPorId(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno con id " + id + " no encontrado"));
    }

    public Alumno buscarPorEmail(String email) {
        return alumnoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Alumno con email " + email + " no encontrado"));
    }

    public List<Alumno> buscarPorAula(Long aulaId) {
        return alumnoRepository.findByAulaId(aulaId);
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        return alumnoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Alumno> buscarPorNombreYApellido(String nombre, String apellido) {
        return alumnoRepository.findByNombreAndApellido(nombre, apellido);
    }

    /**
     * Guarda un nuevo alumno verificando que el aula exista y tenga plazas
     * libres.
     */
    public Alumno guardarAlumno(Alumno alumno) {
        if (alumnoRepository.existsByEmail(alumno.getEmail())) {
            throw new RuntimeException("Ya existe un alumno con el email: " + alumno.getEmail());
        }

        Aula aula = aulaRepository.findById(alumno.getAula().getId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        int ocupacion = alumnoRepository.findByAula(aula).size();
        if (ocupacion >= aula.getCapacidad()) {
            throw new RuntimeException("El aula " + aula.getCodigo() + " está llena");
        }

        alumno.setAula(aula);
        Alumno guardado = alumnoRepository.save(alumno);
        guardado.setCodigo(Constantes.PREFIJO_ALUMNO + guardado.getId());
        return alumnoRepository.save(guardado);
    }

    public Alumno actualizarAlumno(Long id, Alumno alumno) {
        Alumno existente = buscarPorId(id);
        existente.setNombre(alumno.getNombre());
        existente.setApellido(alumno.getApellido());
        existente.setEmail(alumno.getEmail());
        existente.setFechaNac(alumno.getFechaNac());
        if (alumno.getAula() != null) {
            Aula aula = aulaRepository.findById(alumno.getAula().getId())
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
            existente.setAula(aula);
        }
        return alumnoRepository.save(existente);
    }

    public void borrarAlumno(Long id) {
        buscarPorId(id);
        alumnoRepository.deleteById(id);
    }

    public long count() {
        return alumnoRepository.count();
    }
}

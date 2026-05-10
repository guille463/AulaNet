package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.model.AlumnoAsignatura;
import com.colegio.model.Asignatura;
import com.colegio.repository.AlumnoAsignaturaRepository;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las relaciones
 * alumno-asignatura.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class AlumnoAsignaturaService {

    @Autowired
    private AlumnoAsignaturaRepository alumnoAsignaturaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de relaciones alumno-asignatura.
     */
    public List<AlumnoAsignatura> listar() {
        return alumnoAsignaturaRepository.findAll();
    }

    /**
     * Busca una relacion alumno-asignatura por su identificador.
     */
    public AlumnoAsignatura buscarPorId(Long id) {
        return alumnoAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AlumnoAsignatura con id: " + id + " no encontrado"));
    }

    /**
     * Devuelve las asignaturas de un alumno concreto.
     */
    public List<AlumnoAsignatura> buscarPorAlumno(Long alumnoId) {
        return alumnoAsignaturaRepository.findByAlumnoId(alumnoId);
    }

    /**
     * Devuelve los alumnos de una asignatura concreta.
     */
    public List<AlumnoAsignatura> buscarPorAsignatura(Long asignaturaId) {
        return alumnoAsignaturaRepository.findByAsignaturaId(asignaturaId);
    }

    /**
     * Guarda una nueva relacion alumno-asignatura
     */
    public AlumnoAsignatura guardar(AlumnoAsignatura alumnoAsignatura) {
        Alumno alumno = alumnoRepository.findById(alumnoAsignatura.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(alumnoAsignatura.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        if (alumnoAsignaturaRepository.existsByAlumnoAndAsignatura(alumno, asignatura)) {
            throw new RuntimeException("El alumno " + alumno.getCodigo()
                    + " ya esta matriculado en la asignatura " + asignatura.getCodigo());
        }

        if (!alumno.getCurso().equals(asignatura.getCurso())) {
            throw new RuntimeException("El alumno es de " + alumno.getCurso()
                    + " y la asignatura es de " + asignatura.getCurso());
        }

        validarNota(alumnoAsignatura.getNota());

        alumnoAsignatura.setAlumno(alumno);
        alumnoAsignatura.setAsignatura(asignatura);
        AlumnoAsignatura guardado = alumnoAsignaturaRepository.save(alumnoAsignatura);
        guardado.setCodigo(Constantes.PREFIJO_MATR + guardado.getId());
        return alumnoAsignaturaRepository.save(guardado);
    }

    /**
     * Actualiza la nota de una relacion alumno-asignatura existente.
     */
    public AlumnoAsignatura actualizar(Long id, AlumnoAsignatura alumnoAsignatura) {
        AlumnoAsignatura existente = buscarPorId(id);
        validarNota(alumnoAsignatura.getNota());
        existente.setNota(alumnoAsignatura.getNota());
        return alumnoAsignaturaRepository.save(existente);
    }

    /**
     * Elimina una relacion alumno-asignatura por su identificador.
     */
    public void borrar(Long id) {
        buscarPorId(id);
        alumnoAsignaturaRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    private void validarNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new RuntimeException("La nota debe estar entre 0 y 10");
        }
    }
}

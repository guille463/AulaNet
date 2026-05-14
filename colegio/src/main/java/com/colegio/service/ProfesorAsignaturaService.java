package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.model.Aula;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Servicio que gestiona la logica de negocio de las relaciones
 * profesor-asignatura.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Service
public class ProfesorAsignaturaService {

    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de relaciones profesor-asignatura.
     */
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaRepository.findAll();
    }

    /**
     * Busca una relacion profesor-asignatura por su identificador.
     */
    public ProfesorAsignatura buscarPorId(Long id) {
        return profesorAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfesorAsignatura con id: " + id + " no encontrado"));
    }

    /**
     * Devuelve las relaciones de un profesor concreto.
     */
    public List<ProfesorAsignatura> buscarPorProfesor(Long profesorId) {
        return profesorAsignaturaRepository.findByProfesorId(profesorId);
    }

    /**
     * Devuelve las relaciones de una asignatura concreta.
     */
    public List<ProfesorAsignatura> buscarPorAsignatura(Long asignaturaId) {
        return profesorAsignaturaRepository.findByAsignaturaId(asignaturaId);
    }

    public List<ProfesorAsignatura> buscarPorCursoAula(Long aulaId) {
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
        return profesorAsignaturaRepository.findByAsignaturaCurso(aula.getCurso());
    }

    public ProfesorAsignatura guardar(ProfesorAsignatura profesorAsignatura) {
        Profesor profesor = profesorRepository.findById(profesorAsignatura.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(profesorAsignatura.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        if (profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
            throw new RuntimeException("El profesor " + profesor.getCodigo()
                    + " ya imparte la asignatura " + asignatura.getCodigo());
        }

        validarHorasSemanales(profesorAsignatura.getHorasSemanales());

        profesorAsignatura.setProfesor(profesor);
        profesorAsignatura.setAsignatura(asignatura);
        return profesorAsignaturaRepository.save(profesorAsignatura);
    }

    public ProfesorAsignatura actualizar(Long id, ProfesorAsignatura profesorAsignatura) {
        ProfesorAsignatura existente = buscarPorId(id);

        Profesor profesor = profesorRepository.findById(profesorAsignatura.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(profesorAsignatura.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        boolean profesorCambia = false;
        if (!existente.getProfesor().getId().equals(profesor.getId())) {
            profesorCambia = true;
        }

        boolean asignaturaCambia = false;
        if (!existente.getAsignatura().getId().equals(asignatura.getId())) {
            asignaturaCambia = true;
        }

        boolean combinacionCambia = false;
        if (profesorCambia || asignaturaCambia) {
            combinacionCambia = true;
        }

        if (combinacionCambia) {
            if (profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                throw new RuntimeException("El profesor " + profesor.getCodigo()
                        + " ya imparte la asignatura " + asignatura.getCodigo());
            }
        }

        validarHorasSemanales(profesorAsignatura.getHorasSemanales());

        existente.setHorasSemanales(profesorAsignatura.getHorasSemanales());
        existente.setProfesor(profesor);
        existente.setAsignatura(asignatura);
        return profesorAsignaturaRepository.save(existente);
    }

    /**
     * Elimina una relacion profesor-asignatura por su identificador.
     */
    public void borrar(Long id) {
        buscarPorId(id);
        profesorAsignaturaRepository.deleteById(id);
    }

    private void validarHorasSemanales(int horas) {
        if (horas < 1 || horas > 6) {
            throw new RuntimeException("Las horas semanales deben estar entre 1 y 6");
        }
    }
}

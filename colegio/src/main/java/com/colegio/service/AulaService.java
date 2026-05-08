package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Aula;
import com.colegio.model.Profesor;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Servicio que gestiona la logica de negocio de las aulas.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    public List<Aula> listarAulas() {
        return aulaRepository.findAll();
    }

    public Aula buscarAulaPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula con id " + id + " no encontrada"));
    }

    public Aula buscarAulaPorCodigo(String codigo) {
        return aulaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Aula " + codigo + " no encontrada"));
    }

    public List<Aula> buscarAulasPorCurso(String curso) {
        return aulaRepository.findByCurso(curso);
    }

    public List<Aula> findAulasConPlazasLibres() {
        return aulaRepository.findAulasConPlazasLibres();
    }

    public int contarAlumnos(Long aulaId) {
        buscarAulaPorId(aulaId); // verifica existencia
        return aulaRepository.countAlumnosByAulaId(aulaId);
    }

    /**
     * Crea un aula nueva. El codigo se genera como curso+grupo.
     */
    public Aula guardarAula(Aula aula) {
        aula.setCodigo(aula.getCurso() + aula.getGrupo());
        if (aulaRepository.existsByCodigo(aula.getCodigo())) {
            throw new RuntimeException("Ya existe el aula " + aula.getCodigo());
        }
        return aulaRepository.save(aula);
    }

    public Aula actualizarAula(Long id, Aula aula) {
        Aula existente = buscarAulaPorId(id);
        existente.setCurso(aula.getCurso());
        existente.setGrupo(aula.getGrupo());
        existente.setCodigo(aula.getCurso() + aula.getGrupo());
        existente.setCapacidad(aula.getCapacidad());
        return aulaRepository.save(existente);
    }

    public void borrarAula(Long id) {
        buscarAulaPorId(id);
        aulaRepository.deleteById(id);
    }

    // ============================================================
    // GESTION DE TUTOR
    // ============================================================
    /**
     * Asigna un tutor a un aula. Un profesor solo puede ser tutor de un aula a
     * la vez.
     */
    public Aula asignarTutor(Long aulaId, Long profesorId) {
        Aula aula = buscarAulaPorId(aulaId);
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor con id " + profesorId + " no encontrado"));

        if (aulaRepository.existsByTutor(profesor)) {
            throw new RuntimeException("El profesor " + profesor.getCodigo()
                    + " ya es tutor de otra aula");
        }

        aula.setTutor(profesor);
        return aulaRepository.save(aula);
    }

    /**
     * Elimina el tutor asignado a un aula.
     */
    public Aula eliminarTutor(Long aulaId) {
        Aula aula = buscarAulaPorId(aulaId);
        aula.setTutor(null);
        return aulaRepository.save(aula);
    }
}

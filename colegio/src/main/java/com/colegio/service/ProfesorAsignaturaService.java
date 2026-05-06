package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Asignatura;
import com.colegio.entity.Profesor;
import com.colegio.entity.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;

@Service
public class ProfesorAsignaturaService {

    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaRepository.findAll();
    }

    public ProfesorAsignatura buscarPorId(Long id) {
        return profesorAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfesorAsignatura con id: " + id + " no encontrado"));
    }

    public ProfesorAsignatura guardar(ProfesorAsignatura profesorAsignatura) {
        Profesor profesor = profesorRepository.findById(profesorAsignatura.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        Asignatura asignatura = asignaturaRepository.findById(profesorAsignatura.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        profesorAsignatura.setProfesor(profesor);
        profesorAsignatura.setAsignatura(asignatura);
        ProfesorAsignatura guardado = profesorAsignaturaRepository.save(profesorAsignatura);
        return profesorAsignaturaRepository.save(guardado);
    }

    public ProfesorAsignatura actualizar(Long id, ProfesorAsignatura profesorAsignatura) {
        ProfesorAsignatura existente = buscarPorId(id);
        existente.setCurso(profesorAsignatura.getCurso());
        existente.setHorasSemanales(profesorAsignatura.getHorasSemanales());
        existente.setProfesor(profesorAsignatura.getProfesor());
        existente.setAsignatura(profesorAsignatura.getAsignatura());
        return profesorAsignaturaRepository.save(existente);
    }

    public void borrar(Long id) {
        profesorAsignaturaRepository.deleteById(id);
    }
}

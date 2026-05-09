package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las asignaturas.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    public List<Asignatura> listarAsignaturas() {
        return asignaturaRepository.findAll();
    }

    public Asignatura buscarAsignaturaPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura con id " + id + " no encontrada"));
    }

    public List<Asignatura> buscarAsignaturasPorCurso(String curso) {
        return asignaturaRepository.findByCurso(curso);
    }

    /**
     * Guarda una nueva asignatura y le asigna su codigo.
     */
    public Asignatura guardarAsignatura(Asignatura asignatura) {
        if (asignaturaRepository.existsByNombreAndCurso(
                asignatura.getNombre(), asignatura.getCurso())) {
            throw new RuntimeException("Ya existe la asignatura '"
                    + asignatura.getNombre() + "' en el curso " + asignatura.getCurso());
        }
        Asignatura guardada = asignaturaRepository.save(asignatura);
        guardada.setCodigo(Constantes.PREFIJO_ASIG + guardada.getId());
        return asignaturaRepository.save(guardada);
    }

    public Asignatura actualizarAsignatura(Long id, Asignatura asignatura) {
        Asignatura existente = buscarAsignaturaPorId(id);
        existente.setNombre(asignatura.getNombre());
        existente.setDescripcion(asignatura.getDescripcion());
        existente.setCurso(asignatura.getCurso());
        existente.setHorasSemana(asignatura.getHorasSemana());
        return asignaturaRepository.save(existente);
    }

    public void borrarAsignatura(Long id) {
        buscarAsignaturaPorId(id);
        asignaturaRepository.deleteById(id);
    }
}

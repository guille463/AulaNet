package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Servicio que gestiona la logica de negocio de las relaciones
 * profesor-asignatura.
 *
 * <p>
 * Actua como intermediario entre {@link ProfesorAsignaturaController} y
 * {@link ProfesorAsignaturaRepository}. Gestiona la relacion N:M entre
 * {@link Profesor} y {@link Asignatura} a traves de {@link ProfesorAsignatura}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class ProfesorAsignaturaService {

    /**
     * Repositorio para acceder a los datos de {@link ProfesorAsignatura}
     */
    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    /**
     * Repositorio para verificar y cargar datos de {@link Profesor}
     */
    @Autowired
    private ProfesorRepository profesorRepository;

    /**
     * Repositorio para verificar y cargar datos de {@link Asignatura}
     */
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de relaciones profesor-asignatura.
     *
     * @return lista de {@link ProfesorAsignatura}
     */
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaRepository.findAll();
    }

    /**
     * Busca una relacion profesor-asignatura por su identificador.
     *
     * @param id identificador de la relacion
     * @return {@link ProfesorAsignatura} encontrada
     * @throws RuntimeException si la relacion no existe
     */
    public ProfesorAsignatura buscarPorId(Long id) {
        return profesorAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfesorAsignatura con id: " + id + " no encontrado"));
    }

    /**
     * Guarda una nueva relacion profesor-asignatura cargando el profesor y la
     * asignatura completos.
     *
     * <p>
     * Verifica que el {@link Profesor} y la {@link Asignatura} existan antes de
     * guardar.
     * </p>
     *
     * @param profesorAsignatura datos de la relacion a guardar
     * @return relacion guardada
     * @throws RuntimeException si el profesor o la asignatura no existen
     */
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

    /**
     * Actualiza los datos de una relacion profesor-asignatura existente.
     *
     * @param id identificador de la relacion a actualizar
     * @param profesorAsignatura datos nuevos de la relacion
     * @return relacion actualizada
     * @throws RuntimeException si la relacion no existe
     */
    public ProfesorAsignatura actualizar(Long id, ProfesorAsignatura profesorAsignatura) {
        ProfesorAsignatura existente = buscarPorId(id);
        existente.setCurso(profesorAsignatura.getCurso());
        existente.setHorasSemanales(profesorAsignatura.getHorasSemanales());
        existente.setProfesor(profesorAsignatura.getProfesor());
        existente.setAsignatura(profesorAsignatura.getAsignatura());
        return profesorAsignaturaRepository.save(existente);
    }

    /**
     * Elimina una relacion profesor-asignatura por su identificador.
     *
     * @param id identificador de la relacion a eliminar
     */
    public void borrar(Long id) {
        profesorAsignaturaRepository.deleteById(id);
    }
}

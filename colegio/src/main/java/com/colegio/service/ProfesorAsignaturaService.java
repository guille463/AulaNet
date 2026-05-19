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
 * <p>
 * Creacion, consulta, actualizacion y borrado de {@link ProfesorAsignatura}. Al
 * guardar valida que no exista duplicado y que las horas semanales esten en el
 * rango permitido.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see ProfesorAsignatura
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
     * Devuelve todas las relaciones profesor-asignatura registradas.
     *
     * @return lista de relaciones
     */
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaRepository.findAll();
    }

    /**
     * Devuelve la relacion con el id indicado.
     *
     * @param id id de la relacion
     * @return relacion encontrada
     * @throws RuntimeException si no existe una relacion con ese id
     */
    public ProfesorAsignatura buscarPorId(Long id) {
        return profesorAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfesorAsignatura con id: " + id + " no encontrado"));
    }

    /**
     * Devuelve las relaciones del profesor con el id indicado.
     *
     * @param profesorId id del profesor
     * @return lista de relaciones del profesor
     */
    public List<ProfesorAsignatura> buscarPorProfesor(Long profesorId) {
        return profesorAsignaturaRepository.findByProfesorId(profesorId);
    }

    /**
     * Devuelve las relaciones de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de relaciones de la asignatura
     */
    public List<ProfesorAsignatura> buscarPorAsignatura(Long asignaturaId) {
        return profesorAsignaturaRepository.findByAsignaturaId(asignaturaId);
    }

    /**
     * Devuelve las relaciones cuya asignatura pertenece al curso del aula
     * indicada.
     *
     * @param aulaId id del aula
     * @return lista de relaciones del curso del aula
     * @throws RuntimeException si el aula no existe
     */
    public List<ProfesorAsignatura> buscarPorCursoAula(Long aulaId) {
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
        return profesorAsignaturaRepository.findByAsignaturaCurso(aula.getCurso());
    }

    /**
     * Guarda una nueva relacion profesor-asignatura.
     *
     * @param profesorAsignatura datos de la relacion a guardar
     * @return relacion guardada
     * @throws RuntimeException si el profesor o la asignatura no existen, si la
     * relacion ya existe, o si las horas semanales no estan entre 1 y 6
     */
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

    /**
     * Actualiza los datos de una relacion profesor-asignatura existente.
     *
     * <p>
     * Si cambia el profesor o la asignatura, verifica que la nueva combinacion
     * no este duplicada.</p>
     *
     * @param id id de la relacion a actualizar
     * @param profesorAsignatura nuevos datos de la relacion
     * @return relacion actualizada
     * @throws RuntimeException si la relacion, el profesor o la asignatura no
     * existen, si la nueva combinacion ya existe, o si las horas semanales no
     * estan entre 1 y 6
     */
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
     * Borra la relacion con el id indicado.
     *
     * @param id id de la relacion a borrar
     * @throws RuntimeException si la relacion no existe
     */
    public void borrar(Long id) {
        buscarPorId(id);
        profesorAsignaturaRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Valida que las horas semanales esten en el rango permitido.
     *
     * @param horas horas semanales a validar
     * @throws RuntimeException si las horas no estan entre 1 y 6
     */
    private void validarHorasSemanales(int horas) {
        if (horas < 1 || horas > 6) {
            throw new RuntimeException("Las horas semanales deben estar entre 1 y 6");
        }
    }
}

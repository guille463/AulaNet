package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.model.Profesor;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Servicio que gestiona la logica de negocio de las aulas.
 *
 * <p>
 * Coordina la creacion, consulta, actualizacion y borrado de {@link Aula}. Al
 * guardar valida que la capacidad este en el rango permitido y que el codigo no
 * este duplicado. Gestiona tambien la asignacion de tutores.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see Aula
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
    /**
     * Devuelve todas las aulas registradas.
     *
     * @return lista de aulas
     */
    public List<Aula> listarAulas() {
        return aulaRepository.findAll();
    }

    /**
     * Devuelve el aula con el id indicado.
     *
     * @param id id del aula
     * @return aula encontrada
     * @throws RuntimeException si no existe un aula con ese id
     */
    public Aula buscarAulaPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula con id " + id + " no encontrada"));
    }

    /**
     * Devuelve el aula con el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return aula encontrada
     * @throws RuntimeException si no existe un aula con ese codigo
     */
    public Aula buscarAulaPorCodigo(String codigo) {
        return aulaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Aula " + codigo + " no encontrada"));
    }

    /**
     * Devuelve las aulas del curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de aulas del curso
     */
    public List<Aula> buscarAulasPorCurso(Curso curso) {
        return aulaRepository.findByCurso(curso);
    }

    /**
     * Devuelve las aulas con plazas libres.
     *
     * @return lista de aulas con plazas disponibles
     */
    public List<Aula> findAulasConPlazasLibres() {
        return aulaRepository.findAulasConPlazasLibres();
    }

    /**
     * Devuelve el numero de alumnos asignados al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return numero de alumnos del aula
     * @throws RuntimeException si el aula no existe
     */
    public int contarAlumnos(Long aulaId) {
        buscarAulaPorId(aulaId);
        return aulaRepository.countAlumnosByAulaId(aulaId);
    }

    /**
     * Guarda un nueva aula verificando que el codigo no este duplicado.
     *
     * <p>
     * El codigo se genera automaticamente como la etiqueta del curso mas la
     * etiqueta del grupo.
     * </p>
     *
     * @param aula datos del aula a guardar
     * @return aula guardada
     * @throws RuntimeException si ya existe un aula con ese codigo o si la
     *                          capacidad no esta entre 15 y 30
     */
    public Aula guardarAula(Aula aula) {
        validarCapacidad(aula.getCapacidad());
        aula.setCodigo(aula.getCurso().getEtiqueta() + aula.getGrupo().getEtiqueta());
        if (aulaRepository.existsByCodigo(aula.getCodigo())) {
            throw new RuntimeException("Ya existe el aula " + aula.getCodigo());
        }
        return aulaRepository.save(aula);
    }

    /**
     * Actualiza los datos de un aula existente.
     *
     * @param id   id del aula a actualizar
     * @param aula nuevos datos del aula
     * @return aula actualizada
     * @throws RuntimeException si el aula no existe o si la capacidad no esta
     *                          entre 15 y 30
     */
    public Aula actualizarAula(Long id, Aula aula) {
        Aula existente = buscarAulaPorId(id);
        validarCapacidad(aula.getCapacidad());
        existente.setCurso(aula.getCurso());
        existente.setGrupo(aula.getGrupo());
        existente.setCodigo(aula.getCurso().getEtiqueta() + aula.getGrupo().getEtiqueta());
        existente.setCapacidad(aula.getCapacidad());
        return aulaRepository.save(existente);
    }

    /**
     * Borra el aula con el id indicado.
     *
     * @param id id del aula a borrar
     * @throws RuntimeException si el aula no existe
     */
    public void borrarAula(Long id) {
        buscarAulaPorId(id);
        aulaRepository.deleteById(id);
    }

    // ============================================================
    // GESTION DE TUTOR
    // ============================================================
    /**
     * Asigna un tutor a un aula.
     *
     * <p>
     * Un profesor solo puede ser tutor de un aula a la vez.
     * </p>
     *
     * @param aulaId     id del aula
     * @param profesorId id del profesor a asignar como tutor
     * @return aula con el tutor asignado
     * @throws RuntimeException si el aula o el profesor no existen, o si el
     *                          profesor ya es tutor de otra aula
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
     * Elimina el tutor asignado al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return aula sin tutor asignado
     * @throws RuntimeException si el aula no existe
     */
    public Aula eliminarTutor(Long aulaId) {
        Aula aula = buscarAulaPorId(aulaId);
        aula.setTutor(null);
        return aulaRepository.save(aula);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Valida que la capacidad este en el rango permitido.
     *
     * @param capacidad capacidad a validar
     * @throws RuntimeException si la capacidad no esta entre 15 y 30
     */
    private void validarCapacidad(int capacidad) {
        if (capacidad < 15 || capacidad > 30) {
            throw new RuntimeException("La capacidad del aula debe estar entre 15 y 30");
        }
    }
}

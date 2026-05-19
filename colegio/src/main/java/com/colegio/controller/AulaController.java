package com.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.service.AulaService;

/**
 * Controlador REST para la gestion de aulas.
 *
 * <p>
 * Expone los endpoints bajo {@code /api/v1/aulas} y delega toda la logica de
 * negocio en {@link AulaService}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see AulaService
 */
@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    /**
     * Devuelve todas las aulas registradas.
     *
     * @return lista de aulas
     */
    @GetMapping
    public List<Aula> listar() {
        return aulaService.listarAulas();
    }

    /**
     * Devuelve el aula con el id indicado.
     *
     * @param id id del aula
     * @return aula encontrada
     */
    @GetMapping("/{id}")
    public Aula obtenerPorId(@PathVariable Long id) {
        return aulaService.buscarAulaPorId(id);
    }

    /**
     * Devuelve el aula con el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return aula encontrada
     */
    @GetMapping("/codigo/{codigo}")
    public Aula obtenerPorCodigo(@PathVariable String codigo) {
        return aulaService.buscarAulaPorCodigo(codigo);
    }

    /**
     * Devuelve las aulas del curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de aulas del curso
     */
    @GetMapping("/curso/{curso}")
    public List<Aula> obtenerPorCurso(@PathVariable Curso curso) {
        return aulaService.buscarAulasPorCurso(curso);
    }

    /**
     * Devuelve las aulas con plazas libres.
     *
     * @return lista de aulas con plazas disponibles
     */
    @GetMapping("/disponibles")
    public List<Aula> obtenerDisponibles() {
        return aulaService.findAulasConPlazasLibres();
    }

    /**
     * Devuelve el numero de alumnos del aula con el id indicado.
     *
     * @param id id del aula
     * @return numero de alumnos del aula
     */
    @GetMapping("/{id}/alumnos/count")
    public int contarAlumnos(@PathVariable Long id) {
        return aulaService.contarAlumnos(id);
    }

    /**
     * Guarda un nueva aula.
     *
     * @param aula datos del aula a guardar
     * @return aula guardada
     */
    @PostMapping
    public Aula guardar(@RequestBody Aula aula) {
        return aulaService.guardarAula(aula);
    }

    /**
     * Actualiza los datos de un aula existente.
     *
     * @param id id del aula a actualizar
     * @param aula nuevos datos del aula
     * @return aula actualizada
     */
    @PutMapping("/{id}")
    public Aula actualizar(@PathVariable Long id, @RequestBody Aula aula) {
        return aulaService.actualizarAula(id, aula);
    }

    /**
     * Borra el aula con el id indicado.
     *
     * @param id id del aula a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        aulaService.borrarAula(id);
    }

    /**
     * Asigna un tutor al aula indicada.
     *
     * @param aulaId id del aula
     * @param profesorId id del profesor a asignar como tutor
     * @return aula con el tutor asignado
     */
    @PutMapping("/{aulaId}/tutor/{profesorId}")
    public Aula asignarTutor(@PathVariable Long aulaId, @PathVariable Long profesorId) {
        return aulaService.asignarTutor(aulaId, profesorId);
    }

    /**
     * Elimina el tutor del aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return aula sin tutor asignado
     */
    @DeleteMapping("/{aulaId}/tutor")
    public Aula eliminarTutor(@PathVariable Long aulaId) {
        return aulaService.eliminarTutor(aulaId);
    }
}

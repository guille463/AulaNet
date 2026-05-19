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

import com.colegio.model.ProfesorAsignatura;
import com.colegio.service.ProfesorAsignaturaService;

/**
 * Controlador REST para la gestion de relaciones profesor-asignatura.
 *
 * <p>
 * Expone los endpoints bajo {@code /api/v1/profesor-asignatura} y delega toda
 * la logica de negocio en {@link ProfesorAsignaturaService}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see ProfesorAsignaturaService
 */
@RestController
@RequestMapping("/api/v1/profesor-asignatura")
public class ProfesorAsignaturaController {

    @Autowired
    private ProfesorAsignaturaService profesorAsignaturaService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve todas las relaciones profesor-asignatura registradas.
     *
     * @return lista de relaciones
     */
    @GetMapping
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaService.listar();
    }

    /**
     * Devuelve la relacion con el id indicado.
     *
     * @param id id de la relacion
     * @return relacion encontrada
     */
    @GetMapping("/{id}")
    public ProfesorAsignatura obtenerPorId(@PathVariable Long id) {
        return profesorAsignaturaService.buscarPorId(id);
    }

    /**
     * Devuelve las relaciones del profesor con el id indicado.
     *
     * @param profesorId id del profesor
     * @return lista de relaciones del profesor
     */
    @GetMapping("/profesor/{profesorId}")
    public List<ProfesorAsignatura> obtenerPorProfesor(@PathVariable Long profesorId) {
        return profesorAsignaturaService.buscarPorProfesor(profesorId);
    }

    /**
     * Devuelve las relaciones de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de relaciones de la asignatura
     */
    @GetMapping("/asignatura/{asignaturaId}")
    public List<ProfesorAsignatura> obtenerPorAsignatura(@PathVariable Long asignaturaId) {
        return profesorAsignaturaService.buscarPorAsignatura(asignaturaId);
    }

    /**
     * Devuelve las relaciones cuya asignatura pertenece al curso del aula
     * indicada.
     *
     * @param aulaId id del aula
     * @return lista de relaciones del curso del aula
     */
    @GetMapping("/aula/{aulaId}")
    public List<ProfesorAsignatura> obtenerPorAula(@PathVariable Long aulaId) {
        return profesorAsignaturaService.buscarPorCursoAula(aulaId);
    }

    /**
     * Guarda una nueva relacion profesor-asignatura.
     *
     * @param profesorAsignatura datos de la relacion a guardar
     * @return relacion guardada
     */
    @PostMapping
    public ProfesorAsignatura guardar(@RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.guardar(profesorAsignatura);
    }

    /**
     * Actualiza los datos de una relacion profesor-asignatura existente.
     *
     * @param id id de la relacion a actualizar
     * @param profesorAsignatura nuevos datos de la relacion
     * @return relacion actualizada
     */
    @PutMapping("/{id}")
    public ProfesorAsignatura actualizar(@PathVariable Long id,
            @RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.actualizar(id, profesorAsignatura);
    }

    /**
     * Borra la relacion con el id indicado.
     *
     * @param id id de la relacion a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorAsignaturaService.borrar(id);
    }
}

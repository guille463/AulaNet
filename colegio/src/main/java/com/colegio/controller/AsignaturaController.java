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

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
import com.colegio.service.AsignaturaService;

/**
 * Controlador REST para la gestion de asignaturas.
 *
 * <p>
 * Expone los endpoints bajo {@code /api/v1/asignaturas} y delega toda la logica
 * de negocio en {@link AsignaturaService}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see AsignaturaService
 */
@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    /**
     * Devuelve todas las asignaturas registradas.
     *
     * @return lista de asignaturas
     */
    @GetMapping
    public List<Asignatura> listar() {
        return asignaturaService.listarAsignaturas();
    }

    /**
     * Devuelve la asignatura con el id indicado.
     *
     * @param id id de la asignatura
     * @return asignatura encontrada
     */
    @GetMapping("/{id}")
    public Asignatura obtenerPorId(@PathVariable Long id) {
        return asignaturaService.buscarAsignaturaPorId(id);
    }

    /**
     * Devuelve las asignaturas del curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de asignaturas del curso
     */
    @GetMapping("/curso/{curso}")
    public List<Asignatura> obtenerPorCurso(@PathVariable Curso curso) {
        return asignaturaService.buscarAsignaturasPorCurso(curso);
    }

    /**
     * Guarda una nueva asignatura.
     *
     * @param asignatura datos de la asignatura a guardar
     * @return asignatura guardada con codigo asignado
     */
    @PostMapping
    public Asignatura guardar(@RequestBody Asignatura asignatura) {
        return asignaturaService.guardarAsignatura(asignatura);
    }

    /**
     * Actualiza los datos de una asignatura existente.
     *
     * @param id         id de la asignatura a actualizar
     * @param asignatura nuevos datos de la asignatura
     * @return asignatura actualizada
     */
    @PutMapping("/{id}")
    public Asignatura actualizar(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return asignaturaService.actualizarAsignatura(id, asignatura);
    }

    /**
     * Borra la asignatura con el id indicado.
     *
     * @param id id de la asignatura a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        asignaturaService.borrarAsignatura(id);
    }
}

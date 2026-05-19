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

import com.colegio.model.AlumnoAsignatura;
import com.colegio.service.AlumnoAsignaturaService;

/**
 * Controlador REST para la gestion de matriculas.
 *
 * <p>
 * Endpoints{@code /api/v1/alumno-asignatura} y delega toda la logica de negocio
 * en {@link AlumnoAsignaturaService}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AlumnoAsignaturaService
 */
@RestController
@RequestMapping("/api/v1/alumno-asignatura")
public class AlumnoAsignaturaController {

    @Autowired
    private AlumnoAsignaturaService alumnoAsignaturaService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve todas las matriculas registradas.
     *
     * @return lista de matriculas
     */
    @GetMapping
    public List<AlumnoAsignatura> listar() {
        return alumnoAsignaturaService.listar();
    }

    /**
     * Devuelve la matricula con el id indicado.
     *
     * @param id id de la matricula
     * @return matricula encontrada
     */
    @GetMapping("/{id}")
    public AlumnoAsignatura obtenerPorId(@PathVariable Long id) {
        return alumnoAsignaturaService.buscarPorId(id);
    }

    /**
     * Devuelve las matriculas del alumno con el id indicado.
     *
     * @param alumnoId id del alumno
     * @return lista de matriculas del alumno
     */
    @GetMapping("/alumno/{alumnoId}")
    public List<AlumnoAsignatura> obtenerPorAlumno(@PathVariable Long alumnoId) {
        return alumnoAsignaturaService.buscarPorAlumno(alumnoId);
    }

    /**
     * Devuelve las matriculas de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de matriculas de la asignatura
     */
    @GetMapping("/asignatura/{asignaturaId}")
    public List<AlumnoAsignatura> obtenerPorAsignatura(@PathVariable Long asignaturaId) {
        return alumnoAsignaturaService.buscarPorAsignatura(asignaturaId);
    }

    /**
     * Guarda una nueva matricula.
     *
     * @param alumnoAsignatura datos de la matricula a guardar
     * @return matricula guardada con codigo asignado
     */
    @PostMapping
    public AlumnoAsignatura guardar(@RequestBody AlumnoAsignatura alumnoAsignatura) {
        return alumnoAsignaturaService.guardar(alumnoAsignatura);
    }

    /**
     * Actualiza la nota de una matricula existente.
     *
     * @param id id de la matricula a actualizar
     * @param alumnoAsignatura nuevos datos de la matricula
     * @return matricula con la nota actualizada
     */
    @PutMapping("/{id}")
    public AlumnoAsignatura actualizar(@PathVariable Long id,
            @RequestBody AlumnoAsignatura alumnoAsignatura) {
        return alumnoAsignaturaService.actualizar(id, alumnoAsignatura);
    }

    /**
     * Borra la matricula con el id indicado.
     *
     * @param id id de la matricula a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoAsignaturaService.borrar(id);
    }
}

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
 * Controlador REST para la gestion de relaciones alumno-asignatura.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
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
     * Devuelve la lista completa de relaciones alumno-asignatura.
     */
    @GetMapping
    public List<AlumnoAsignatura> listar() {
        return alumnoAsignaturaService.listar();
    }

    /**
     * Devuelve una relacion alumno-asignatura por su identificador.
     */
    @GetMapping("/{id}")
    public AlumnoAsignatura obtenerPorId(@PathVariable Long id) {
        return alumnoAsignaturaService.buscarPorId(id);
    }

    /**
     * Devuelve todas las asignaturas de un alumno concreto.
     */
    @GetMapping("/alumno/{alumnoId}")
    public List<AlumnoAsignatura> obtenerPorAlumno(@PathVariable Long alumnoId) {
        return alumnoAsignaturaService.buscarPorAlumno(alumnoId);
    }

    /**
     * Devuelve todos los alumnos de una asignatura concreta.
     */
    @GetMapping("/asignatura/{asignaturaId}")
    public List<AlumnoAsignatura> obtenerPorAsignatura(@PathVariable Long asignaturaId) {
        return alumnoAsignaturaService.buscarPorAsignatura(asignaturaId);
    }

    /**
     * Crea una nueva relacion entre un alumno y una asignatura.
     */
    @PostMapping
    public AlumnoAsignatura guardar(@RequestBody AlumnoAsignatura alumnoAsignatura) {
        return alumnoAsignaturaService.guardar(alumnoAsignatura);
    }

    /**
     * Actualiza la nota de una relacion alumno-asignatura existente.
     */
    @PutMapping("/{id}")
    public AlumnoAsignatura actualizar(@PathVariable Long id,
            @RequestBody AlumnoAsignatura alumnoAsignatura) {
        return alumnoAsignaturaService.actualizar(id, alumnoAsignatura);
    }

    /**
     * Elimina una relacion alumno-asignatura por su identificador.
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoAsignaturaService.borrar(id);
    }
}

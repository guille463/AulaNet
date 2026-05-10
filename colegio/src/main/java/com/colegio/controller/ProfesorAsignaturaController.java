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
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
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
     * Devuelve la lista completa de relaciones profesor-asignatura.
     */
    @GetMapping
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaService.listar();
    }

    /**
     * Devuelve una relacion profesor-asignatura por su identificador.
     */
    @GetMapping("/{id}")
    public ProfesorAsignatura obtenerPorId(@PathVariable Long id) {
        return profesorAsignaturaService.buscarPorId(id);
    }

    /**
     * Devuelve todas las asignaturas que imparte un profesor concreto.
     */
    @GetMapping("/profesor/{profesorId}")
    public List<ProfesorAsignatura> obtenerPorProfesor(@PathVariable Long profesorId) {
        return profesorAsignaturaService.buscarPorProfesor(profesorId);
    }

    /**
     * Devuelve todos los profesores que imparten una asignatura concreta.
     */
    @GetMapping("/asignatura/{asignaturaId}")
    public List<ProfesorAsignatura> obtenerPorAsignatura(@PathVariable Long asignaturaId) {
        return profesorAsignaturaService.buscarPorAsignatura(asignaturaId);
    }

    /**
     * Crea una nueva relacion entre un profesor y una asignatura.
     */
    @PostMapping
    public ProfesorAsignatura guardar(@RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.guardar(profesorAsignatura);
    }

    /**
     * Actualiza los datos de una relacion profesor-asignatura existente.
     */
    @PutMapping("/{id}")
    public ProfesorAsignatura actualizar(@PathVariable Long id,
            @RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.actualizar(id, profesorAsignatura);
    }

    /**
     * Elimina una relacion profesor-asignatura por su identificador.
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorAsignaturaService.borrar(id);
    }
}

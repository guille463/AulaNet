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
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/profesor-asignatura")
public class ProfesorAsignaturaController {

    /**
     * Servicio que gestiona la logica de negocio de {@link ProfesorAsignatura}
     */
    @Autowired
    private ProfesorAsignaturaService profesorAsignaturaService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve la lista completa de relaciones profesor-asignatura.
     *
     * @return lista de {@link ProfesorAsignatura} en formato JSON
     */
    @GetMapping
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaService.listar();
    }

    /**
     * Devuelve una relacion profesor-asignatura por su identificador.
     *
     * @param id identificador de la relacion
     * @return {@link ProfesorAsignatura} encontrada en formato JSON
     */
    @GetMapping("/{id}")
    public ProfesorAsignatura obtenerPorId(@PathVariable Long id) {
        return profesorAsignaturaService.buscarPorId(id);
    }

    /**
     * Crea una nueva relacion entre un profesor y una asignatura.
     *
     * @param profesorAsignatura datos de la relacion a crear
     * @return relacion creada en formato JSON
     */
    @PostMapping
    public ProfesorAsignatura guardar(@RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.guardar(profesorAsignatura);
    }

    /**
     * Actualiza los datos de una relacion profesor-asignatura existente.
     *
     * @param id identificador de la relacion a actualizar
     * @param profesorAsignatura datos nuevos de la relacion
     * @return relacion actualizada en formato JSON
     */
    @PutMapping("/{id}")
    public ProfesorAsignatura actualizar(@PathVariable Long id, @RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.actualizar(id, profesorAsignatura);
    }

    /**
     * Elimina una relacion profesor-asignatura por su identificador.
     *
     * @param id identificador de la relacion a eliminar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorAsignaturaService.borrar(id);
    }
}

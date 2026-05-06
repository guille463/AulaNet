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

import com.colegio.entity.Asignatura;
import com.colegio.service.AsignaturaService;

/**
 * Controlador REST para la gestion de asignaturas.
 *
 * <p>
 * Expone los endpoints de la API bajo {@code /api/v1/asignaturas}. Delega la
 * logica de negocio en {@link AsignaturaService}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController {

    /**
     * Servicio que gestiona la logica de negocio de {@link Asignatura}
     */
    @Autowired
    private AsignaturaService asignaturaService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve la lista completa de asignaturas.
     *
     * @return lista de {@link Asignatura} en formato JSON
     */
    @GetMapping
    public List<Asignatura> listar() {
        return asignaturaService.listarAsignaturas();
    }

    /**
     * Devuelve una asignatura por su identificador.
     *
     * @param id identificador de la asignatura
     * @return {@link Asignatura} encontrada en formato JSON
     */
    @GetMapping("/{id}")
    public Asignatura obtenerPorid(@PathVariable Long id) {
        return asignaturaService.buscarAsignaturaPorId(id);
    }

    /**
     * Crea una nueva asignatura.
     *
     * @param asignatura datos de la {@link Asignatura} a crear
     * @return asignatura creada en formato JSON
     */
    @PostMapping
    public Asignatura guardar(@RequestBody Asignatura asignatura) {
        return asignaturaService.guardarAsignatura(asignatura);
    }

    /**
     * Actualiza los datos de una asignatura existente.
     *
     * @param id identificador de la asignatura a actualizar
     * @param asignatura datos nuevos de la {@link Asignatura}
     * @return asignatura actualizada en formato JSON
     */
    @PutMapping("/{id}")
    public Asignatura actualizarAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return asignaturaService.actualizarAsignatura(id, asignatura);
    }

    /**
     * Elimina una asignatura por su identificador.
     *
     * @param id identificador de la asignatura a eliminar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        asignaturaService.borrarAsignatura(id);
    }
}

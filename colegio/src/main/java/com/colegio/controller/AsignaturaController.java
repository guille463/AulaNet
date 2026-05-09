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
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public List<Asignatura> listar() {
        return asignaturaService.listarAsignaturas();
    }

    @GetMapping("/{id}")
    public Asignatura obtenerPorId(@PathVariable Long id) {
        return asignaturaService.buscarAsignaturaPorId(id);
    }

    @GetMapping("/curso/{curso}")
    public List<Asignatura> obtenerPorCurso(@PathVariable Curso curso) {
        return asignaturaService.buscarAsignaturasPorCurso(curso);
    }

    @PostMapping
    public Asignatura guardar(@RequestBody Asignatura asignatura) {
        return asignaturaService.guardarAsignatura(asignatura);
    }

    @PutMapping("/{id}")
    public Asignatura actualizar(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return asignaturaService.actualizarAsignatura(id, asignatura);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        asignaturaService.borrarAsignatura(id);
    }
}

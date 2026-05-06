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

import com.colegio.entity.ProfesorAsignatura;
import com.colegio.service.ProfesorAsignaturaService;

@RestController
@RequestMapping("/api/v1/profesor-asignatura")
public class ProfesorAsignaturaController {

    @Autowired
    private ProfesorAsignaturaService profesorAsignaturaService;

    @GetMapping
    public List<ProfesorAsignatura> listar() {
        return profesorAsignaturaService.listar();
    }

    @GetMapping("/{id}")
    public ProfesorAsignatura obtenerPorId(@PathVariable Long id) {
        return profesorAsignaturaService.buscarPorId(id);
    }

    @PostMapping
    public ProfesorAsignatura guardar(@RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.guardar(profesorAsignatura);
    }

    @PutMapping("/{id}")
    public ProfesorAsignatura actualizar(@PathVariable Long id, @RequestBody ProfesorAsignatura profesorAsignatura) {
        return profesorAsignaturaService.actualizar(id, profesorAsignatura);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorAsignaturaService.borrar(id);
    }
}

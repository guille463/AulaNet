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

import com.colegio.model.Profesor;
import com.colegio.service.ProfesorService;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    public List<Profesor> listar() {
        return profesorService.listarProfesores();
    }

    @GetMapping("/{id}")
    public Profesor obtenerPorid(@PathVariable Long id) {
        return profesorService.buscarProfesorPorId(id);
    }

    @PostMapping
    public Profesor guardar(@RequestBody Profesor profesor) {
        return profesorService.guardarProfesor(profesor);
    }

    @PutMapping("/{id}")
    public Profesor actualizarProfesor(@RequestBody Profesor profesor, @PathVariable Long id) {
        return profesorService.actualizarProfesor(id, profesor);

    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorService.borrarProfesor(id);
    }
}

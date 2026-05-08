package com.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.model.Aula;
import com.colegio.service.AulaService;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public List<Aula> findAll() {
        return aulaService.findAll();
    }

    @GetMapping("/curso/{curso}")
    public List<Aula> findByCurso(@PathVariable String curso) {
        return aulaService.findByCurso(curso);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Aula> findByCodigo(@PathVariable String codigo) {
        return aulaService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles")
    public List<Aula> findAulasConPlazasLibres() {
        return aulaService.findAulasConPlazasLibres();
    }

    @GetMapping("/{id}/alumnos/count")
    public int countAlumnos(@PathVariable Long id) {
        return aulaService.countAlumnos(id);
    }
}

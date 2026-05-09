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

import com.colegio.model.Aula;
import com.colegio.service.AulaService;

@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public List<Aula> listar() {
        return aulaService.listarAulas();
    }

    @GetMapping("/{id}")
    public Aula obtenerPorId(@PathVariable Long id) {
        return aulaService.buscarAulaPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public Aula obtenerPorCodigo(@PathVariable String codigo) {
        return aulaService.buscarAulaPorCodigo(codigo);
    }

    @GetMapping("/curso/{curso}")
    public List<Aula> obtenerPorCurso(@PathVariable String curso) {
        return aulaService.buscarAulasPorCurso(curso);
    }

    @GetMapping("/disponibles")
    public List<Aula> obtenerDisponibles() {
        return aulaService.findAulasConPlazasLibres();
    }

    @GetMapping("/{id}/alumnos/count")
    public int contarAlumnos(@PathVariable Long id) {
        return aulaService.contarAlumnos(id);
    }

    @PostMapping
    public Aula guardar(@RequestBody Aula aula) {
        return aulaService.guardarAula(aula);
    }

    @PutMapping("/{id}")
    public Aula actualizar(@PathVariable Long id, @RequestBody Aula aula) {
        return aulaService.actualizarAula(id, aula);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        aulaService.borrarAula(id);
    }

    @PutMapping("/{aulaId}/tutor/{profesorId}")
    public Aula asignarTutor(@PathVariable Long aulaId, @PathVariable Long profesorId) {
        return aulaService.asignarTutor(aulaId, profesorId);
    }

    @DeleteMapping("/{aulaId}/tutor")
    public Aula eliminarTutor(@PathVariable Long aulaId) {
        return aulaService.eliminarTutor(aulaId);
    }
}

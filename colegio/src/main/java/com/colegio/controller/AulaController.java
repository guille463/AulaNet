package com.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * Controlador REST para la gestion de aulas. Base: /api/v1/aulas
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    // GET /api/v1/aulas
    @GetMapping
    public ResponseEntity<List<Aula>> listar() {
        return ResponseEntity.ok(aulaService.listarAulas());
    }

    // GET /api/v1/aulas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Aula> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.buscarAulaPorId(id));
    }

    // GET /api/v1/aulas/codigo/{codigo}
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Aula> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(aulaService.buscarAulaPorCodigo(codigo));
    }

    // GET /api/v1/aulas/curso/{curso}
    @GetMapping("/curso/{curso}")
    public ResponseEntity<List<Aula>> obtenerPorCurso(@PathVariable String curso) {
        return ResponseEntity.ok(aulaService.buscarAulasPorCurso(curso));
    }

    // GET /api/v1/aulas/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Aula>> obtenerDisponibles() {
        return ResponseEntity.ok(aulaService.findAulasConPlazasLibres());
    }

    // GET /api/v1/aulas/{id}/alumnos/count
    @GetMapping("/{id}/alumnos/count")
    public ResponseEntity<Integer> contarAlumnos(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.contarAlumnos(id));
    }

    // POST /api/v1/aulas
    @PostMapping
    public ResponseEntity<Aula> guardar(@RequestBody Aula aula) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aulaService.guardarAula(aula));
    }

    // PUT /api/v1/aulas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Aula> actualizar(@PathVariable Long id,
            @RequestBody Aula aula) {
        return ResponseEntity.ok(aulaService.actualizarAula(id, aula));
    }

    // DELETE /api/v1/aulas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        aulaService.borrarAula(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/v1/aulas/{aulaId}/tutor/{profesorId}
    @PutMapping("/{aulaId}/tutor/{profesorId}")
    public ResponseEntity<Aula> asignarTutor(@PathVariable Long aulaId,
            @PathVariable Long profesorId) {
        return ResponseEntity.ok(aulaService.asignarTutor(aulaId, profesorId));
    }

    // DELETE /api/v1/aulas/{aulaId}/tutor
    @DeleteMapping("/{aulaId}/tutor")
    public ResponseEntity<Aula> eliminarTutor(@PathVariable Long aulaId) {
        return ResponseEntity.ok(aulaService.eliminarTutor(aulaId));
    }
}

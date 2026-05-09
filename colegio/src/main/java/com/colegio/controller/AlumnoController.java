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

import com.colegio.model.Alumno;
import com.colegio.model.Curso;
import com.colegio.service.AlumnoService;

/**
 * Controlador REST para la gestion de alumnos.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> listar() {
        return alumnoService.listarAlumnos();
    }

    @GetMapping("/{id}")
    public Alumno obtenerPorId(@PathVariable Long id) {
        return alumnoService.buscarPorId(id);
    }

    @GetMapping("/email/{email}")
    public Alumno obtenerPorEmail(@PathVariable String email) {
        return alumnoService.buscarPorEmail(email);
    }

    @GetMapping("/aula/{aulaId}")
    public List<Alumno> obtenerPorAula(@PathVariable Long aulaId) {
        return alumnoService.buscarPorAula(aulaId);
    }

    @GetMapping("/curso/{curso}")
    public List<Alumno> obtenerPorCurso(@PathVariable Curso curso) {
        return alumnoService.buscarPorCurso(curso);
    }

    @GetMapping("/buscar/{nombre}")
    public List<Alumno> buscarPorNombre(@PathVariable String nombre) {
        return alumnoService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/{nombre}/{apellido}")
    public List<Alumno> buscarPorNombreYApellido(@PathVariable String nombre,
            @PathVariable String apellido) {
        return alumnoService.buscarPorNombreYApellido(nombre, apellido);
    }

    @PostMapping
    public Alumno guardar(@RequestBody Alumno alumno) {
        return alumnoService.guardarAlumno(alumno);
    }

    @PutMapping("/{id}")
    public Alumno actualizar(@PathVariable Long id, @RequestBody Alumno alumno) {
        return alumnoService.actualizarAlumno(id, alumno);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoService.borrarAlumno(id);
    }
}

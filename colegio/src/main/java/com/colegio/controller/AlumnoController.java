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
import com.colegio.service.AlumnoService;

/**
 * Controlador REST para la gestion de alumnos.
 *
 * <p>
 * Expone los endpoints de la API bajo {@code /api/v1/alumnos}. Delega la logica
 * de negocio en {@link AlumnoService}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    /**
     * Servicio que gestiona la logica de negocio de {@link Alumno}
     */
    @Autowired
    private AlumnoService alumnoService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve la lista completa de alumnos.
     *
     * @return lista de {@link Alumno} en formato JSON
     */
    @GetMapping
    public List<Alumno> listar() {
        return alumnoService.ListarAlumnos();
    }

    /**
     * Devuelve un alumno por su identificador.
     *
     * @param id identificador del alumno
     * @return {@link Alumno} encontrado en formato JSON
     */
    @GetMapping("/{id}")
    public Alumno obtenerPorid(@PathVariable Long id) {
        return alumnoService.buscarPorid(id);
    }

    @GetMapping("/email/{email}")
    public Alumno obtenerAlumnoPorEmail(@PathVariable String email) {
        return alumnoService.buscarAlumnoPorEmail(email);
    }

    /**
     * Crea un nuevo alumno.
     *
     * @param alumno datos del {@link Alumno} a crear
     * @return alumno creado en formato JSON
     */
    @PostMapping
    public Alumno guardar(@RequestBody Alumno alumno) {
        return alumnoService.guardarAlumno(alumno);
    }

    /**
     * Actualiza los datos de un alumno existente.
     *
     * @param id identificador del alumno a actualizar
     * @param alumno datos nuevos del {@link Alumno}
     * @return alumno actualizado en formato JSON
     */
    @PutMapping("/{id}")
    public Alumno actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        return alumnoService.actualizarAlumno(id, alumno);
    }

    /**
     * Elimina un alumno por su identificador.
     *
     * @param id identificador del alumno a eliminar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoService.borrarAlumno(id);
    }

}

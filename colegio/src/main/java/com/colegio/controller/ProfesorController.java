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

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.service.ProfesorService;

/**
 * Controlador REST para la gestion de profesores.
 *
 * <p>
 * Expone los endpoints bajo {@code /api/v1/profesores} y delega toda la logica
 * de negocio en {@link ProfesorService}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see ProfesorService
 */
@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    /**
     * Devuelve todos los profesores registrados.
     *
     * @return lista de profesores
     */
    @GetMapping
    public List<Profesor> listar() {
        return profesorService.listarProfesores();
    }

    /**
     * Devuelve el profesor con el id indicado.
     *
     * @param id id del profesor
     * @return profesor encontrado
     */
    @GetMapping("/{id}")
    public Profesor obtenerPorId(@PathVariable Long id) {
        return profesorService.buscarProfesorPorId(id);
    }

    /**
     * Devuelve los profesores cuyo nombre contiene la cadena indicada.
     *
     * @param nombre fragmento del nombre a buscar
     * @return lista de profesores que coinciden
     */
    @GetMapping("/buscar/{nombre}")
    public List<Profesor> buscarPorNombre(@PathVariable String nombre) {
        return profesorService.buscarPorNombre(nombre);
    }

    /**
     * Devuelve el profesor con el email indicado.
     *
     * @param email email del profesor
     * @return profesor encontrado
     */
    @GetMapping("/email/{email}")
    public Profesor obtenerPorEmail(@PathVariable String email) {
        return profesorService.buscarProfesorPorEmail(email);
    }

    /**
     * Guarda un nuevo profesor.
     *
     * @param profesor datos del profesor a guardar
     * @return profesor guardado con codigo y asignaturas asignadas
     */
    @PostMapping
    public Profesor guardar(@RequestBody Profesor profesor) {
        return profesorService.guardarProfesor(profesor);
    }

    /**
     * Actualiza los datos de un profesor existente.
     *
     * @param id id del profesor a actualizar
     * @param profesor nuevos datos del profesor
     * @return profesor actualizado
     */
    @PutMapping("/{id}")
    public Profesor actualizar(@PathVariable Long id, @RequestBody Profesor profesor) {
        return profesorService.actualizarProfesor(id, profesor);
    }

    /**
     * Borra el profesor con el id indicado.
     *
     * @param id id del profesor a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        profesorService.borrarProfesor(id);
    }

    /**
     * Devuelve los profesores con la especialidad indicada.
     *
     * @param especialidad especialidad por la que filtrar
     * @return lista de profesores con esa especialidad
     */
    @GetMapping("/especialidad/{especialidad}")
    public List<Profesor> obtenerPorEspecialidad(@PathVariable Especialidad especialidad) {
        return profesorService.buscarPorEspecialidad(especialidad);
    }
}

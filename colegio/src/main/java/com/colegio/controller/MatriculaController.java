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

import com.colegio.model.Matricula;
import com.colegio.service.MatriculaService;

/**
 * Controlador REST para la gestion de matriculas.
 *
 * <p>
 * Expone los endpoints de la API bajo {@code /api/v1/matriculas}. Delega la
 * logica de negocio en {@link MatriculaService}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    /**
     * Servicio que gestiona la logica de negocio de {@link Matricula}
     */
    @Autowired
    private MatriculaService matriculaService;

    // ============================================================
    // ENDPOINTS
    // ============================================================
    /**
     * Devuelve la lista completa de matriculas.
     *
     * @return lista de {@link Matricula} en formato JSON
     */
    @GetMapping
    public List<Matricula> mostrarMatriculas() {
        return matriculaService.listarMatriculas();
    }

    /**
     * Devuelve una matricula por su identificador.
     *
     * @param id identificador de la matricula
     * @return {@link Matricula} encontrada en formato JSON
     */
    @GetMapping("/{id}")
    public Matricula mostrarMatriculaPorid(@PathVariable Long id) {
        return matriculaService.buscarMatriculaPorId(id);
    }

    /**
     * Crea una nueva matricula asignando un alumno y una asignatura.
     *
     * @param matricula datos de la {@link Matricula} a crear
     * @return matricula creada en formato JSON
     */
    @PostMapping
    public Matricula guardarMatricula(@RequestBody Matricula matricula) {
        return matriculaService.guardarMatricula(matricula);
    }

    /**
     * Actualiza los datos de una matricula existente.
     *
     * @param id identificador de la matricula a actualizar
     * @param matricula datos nuevos de la {@link Matricula}
     * @return matricula actualizada en formato JSON
     */
    @PutMapping("/{id}")
    public Matricula actualizarMatricula(@PathVariable Long id, @RequestBody Matricula matricula) {
        return matriculaService.actualizarMatricula(id, matricula);
    }

    /**
     * Elimina una matricula por su identificador.
     *
     * @param id identificador de la matricula a eliminar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        matriculaService.borrarmatricula(id);
    }
}

package com.colegio.controller;

import java.time.LocalDate;
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
 * <p>
 * Endpoints {@code /api/v1/alumnos} y delega toda la logica de negocio en
 * {@link AlumnoService}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see AlumnoService
 */
@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    /**
     * Devuelve todos los alumnos registrados.
     *
     * @return lista de alumnos
     */
    @GetMapping
    public List<Alumno> listar() {
        return alumnoService.listarAlumnos();
    }

    /**
     * Devuelve el alumno con el id indicado.
     *
     * @param id id del alumno
     * @return alumno encontrado
     */
    @GetMapping("/{id}")
    public Alumno obtenerPorId(@PathVariable Long id) {
        return alumnoService.buscarPorId(id);
    }

    /**
     * Devuelve los alumnos nacidos en la fecha indicada.
     *
     * @param fecha fecha de nacimiento a buscar
     * @return lista de alumnos con esa fecha de nacimiento
     */
    @GetMapping("/fecha/{fecha}")
    public List<Alumno> obtenerPorFechaNacimiento(@PathVariable LocalDate fecha) {
        return alumnoService.buscarPorFechaNacimiento(fecha);
    }

    /**
     * Devuelve los alumnos asignados al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return lista de alumnos del aula
     */
    @GetMapping("/aula/{aulaId}")
    public List<Alumno> obtenerPorAula(@PathVariable Long aulaId) {
        return alumnoService.buscarPorAula(aulaId);
    }

    /**
     * Devuelve los alumnos cuya aula pertenece al curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de alumnos del curso
     */
    @GetMapping("/curso/{curso}")
    public List<Alumno> obtenerPorCurso(@PathVariable Curso curso) {
        return alumnoService.buscarPorCurso(curso);
    }

    /**
     * Devuelve los alumnos cuyo nombre contiene la cadena indicada.
     *
     * @param nombre fragmento del nombre a buscar
     * @return lista de alumnos que coinciden
     */
    @GetMapping("/buscar/{nombre}")
    public List<Alumno> buscarPorNombre(@PathVariable String nombre) {
        return alumnoService.buscarPorNombre(nombre);
    }

    /**
     * Devuelve los alumnos que coinciden con el nombre y apellido indicados.
     *
     * @param nombre   nombre del alumno
     * @param apellido apellido del alumno
     * @return lista de alumnos que coinciden
     */
    @GetMapping("/buscar/{nombre}/{apellido}")
    public List<Alumno> buscarPorNombreYApellido(@PathVariable String nombre,
            @PathVariable String apellido) {
        return alumnoService.buscarPorNombreYApellido(nombre, apellido);
    }

    /**
     * Devuelve los alumnos cuya aula tiene el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return lista de alumnos del aula
     */
    @GetMapping("/aula/codigo/{codigo}")
    public List<Alumno> obtenerPorCodigoAula(@PathVariable String codigo) {
        return alumnoService.buscarPorCodigoAula(codigo);
    }

    /**
     * Guarda un nuevo alumno.
     *
     * @param alumno datos del alumno a guardar
     * @return alumno guardado con codigo y matriculas asignadas
     */
    @PostMapping
    public Alumno guardar(@RequestBody Alumno alumno) {
        return alumnoService.guardarAlumno(alumno);
    }

    /**
     * Actualiza los datos de un alumno existente.
     *
     * @param id     id del alumno a actualizar
     * @param alumno nuevos datos del alumno
     * @return alumno actualizado
     */
    @PutMapping("/{id}")
    public Alumno actualizar(@PathVariable Long id, @RequestBody Alumno alumno) {
        return alumnoService.actualizarAlumno(id, alumno);
    }

    /**
     * Borra el alumno con el id indicado.
     *
     * @param id id del alumno a borrar
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoService.borrarAlumno(id);
    }
}

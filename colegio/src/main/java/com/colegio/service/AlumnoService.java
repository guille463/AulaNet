package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.repository.AlumnoRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los alumnos.
 *
 * <p>
 * Actua como intermediario entre {@link AlumnoController} y
 * {@link AlumnoRepository}. Contiene las reglas de negocio y operaciones CRUD
 * sobre {@link Alumno}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class AlumnoService {

    /**
     * Repositorio para acceder a los datos de {@link Alumno}
     */
    @Autowired
    private AlumnoRepository alumnoRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de alumnos.
     *
     * @return lista de {@link Alumno}
     */
    public List<Alumno> ListarAlumnos() {
        return alumnoRepository.findAll();
    }

    /**
     * Busca un alumno por su identificador.
     *
     * @param id identificador del alumno
     * @return {@link Alumno} encontrado
     * @throws RuntimeException si el alumno no existe
     */
    public Alumno buscarPorid(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno con id: " + id + " no encontrado"));
    }

    public Alumno buscarAlumnoPorEmail(String email) {
        return alumnoRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Alumno con id: " + email + " no encontrado"));
    }

    public Alumno buscarAlumnoPorNombre(String nombre) {
        return alumnoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Alumno con nombre: " + nombre + " no encontrado"));
    }

    public List<Alumno> buscarAlumnosPorCurso(String curso) {
        return alumnoRepository.findByCurso(curso);
    }

    public List<Alumno> buscarAlumnosPorNombreContaining(String nombre) {
        return alumnoRepository.findByNombreContaining(nombre);
    }

    public List<Alumno> buscarAlumnosPorNombreYApellido(String nombre, String apellido) {
        return alumnoRepository.findByNombreAndApellido(nombre, apellido);
    }

    /**
     * Guarda un nuevo alumno y le asigna su codigo identificativo.
     *
     * <p>
     * El codigo se genera automaticamente con el prefijo {@code ALUM-} seguido
     * del ID generado por la base de datos.
     * </p>
     *
     * @param alumno datos del {@link Alumno} a guardar
     * @return alumno guardado con codigo asignado
     */
    public Alumno guardarAlumno(Alumno alumno) {

        alumno.setCodigo(Constantes.PREFIJO_ALUMNO + alumno.getId());
        return alumnoRepository.save(alumno);
    }

    /**
     * Actualiza los datos de un alumno existente.
     *
     * @param id identificador del alumno a actualizar
     * @param alumno datos nuevos del {@link Alumno}
     * @return alumno actualizado
     * @throws RuntimeException si el alumno no existe
     */
    public Alumno actualizarAlumno(Long id, Alumno alumno) {
        Alumno existente = buscarPorid(id);
        existente.setNombre(alumno.getNombre());
        existente.setApellido(alumno.getApellido());
        existente.setEmail(alumno.getEmail());
        existente.setFechaNac(alumno.getFechaNac());
        existente.setCurso(alumno.getCurso());
        return alumnoRepository.save(existente);
    }

    /**
     * Elimina un alumno por su identificador.
     *
     * @param id identificador del alumno a eliminar
     */
    public void borrarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    public long count() {
        return alumnoRepository.count();
    }
}

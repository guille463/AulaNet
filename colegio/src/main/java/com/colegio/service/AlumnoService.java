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
        Alumno guardado = alumnoRepository.save(alumno);
        guardado.setCodigo(Constantes.PREFIJO_ALUMNO + guardado.getId());
        return alumnoRepository.save(guardado);
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
}

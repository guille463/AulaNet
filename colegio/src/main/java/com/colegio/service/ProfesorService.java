package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Profesor;
import com.colegio.repository.ProfesorRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los profesores.
 *
 * <p>
 * Actua como intermediario entre {@link ProfesorController} y
 * {@link ProfesorRepository}. Contiene las reglas de negocio y operaciones CRUD
 * sobre {@link Profesor}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class ProfesorService {

    /**
     * Repositorio para acceder a los datos de {@link Profesor}
     */
    @Autowired
    ProfesorRepository profesorRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de profesores.
     *
     * @return lista de {@link Profesor}
     */
    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    /**
     * Busca un profesor por su identificador.
     *
     * @param id identificador del profesor
     * @return {@link Profesor} encontrado
     * @throws RuntimeException si el profesor no existe
     */
    public Profesor buscarProfesorPorId(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El profesor con id: " + id + " no existe"));
    }

    /**
     * Guarda un nuevo profesor y le asigna su codigo identificativo.
     *
     * <p>
     * El codigo se genera automaticamente con el prefijo {@code PROF-} seguido
     * del ID generado por la base de datos.
     * </p>
     *
     * @param profesor datos del {@link Profesor} a guardar
     * @return profesor guardado con codigo asignado
     */
    public Profesor guardarProfesor(Profesor profesor) {
        Profesor guardado = profesorRepository.save(profesor);
        guardado.setCodigo(Constantes.PREFIJO_PROFESOR + guardado.getId());
        return profesorRepository.save(guardado);
    }

    /**
     * Actualiza los datos de un profesor existente.
     *
     * @param id identificador del profesor a actualizar
     * @param profesor datos nuevos del {@link Profesor}
     * @return profesor actualizado
     * @throws RuntimeException si el profesor no existe
     */
    public Profesor actualizarProfesor(Long id, Profesor profesor) {
        Profesor existente = buscarProfesorPorId(id);
        existente.setNombre(profesor.getNombre());
        existente.setApellido(profesor.getApellido());
        existente.setEmail(profesor.getEmail());
        existente.setFechaNac(profesor.getFechaNac());
        return profesorRepository.save(existente);
    }

    /**
     * Elimina un profesor por su identificador.
     *
     * @param id identificador del profesor a eliminar
     */
    public void borrarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }
}

package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.model.Asignatura;
import com.colegio.model.Matricula;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.MatriculaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las matriculas.
 *
 * <p>
 * Actua como intermediario entre {@link MatriculaController} y
 * {@link MatriculaRepository}. Gestiona la relacion N:M entre {@link Alumno} y
 * {@link Asignatura} a traves de {@link Matricula}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class MatriculaService {

    /**
     * Repositorio para acceder a los datos de {@link Matricula}
     */
    @Autowired
    MatriculaRepository matriculaRepository;

    /**
     * Repositorio para verificar y cargar datos de {@link Alumno}
     */
    @Autowired
    private AlumnoRepository alumnoRepository;

    /**
     * Repositorio para verificar y cargar datos de {@link Asignatura}
     */
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de matriculas.
     *
     * @return lista de {@link Matricula}
     */
    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    /**
     * Busca una matricula por su identificador.
     *
     * @param id identificador de la matricula
     * @return {@link Matricula} encontrada
     * @throws RuntimeException si la matricula no existe
     */
    public Matricula buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Matricula con id: " + id + " no existe."));
    }

    /**
     * Guarda una nueva matricula cargando el alumno y la asignatura completos.
     *
     * <p>
     * Verifica que el {@link Alumno} y la {@link Asignatura} existan antes de
     * guardar. El codigo se genera automaticamente con el prefijo {@code MTR-}.
     * </p>
     *
     * @param matricula datos de la {@link Matricula} a guardar
     * @return matricula guardada con codigo asignado
     * @throws RuntimeException si el alumno o la asignatura no existen
     */
    public Matricula guardarMatricula(Matricula matricula) {
        Alumno alumno = alumnoRepository.findById(matricula.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        Asignatura asignatura = asignaturaRepository.findById(matricula.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        matricula.setAlumno(alumno);
        matricula.setAsignatura(asignatura);
        Matricula guardada = matriculaRepository.save(matricula);
        guardada.setCodigo(Constantes.PREFIJO_MATR + guardada.getId());
        return matriculaRepository.save(guardada);
    }

    /**
     * Actualiza los datos de una matricula existente.
     *
     * @param id identificador de la matricula a actualizar
     * @param matricula datos nuevos de la {@link Matricula}
     * @return matricula actualizada
     * @throws RuntimeException si la matricula no existe
     */
    public Matricula actualizarMatricula(Long id, Matricula matricula) {
        Matricula existente = buscarMatriculaPorId(id);
        existente.setAlumno(matricula.getAlumno());
        existente.setAsignatura(matricula.getAsignatura());
        existente.setCurso(matricula.getCurso());
        existente.setFechaMatr(matricula.getFechaMatr());
        existente.setNota(matricula.getNota());
        return matriculaRepository.save(existente);
    }

    /**
     * Elimina una matricula por su identificador.
     *
     * @param id identificador de la matricula a eliminar
     */
    public void borrarmatricula(Long id) {
        matriculaRepository.deleteById(id);
    }
}

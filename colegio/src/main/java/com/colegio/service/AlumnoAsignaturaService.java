package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.model.AlumnoAsignatura;
import com.colegio.model.Asignatura;
import com.colegio.repository.AlumnoAsignaturaRepository;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las matriculas.
 *
 * <p>
 * Creacion, consulta, actualizacion y borrado de {@link AlumnoAsignatura}. Al
 * guardar valida que el alumno y la asignatura pertenezcan al mismo curso y que
 * la nota sea valida.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AlumnoAsignatura
 */
@Service
public class AlumnoAsignaturaService {

    @Autowired
    private AlumnoAsignaturaRepository alumnoAsignaturaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AulaService aulaService;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve todas las matriculas registradas.
     *
     * @return lista de matriculas
     */
    public List<AlumnoAsignatura> listar() {
        List<AlumnoAsignatura> lista = alumnoAsignaturaRepository.findAll();
        for (AlumnoAsignatura matricula : lista) {
            rellenarTutorAula(matricula);
        }
        return lista;
    }

    /**
     * Devuelve la matricula con el id indicado.
     *
     * @param id id de la matricula
     * @return matricula encontrada
     * @throws RuntimeException si no existe una matricula con ese id
     */
    public AlumnoAsignatura buscarPorId(Long id) {
        AlumnoAsignatura matricula = alumnoAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AlumnoAsignatura con id: " + id + " no encontrado"));
        rellenarTutorAula(matricula);
        return matricula;
    }

    /**
     * Devuelve las matriculas del alumno con el id indicado.
     *
     * @param alumnoId id del alumno
     * @return lista de matriculas del alumno
     */
    public List<AlumnoAsignatura> buscarPorAlumno(Long alumnoId) {
        List<AlumnoAsignatura> lista = alumnoAsignaturaRepository.findByAlumnoId(alumnoId);
        for (AlumnoAsignatura matricula : lista) {
            rellenarTutorAula(matricula);
        }
        return lista;
    }

    /**
     * Devuelve las matriculas de la asignatura con el id indicado.
     *
     * @param asignaturaId id de la asignatura
     * @return lista de matriculas de la asignatura
     */
    public List<AlumnoAsignatura> buscarPorAsignatura(Long asignaturaId) {
        List<AlumnoAsignatura> lista = alumnoAsignaturaRepository.findByAsignaturaId(asignaturaId);
        for (AlumnoAsignatura matricula : lista) {
            rellenarTutorAula(matricula);
        }
        return lista;
    }

    /**
     * Guarda una nueva matricula verificando curso, duplicados y nota.
     *
     * <p>
     * Tras la primera persistencia asigna el codigo {@code MTR-<id>}.
     * </p>
     *
     * @param alumnoAsignatura datos de la matricula a guardar
     * @return matricula guardada con codigo asignado
     * @throws RuntimeException si el alumno o la asignatura no existen, si el
     * alumno ya esta matriculado, si el curso del alumno no coincide con el de
     * la asignatura, o si la nota no esta entre 0 y 10
     */
    public AlumnoAsignatura guardar(AlumnoAsignatura alumnoAsignatura) {
        Alumno alumno = alumnoRepository.findById(alumnoAsignatura.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(alumnoAsignatura.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        if (alumnoAsignaturaRepository.existsByAlumnoAndAsignatura(alumno, asignatura)) {
            throw new RuntimeException("El alumno " + alumno.getCodigo()
                    + " ya esta matriculado en la asignatura " + asignatura.getCodigo());
        }

        if (!alumno.getCurso().equals(asignatura.getCurso())) {
            throw new RuntimeException("El alumno es de " + alumno.getCurso()
                    + " y la asignatura es de " + asignatura.getCurso());
        }

        validarNota(alumnoAsignatura.getNota());

        alumnoAsignatura.setAlumno(alumno);
        alumnoAsignatura.setAsignatura(asignatura);
        AlumnoAsignatura guardado = alumnoAsignaturaRepository.save(alumnoAsignatura);
        guardado.setCodigo(Constantes.PREFIJO_MATR + guardado.getId());
        return alumnoAsignaturaRepository.save(guardado);
    }

    /**
     * Actualiza la nota de una matricula existente.
     *
     * @param id id de la matricula a actualizar
     * @param alumnoAsignatura nuevos datos de la matricula
     * @return matricula con la nota actualizada
     * @throws RuntimeException si la matricula no existe o la nota no es valida
     */
    public AlumnoAsignatura actualizar(Long id, AlumnoAsignatura alumnoAsignatura) {
        AlumnoAsignatura existente = buscarPorId(id);
        validarNota(alumnoAsignatura.getNota());
        existente.setNota(alumnoAsignatura.getNota());
        return alumnoAsignaturaRepository.save(existente);
    }

    /**
     * Borra la matricula con el id indicado.
     *
     * @param id id de la matricula a borrar
     * @throws RuntimeException si la matricula no existe
     */
    public void borrar(Long id) {
        buscarPorId(id);
        alumnoAsignaturaRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Valida que la nota este en el rango permitido.
     *
     * @param nota nota a validar
     * @throws RuntimeException si la nota no esta entre 0 y 10
     */
    private void validarNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new RuntimeException("La nota debe estar entre 0 y 10");
        }
    }

    private void rellenarTutorAula(AlumnoAsignatura matricula) {
        if (matricula.getAlumno() != null
                && matricula.getAlumno().getAula() != null
                && matricula.getAlumno().getAula().getTutor() != null) {
            matricula.getAlumno().getAula().getTutor()
                    .setCodigoAula(matricula.getAlumno().getAula().getCodigo());
        }
    }
}

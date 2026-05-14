package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Alumno;
import com.colegio.model.AlumnoAsignatura;
import com.colegio.model.Asignatura;
import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.repository.AlumnoAsignaturaRepository;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.AulaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los alumnos.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Service
public class AlumnoService {

    @Autowired
    private AlumnoAsignaturaRepository alumnoAsignaturaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    AlumnoService(AlumnoAsignaturaRepository alumnoAsignaturaRepository) {
        this.alumnoAsignaturaRepository = alumnoAsignaturaRepository;
    }

    // ============================================================
    // METODOS CRUD
    // ============================================================
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno buscarPorId(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno con id " + id + " no encontrado"));
    }

    public Alumno buscarPorEmail(String email) {
        return alumnoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Alumno con email " + email + " no encontrado"));
    }

    public List<Alumno> buscarPorAula(Long aulaId) {
        return alumnoRepository.findByAulaId(aulaId);
    }

    public List<Alumno> buscarPorCurso(Curso curso) {
        return alumnoRepository.findByAulaCurso(curso);
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        return alumnoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Alumno> buscarPorNombreYApellido(String nombre, String apellido) {
        return alumnoRepository.findByNombreAndApellido(nombre, apellido);
    }

    /**
     * Guarda un nuevo alumno verificando que el aula exista y tenga plazas
     * libres.
     */
    public Alumno guardarAlumno(Alumno alumno) {
        if (alumnoRepository.existsByEmail(alumno.getEmail())) {
            throw new RuntimeException("Ya existe un alumno con el email: " + alumno.getEmail());
        }

        Aula aula = aulaRepository.findById(alumno.getAula().getId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        int ocupacion = aulaRepository.countAlumnosByAulaId(aula.getId());
        if (ocupacion >= aula.getCapacidad()) {
            throw new RuntimeException("El aula " + aula.getCodigo() + " está llena");
        }

        alumno.setAula(aula);
        Alumno guardado = alumnoRepository.save(alumno);
        guardado.setCodigo(Constantes.PREFIJO_ALUMNO + guardado.getId());
        guardado = alumnoRepository.save(guardado);
        matricularEnAsignaturas(guardado);
        return guardado;

    }

    public Alumno actualizarAlumno(Long id, Alumno alumno) {
        Alumno existente = buscarPorId(id);

        if (!existente.getEmail().equals(alumno.getEmail())
                && alumnoRepository.existsByEmail(alumno.getEmail())) {
            throw new RuntimeException("Ya existe un alumno con el email: " + alumno.getEmail());
        }

        existente.setNombre(alumno.getNombre());
        existente.setApellido(alumno.getApellido());
        existente.setEmail(alumno.getEmail());
        if (alumno.getAula() != null) {
            Aula aula = aulaRepository.findById(alumno.getAula().getId())
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
            existente.setAula(aula);
        }
        return alumnoRepository.save(existente);
    }

    public void borrarAlumno(Long id) {
        buscarPorId(id);
        alumnoAsignaturaRepository.deleteAll(alumnoAsignaturaRepository.findByAlumnoId(id));
        alumnoRepository.deleteById(id);
    }

    public long count() {
        return alumnoRepository.count();
    }

    public List<Alumno> buscarPorCodigoAula(String codigo) {
        return alumnoRepository.findByAulaCodigo(codigo);
    }

    private void matricularEnAsignaturas(Alumno alumno) {
        List<Asignatura> asignaturas = asignaturaRepository.findByCurso(alumno.getCurso());

        for (Asignatura asignatura : asignaturas) {
            if (!alumnoAsignaturaRepository.existsByAlumnoAndAsignatura(alumno, asignatura)) {
                AlumnoAsignatura matricula = new AlumnoAsignatura(0.0, alumno, asignatura);
                AlumnoAsignatura guardada = alumnoAsignaturaRepository.save(matricula);
                guardada.setCodigo(Constantes.PREFIJO_MATR + guardada.getId());
                alumnoAsignaturaRepository.save(guardada);
            }
        }
    }
}

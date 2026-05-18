package com.colegio.service;

import java.time.LocalDate;
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
 * <p>
 * Creacion, consulta, actualizacion y borrado de {@link Alumno}. Al guardar un
 * alumno verifica la capacidad del aula y lo matricula en las asignaturas de su
 * curso.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 4.0
 * @see Alumno
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

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve todos los alumnos registrados.
     *
     * @return lista de alumnos
     */
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    /**
     * Devuelve el alumno con el id indicado.
     *
     * @param id id del alumno
     * @return alumno encontrado
     * @throws RuntimeException si no existe un alumno con ese id
     */
    public Alumno buscarPorId(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno con id " + id + " no encontrado"));
    }

    /**
     * Devuelve los alumnos asignados al aula con el id indicado.
     *
     * @param aulaId id del aula
     * @return lista de alumnos del aula
     */
    public List<Alumno> buscarPorAula(Long aulaId) {
        return alumnoRepository.findByAulaId(aulaId);
    }

    /**
     * Devuelve los alumnos cuya aula pertenece al curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de alumnos del curso
     */
    public List<Alumno> buscarPorCurso(Curso curso) {
        return alumnoRepository.findByAulaCurso(curso);
    }

    /**
     * Devuelve los alumnos cuyo nombre contiene la cadena d etexto indicada.
     *
     * @param nombre fragmento del nombre a buscar
     * @return lista de alumnos que coinciden
     */
    public List<Alumno> buscarPorNombre(String nombre) {
        return alumnoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Devuelve los alumnos que coinciden exactamente con el nombre y apellido.
     *
     * @param nombre nombre del alumno
     * @param apellido apellido del alumno
     * @return lista de alumnos que coinciden
     */
    public List<Alumno> buscarPorNombreYApellido(String nombre, String apellido) {
        return alumnoRepository.findByNombreAndApellido(nombre, apellido);
    }

    /**
     * Devuelve los alumnos nacidos en la fecha indicada.
     *
     * @param fechaNacimiento fecha de nacimiento a buscar
     * @return lista de alumnos con esa fecha de nacimiento
     */
    public List<Alumno> buscarPorFechaNacimiento(LocalDate fechaNacimiento) {
        return alumnoRepository.findByFechaNacimiento(fechaNacimiento);
    }

    /**
     * Guarda un nuevo alumno verificando que el aula exista y tenga plazas
     * libres.
     *
     * <p>
     * Asigna el codigo {@code ALUM-<id>} y matricula al alumno en todas las
     * asignaturas de su curso.</p>
     *
     * @param alumno datos del alumno a guardar
     * @return alumno guardado con codigo y matriculas asignadas
     * @throws RuntimeException si el aula no existe o esta llena
     */
    public Alumno guardarAlumno(Alumno alumno) {
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

    /**
     * Actualiza los datos de un alumno existente.
     *
     * @param id id del alumno a actualizar
     * @param alumno nuevos datos del alumno
     * @return alumno actualizado
     * @throws RuntimeException si el alumno o el aula no existen
     */
    public Alumno actualizarAlumno(Long id, Alumno alumno) {
        Alumno existente = buscarPorId(id);

        existente.setNombre(alumno.getNombre());
        existente.setApellido(alumno.getApellido());
        existente.setFechaNacimiento(alumno.getFechaNacimiento());
        if (alumno.getAula() != null) {
            Aula aula = aulaRepository.findById(alumno.getAula().getId())
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
            existente.setAula(aula);
        }
        return alumnoRepository.save(existente);
    }

    /**
     * Borra un alumno y todas sus matriculas asociadas.
     *
     * @param id id del alumno a borrar
     * @throws RuntimeException si el alumno no existe
     */
    public void borrarAlumno(Long id) {
        buscarPorId(id);
        alumnoAsignaturaRepository.deleteAll(alumnoAsignaturaRepository.findByAlumnoId(id));
        alumnoRepository.deleteById(id);
    }

    /**
     * Devuelve el numero total de alumnos registrados.
     *
     * @return total de alumnos
     */
    public long count() {
        return alumnoRepository.count();
    }

    /**
     * Devuelve los alumnos cuya aula tiene el codigo indicado.
     *
     * @param codigo codigo del aula
     * @return lista de alumnos del aula
     */
    public List<Alumno> buscarPorCodigoAula(String codigo) {
        return alumnoRepository.findByAulaCodigo(codigo);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Matricula al alumno en todas las asignaturas de su curso.
     *
     * <p>
     * Solo crea la matricula si no existe ya. Asigna el codigo {@code MTR-<id>}
     * tras cada persistencia.</p>
     *
     * @param alumno alumno a matricular
     */
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

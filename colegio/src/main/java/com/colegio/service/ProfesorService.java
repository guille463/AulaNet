package com.colegio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.model.Aula;
import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de los profesores.
 *
 * <p>
 * Coordina la creacion, consulta, actualizacion y borrado de {@link Profesor}.
 * Al guardar asigna automaticamente las asignaturas segun la especialidad y, si
 * es {@link Especialidad#GENERAL}, lo asigna como tutor del aula indicada.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 4.0
 * @see Profesor
 * @see ProfesorAsignatura
 */
@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve todos los profesores registrados.
     *
     * @return lista de profesores
     */
    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    /**
     * Devuelve el profesor con el id indicado.
     *
     * @param id id del profesor
     * @return profesor encontrado
     * @throws RuntimeException si no existe un profesor con ese id
     */
    public Profesor buscarProfesorPorId(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor con id " + id + " no encontrado"));
    }

    /**
     * Devuelve el profesor con el email indicado.
     *
     * @param email email del profesor
     * @return profesor encontrado
     * @throws RuntimeException si no existe un profesor con ese email
     */
    public Profesor buscarProfesorPorEmail(String email) {
        return profesorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor con email " + email + " no encontrado"));
    }

    /**
     * Devuelve los profesores cuyo nombre contiene la cadena indicada, sin
     * distinguir mayusculas.
     *
     * @param nombre fragmento del nombre a buscar
     * @return lista de profesores que coinciden
     */
    public List<Profesor> buscarPorNombre(String nombre) {
        return profesorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Devuelve los profesores con la especialidad indicada.
     *
     * @param especialidad especialidad por la que filtrar
     * @return lista de profesores con esa especialidad
     */
    public List<Profesor> buscarPorEspecialidad(Especialidad especialidad) {
        return profesorRepository.findByEspecialidad(especialidad);
    }

    /**
     * Guarda un nuevo profesor asignandole asignaturas segun su especialidad.
     *
     * <p>
     * {@code PROF-<id>}. Si la especialidad es {@link Especialidad#GENERAL} y
     * se indica un codigo de aula, el profesor se asigna como tutor de esa
     * aula.</p>
     *
     * @param profesor datos del profesor a guardar
     * @return profesor guardado con codigo y asignaturas asignadas
     * @throws RuntimeException si ya existe un profesor con ese email o si el
     * aula indicada no existe
     */
    public Profesor guardarProfesor(Profesor profesor) {
        if (profesorRepository.existsByEmail(profesor.getEmail())) {
            throw new RuntimeException("Ya existe un profesor con el email: " + profesor.getEmail());
        }

        String codigoAula = profesor.getCodigoAula();

        Profesor guardado = profesorRepository.save(profesor);
        guardado.setCodigo(Constantes.PREFIJO_PROFESOR + guardado.getId());
        guardado = profesorRepository.save(guardado);

        asignarAsignaturas(guardado);

        if (guardado.getEspecialidad().equals(Especialidad.GENERAL)
                && codigoAula != null
                && !codigoAula.isEmpty()) {
            Aula aula = aulaRepository.findByCodigo(codigoAula)
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada: " + codigoAula));
            aula.setTutor(guardado);
            aulaRepository.save(aula);
        }

        return guardado;
    }

    /**
     * Actualiza los datos de un profesor existente.
     *
     * <p>
     * Si cambia la especialidad o el aula, se eliminan las asignaturas
     * anteriores y se reasignan segun los nuevos datos.</p>
     *
     * @param id id del profesor a actualizar
     * @param profesor nuevos datos del profesor
     * @return profesor actualizado
     * @throws RuntimeException si el profesor no existe o si el nuevo email ya
     * esta en uso
     */
    public Profesor actualizarProfesor(Long id, Profesor profesor) {
        Profesor existente = buscarProfesorPorId(id);

        if (!existente.getEmail().equals(profesor.getEmail())
                && profesorRepository.existsByEmail(profesor.getEmail())) {
            throw new RuntimeException("Ya existe un profesor con el email: " + profesor.getEmail());
        }

        existente.setNombre(profesor.getNombre());
        existente.setApellido(profesor.getApellido());
        existente.setEmail(profesor.getEmail());

        boolean especialidadCambia = false;
        boolean aulaCambia = false;

        if (!existente.getEspecialidad().equals(profesor.getEspecialidad())) {
            especialidadCambia = true;
        }

        if (profesor.getCodigoAula() == null) {
            aulaCambia = false;
        } else if (!profesor.getCodigoAula().equals(existente.getCodigoAula())) {
            aulaCambia = true;
        }

        if (especialidadCambia || aulaCambia) {
            profesorAsignaturaRepository.deleteAll(profesorAsignaturaRepository.findByProfesorId(id));
            existente.setEspecialidad(profesor.getEspecialidad());
            existente.setCodigoAula(profesor.getCodigoAula());
            asignarAsignaturas(existente);
        }

        return profesorRepository.save(existente);
    }

    /**
     * Borra el profesor y todas sus relaciones con asignaturas.
     *
     * @param id id del profesor a borrar
     * @throws RuntimeException si el profesor no existe
     */
    public void borrarProfesor(Long id) {
        buscarProfesorPorId(id);
        profesorAsignaturaRepository.deleteAll(profesorAsignaturaRepository.findByProfesorId(id));
        profesorRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Asigna al profesor las asignaturas correspondientes a su especialidad.
     *
     * <p>
     * Para {@link Especialidad#GENERAL} filtra ademas por el curso del aula
     * indicada. Solo crea la relacion si no existe ya.</p>
     *
     * @param profesor profesor al que asignar las asignaturas
     */
    private void asignarAsignaturas(Profesor profesor) {
        List<String> nombresAsignaturas = new ArrayList<>();

        if (profesor.getEspecialidad().equals(Especialidad.GENERAL)) {
            nombresAsignaturas.add("Matematicas");
            nombresAsignaturas.add("Lengua Castellana y Literatura");
            nombresAsignaturas.add("Ciencias de la Naturaleza");
            nombresAsignaturas.add("Ciencias Sociales");
            nombresAsignaturas.add("Plastica");
        } else if (profesor.getEspecialidad().equals(Especialidad.EDUCACION_FISICA)) {
            nombresAsignaturas.add("Educacion Fisica");
        } else if (profesor.getEspecialidad().equals(Especialidad.INGLES)) {
            nombresAsignaturas.add("Ingles");
        } else if (profesor.getEspecialidad().equals(Especialidad.MUSICA)) {
            nombresAsignaturas.add("Musica");
        } else if (profesor.getEspecialidad().equals(Especialidad.RELIGION)) {
            nombresAsignaturas.add("Religion");
        }

        List<Asignatura> todasAsignaturas = asignaturaRepository.findAll();

        for (Asignatura asignatura : todasAsignaturas) {
            if (nombresAsignaturas.contains(asignatura.getNombre())) {
                boolean esCursoDelAula = true;

                if (profesor.getEspecialidad().equals(Especialidad.GENERAL)
                        && profesor.getCodigoAula() != null
                        && !profesor.getCodigoAula().isEmpty()) {
                    Aula aulaEncontrada = aulaRepository.findByCodigo(profesor.getCodigoAula()).orElse(null);
                    if (aulaEncontrada != null) {
                        esCursoDelAula = asignatura.getCurso().equals(aulaEncontrada.getCurso());
                    } else {
                        esCursoDelAula = false;
                    }
                }

                if (esCursoDelAula) {
                    if (!profesorAsignaturaRepository.existsByProfesorAndAsignatura(profesor, asignatura)) {
                        ProfesorAsignatura pa = new ProfesorAsignatura(asignatura.getHorasSemana(), profesor, asignatura);
                        profesorAsignaturaRepository.save(pa);
                    }
                }
            }
        }
    }
}

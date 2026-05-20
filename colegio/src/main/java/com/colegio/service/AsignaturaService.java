package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las asignaturas.
 *
 * <p>
 * Coordina la creacion, consulta, actualizacion y borrado de
 * {@link Asignatura}. Al guardar valida que no exista duplicado en el mismo
 * curso y que las horas semanales esten en el rango permitido.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see Asignatura
 */
@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve todas las asignaturas registradas.
     *
     * @return lista de asignaturas
     */
    public List<Asignatura> listarAsignaturas() {
        return asignaturaRepository.findAll();
    }

    /**
     * Devuelve la asignatura con el id indicado.
     *
     * @param id id de la asignatura
     * @return asignatura encontrada
     * @throws RuntimeException si no existe una asignatura con ese id
     */
    public Asignatura buscarAsignaturaPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura con id " + id + " no encontrada"));
    }

    /**
     * Devuelve las asignaturas del curso indicado.
     *
     * @param curso curso por el que filtrar
     * @return lista de asignaturas del curso
     */
    public List<Asignatura> buscarAsignaturasPorCurso(Curso curso) {
        return asignaturaRepository.findByCurso(curso);
    }

    /**
     * Guarda una nueva asignatura verificando que no exista duplicado en el
     * mismo curso.
     *
     * <p>
     * Tras la primera persistencia asigna el codigo {@code ASG-<id>}.
     * </p>
     *
     * @param asignatura datos de la asignatura a guardar
     * @return asignatura guardada con codigo asignado
     * @throws RuntimeException si ya existe la asignatura en ese curso o si las
     *                          horas semanales no estan entre 1 y 6
     */
    public Asignatura guardarAsignatura(Asignatura asignatura) {
        if (asignaturaRepository.existsByNombreAndCurso(
                asignatura.getNombre(), asignatura.getCurso())) {
            throw new RuntimeException("Ya existe la asignatura '"
                    + asignatura.getNombre() + "' en el curso " + asignatura.getCurso());
        }
        validarHorasSemana(asignatura.getHorasSemana());
        Asignatura guardada = asignaturaRepository.save(asignatura);
        guardada.setCodigo(Constantes.PREFIJO_ASIG + guardada.getId());
        return asignaturaRepository.save(guardada);
    }

    /**
     * Actualiza los datos de una asignatura existente.
     *
     * @param id         id de la asignatura a actualizar
     * @param asignatura nuevos datos de la asignatura
     * @return asignatura actualizada
     * @throws RuntimeException si la asignatura no existe o si las horas
     *                          semanales no estan entre 1 y 6
     */
    public Asignatura actualizarAsignatura(Long id, Asignatura asignatura) {
        Asignatura existente = buscarAsignaturaPorId(id);
        validarHorasSemana(asignatura.getHorasSemana());
        existente.setNombre(asignatura.getNombre());
        existente.setDescripcion(asignatura.getDescripcion());
        existente.setCurso(asignatura.getCurso());
        existente.setHorasSemana(asignatura.getHorasSemana());
        return asignaturaRepository.save(existente);
    }

    /**
     * Borra la asignatura con el id indicado.
     *
     * @param id id de la asignatura a borrar
     * @throws RuntimeException si la asignatura no existe
     */
    public void borrarAsignatura(Long id) {
        buscarAsignaturaPorId(id);
        asignaturaRepository.deleteById(id);
    }

    // ============================================================
    // METODOS PRIVADOS
    // ============================================================
    /**
     * Valida que las horas semanales esten en el rango permitido.
     *
     * @param horas horas semanales a validar
     * @throws RuntimeException si las horas no estan entre 1 y 6
     */
    private void validarHorasSemana(int horas) {
        if (horas < 1 || horas > 6) {
            throw new RuntimeException("Las horas semanales deben estar entre 1 y 6");
        }
    }
}

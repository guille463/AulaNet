package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Asignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;

/**
 * Servicio que gestiona la logica de negocio de las asignaturas.
 *
 * <p>
 * Actua como intermediario entre {@link AsignaturaController} y
 * {@link AsignaturaRepository}. Contiene las reglas de negocio y operaciones
 * CRUD sobre {@link Asignatura}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Service
public class AsignaturaService {

    /**
     * Repositorio para acceder a los datos de {@link Asignatura}
     */
    @Autowired
    AsignaturaRepository asignaturaRepository;

    // ============================================================
    // METODOS CRUD
    // ============================================================
    /**
     * Devuelve la lista completa de asignaturas.
     *
     * @return lista de {@link Asignatura}
     */
    public List<Asignatura> listarAsignaturas() {
        return asignaturaRepository.findAll();
    }

    /**
     * Busca una asignatura por su identificador.
     *
     * @param id identificador de la asignatura
     * @return {@link Asignatura} encontrada
     * @throws RuntimeException si la asignatura no existe
     */
    public Asignatura buscarAsignaturaPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Asignatura con id: " + id + " no existe"));
    }

    /**
     * Guarda una nueva asignatura y le asigna su codigo identificativo.
     *
     * <p>
     * El codigo se genera automaticamente con el prefijo {@code ASG-} seguido
     * del ID generado por la base de datos.
     * </p>
     *
     * @param asignatura datos de la {@link Asignatura} a guardar
     * @return asignatura guardada con codigo asignado
     */
    public Asignatura guardarAsignatura(Asignatura asignatura) {
        Asignatura guardado = asignaturaRepository.save(asignatura);
        guardado.setCodigo(Constantes.PREFIJO_ASIG + guardado.getId());
        return asignaturaRepository.save(guardado);
    }

    /**
     * Actualiza los datos de una asignatura existente.
     *
     * @param id identificador de la asignatura a actualizar
     * @param asignatura datos nuevos de la {@link Asignatura}
     * @return asignatura actualizada
     * @throws RuntimeException si la asignatura no existe
     */
    public Asignatura actualizarAsignatura(Long id, Asignatura asignatura) {
        Asignatura existente = buscarAsignaturaPorId(id);
        existente.setNombre(asignatura.getNombre());
        existente.setDescripcion(asignatura.getDescripcion());
        existente.setCurso(asignatura.getCurso());
        existente.setCreditos(asignatura.getCreditos());
        existente.setHorasSemana(asignatura.getHorasSemana());
        return asignaturaRepository.save(existente);
    }

    /**
     * Elimina una asignatura por su identificador.
     *
     * @param id identificador de la asignatura a eliminar
     */
    public void borrarAsignatura(Long id) {
        asignaturaRepository.deleteById(id);
    }
}

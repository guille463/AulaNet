package com.colegio.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorService}.
 *
 * <p>
 * Cubre los casos principales de listado, busqueda, guardado, actualizacion y
 * borrado.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see ProfesorService
 */
@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    /**
     * Profesor de prueba con especialidad {@code GENERAL}.
     */
    private Profesor profesor;

    /**
     * Profesor de prueba con especialidad {@code EDUCACION_FISICA}.
     */
    private Profesor profesor2;

    // ============================================================
    // CONFIGURACION
    // ============================================================
    /**
     * Inicializa los objetos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", Especialidad.GENERAL);
        profesor.setCodigo("PROF-1");
        profesor2 = new Profesor("Pepe", "Garcia", "pepe@colegio.com", Especialidad.EDUCACION_FISICA);
        profesor2.setCodigo("PROF-2");
    }

    // ============================================================
    // TESTS
    // ============================================================
    /**
     * Verifica que {@code listarProfesores} devuelve la lista completa.
     */
    @Test
    @DisplayName("listarProfesores devuelve lista correcta")
    void listarProfesores_devuelveListaCorrecta() {
        when(profesorRepository.findAll()).thenReturn(Arrays.asList(profesor, profesor2));

        List<Profesor> resultado = profesorService.listarProfesores();

        assertEquals(2, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

    /**
     * Verifica que {@code buscarProfesorPorId} devuelve el profesor correcto
     * cuando existe.
     */
    @Test
    @DisplayName("buscarProfesorPorId devuelve el profesor correcto")
    void buscarProfesorPorId_devuelveProfesorCorrecto() {
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        Profesor resultado = profesorService.buscarProfesorPorId(1L);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    /**
     * Verifica que {@code buscarProfesorPorId} lanza {@link RuntimeException}
     * cuando el profesor no existe.
     */
    @Test
    @DisplayName("buscarProfesorPorId lanza excepcion si no existe")
    void buscarProfesorPorId_lanzaExcepcionSiNoExiste() {
        when(profesorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            profesorService.buscarProfesorPorId(99L);
        });
    }

    /**
     * Verifica que {@code buscarProfesorPorEmail} devuelve el profesor correcto
     * cuando existe.
     */
    @Test
    @DisplayName("buscarProfesorPorEmail devuelve el profesor correcto")
    void buscarProfesorPorEmail_devuelveProfesorCorrecto() {
        when(profesorRepository.findByEmail("carlos@colegio.com")).thenReturn(Optional.of(profesor));

        Profesor resultado = profesorService.buscarProfesorPorEmail("carlos@colegio.com");

        assertNotNull(resultado);
        assertEquals("carlos@colegio.com", resultado.getEmail());
    }

    /**
     * Verifica que {@code buscarProfesorPorEmail} lanza
     * {@link RuntimeException} cuando el profesor no existe.
     */
    @Test
    @DisplayName("buscarProfesorPorEmail lanza excepcion si no existe")
    void buscarProfesorPorEmail_lanzaExcepcionSiNoExiste() {
        when(profesorRepository.findByEmail("noexiste@colegio.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            profesorService.buscarProfesorPorEmail("noexiste@colegio.com");
        });
    }

    /**
     * Verifica que {@code buscarPorEspecialidad} devuelve los profesores de la
     * especialidad indicada.
     */
    @Test
    @DisplayName("buscarPorEspecialidad devuelve lista correcta")
    void buscarPorEspecialidad_devuelveListaCorrecta() {
        when(profesorRepository.findByEspecialidad(Especialidad.GENERAL))
                .thenReturn(Arrays.asList(profesor));

        List<Profesor> resultado = profesorService.buscarPorEspecialidad(Especialidad.GENERAL);

        assertEquals(1, resultado.size());
        assertEquals(Especialidad.GENERAL, resultado.get(0).getEspecialidad());
    }

    /**
     * Verifica que {@code guardarProfesor} lanza {@link RuntimeException}
     * cuando ya existe un profesor con el mismo email.
     */
    @Test
    @DisplayName("guardarProfesor lanza excepcion si email duplicado")
    void guardarProfesor_lanzaExcepcionSiEmailDuplicado() {
        when(profesorRepository.existsByEmail("carlos@colegio.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            profesorService.guardarProfesor(profesor);
        });
    }

    /**
     * Verifica que {@code guardarProfesor} invoca {@code save} dos veces para
     * asignar el codigo.
     */
    @Test
    @DisplayName("guardarProfesor llama a save dos veces para asignar codigo")
    void guardarProfesor_llamaSaveDosVeces() {
        when(profesorRepository.existsByEmail("carlos@colegio.com")).thenReturn(false);
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        profesorService.guardarProfesor(profesor);

        verify(profesorRepository, times(2)).save(any(Profesor.class));
    }

    /**
     * Verifica que {@code actualizarProfesor} lanza {@link RuntimeException}
     * cuando el nuevo email ya pertenece a otro profesor.
     */
    @Test
    @DisplayName("actualizarProfesor lanza excepcion si email ya pertenece a otro profesor")
    void actualizarProfesor_lanzaExcepcionSiEmailDuplicado() {
        Profesor datos = new Profesor("Carlos", "Martinez", "otro@colegio.com", Especialidad.GENERAL);
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(profesorRepository.existsByEmail("otro@colegio.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            profesorService.actualizarProfesor(1L, datos);
        });
    }

    /**
     * Verifica que {@code borrarProfesor} invoca {@code deleteById} exactamente
     * una vez.
     */
    @Test
    @DisplayName("borrarProfesor llama a deleteById una vez")
    void borrarProfesor_llamaDeleteById() {
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        profesorService.borrarProfesor(1L);

        verify(profesorRepository, times(1)).deleteById(1L);
    }
}

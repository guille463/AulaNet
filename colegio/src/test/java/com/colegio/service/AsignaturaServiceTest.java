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

import com.colegio.model.Asignatura;
import com.colegio.model.Curso;
import com.colegio.repository.AsignaturaRepository;

/**
 * Pruebas unitarias del servicio {@link AsignaturaService}.
 *
 * <p>
 * Usa Mockito para aislar el servicio de sus dependencias. Cubre los casos
 * principales de listado, busqueda, guardado, actualizacion y borrado.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see AsignaturaService
 */
@ExtendWith(MockitoExtension.class)
class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    /**
     * Asignatura de prueba reutilizada en todos los tests.
     */
    private Asignatura asignatura;

    // ============================================================
    // CONFIGURACION
    // ============================================================
    /**
     * Inicializa los objetos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        asignatura = new Asignatura("Matematicas", Curso.PRIMERO, 4, "Aritmetica basica");
        asignatura.setCodigo("ASG-1");
    }

    // ============================================================
    // TESTS
    // ============================================================
    /**
     * Verifica que {@code listarAsignaturas} devuelve la lista completa.
     */
    @Test
    @DisplayName("listarAsignaturas devuelve lista correcta")
    void listarAsignaturas_devuelveListaCorrecta() {
        when(asignaturaRepository.findAll()).thenReturn(Arrays.asList(asignatura));

        List<Asignatura> resultado = asignaturaService.listarAsignaturas();

        assertEquals(1, resultado.size());
        assertEquals("Matematicas", resultado.get(0).getNombre());
    }

    /**
     * Verifica que {@code buscarAsignaturaPorId} devuelve la asignatura
     * correcta cuando existe.
     */
    @Test
    @DisplayName("buscarAsignaturaPorId devuelve la asignatura correcta")
    void buscarAsignaturaPorId_devuelveAsignaturaCorrecta() {
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        Asignatura resultado = asignaturaService.buscarAsignaturaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Matematicas", resultado.getNombre());
    }

    /**
     * Verifica que {@code buscarAsignaturaPorId} lanza {@link RuntimeException}
     * cuando la asignatura no existe.
     */
    @Test
    @DisplayName("buscarAsignaturaPorId lanza excepcion si no existe")
    void buscarAsignaturaPorId_lanzaExcepcionSiNoExiste() {
        when(asignaturaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.buscarAsignaturaPorId(99L);
        });
    }

    /**
     * Verifica que {@code buscarAsignaturasPorCurso} devuelve las asignaturas
     * del curso indicado.
     */
    @Test
    @DisplayName("buscarAsignaturasPorCurso devuelve lista correcta")
    void buscarAsignaturasPorCurso_devuelveListaCorrecta() {
        when(asignaturaRepository.findByCurso(Curso.PRIMERO)).thenReturn(Arrays.asList(asignatura));

        List<Asignatura> resultado = asignaturaService.buscarAsignaturasPorCurso(Curso.PRIMERO);

        assertEquals(1, resultado.size());
        assertEquals(Curso.PRIMERO, resultado.get(0).getCurso());
    }

    /**
     * Verifica que {@code guardarAsignatura} lanza {@link RuntimeException}
     * cuando ya existe una asignatura con el mismo nombre y curso.
     */
    @Test
    @DisplayName("guardarAsignatura lanza excepcion si nombre y curso duplicados")
    void guardarAsignatura_lanzaExcepcionSiNombreYCursoDuplicados() {
        when(asignaturaRepository.existsByNombreAndCurso("Matematicas", Curso.PRIMERO))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.guardarAsignatura(asignatura);
        });
    }

    /**
     * Verifica que {@code guardarAsignatura} lanza {@link RuntimeException}
     * cuando las horas semanales estan fuera del rango permitido.
     */
    @Test
    @DisplayName("guardarAsignatura lanza excepcion si horas semanales invalidas")
    void guardarAsignatura_lanzaExcepcionSiHorasInvalidas() {
        Asignatura invalida = new Asignatura("Lengua", Curso.PRIMERO, 8, "desc");
        when(asignaturaRepository.existsByNombreAndCurso("Lengua", Curso.PRIMERO))
                .thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.guardarAsignatura(invalida);
        });
    }

    /**
     * Verifica que {@code guardarAsignatura} invoca {@code save} dos veces para
     * asignar el codigo.
     */
    @Test
    @DisplayName("guardarAsignatura llama a save dos veces para asignar codigo")
    void guardarAsignatura_llamaSaveDosVeces() {
        when(asignaturaRepository.existsByNombreAndCurso("Matematicas", Curso.PRIMERO))
                .thenReturn(false);
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);

        asignaturaService.guardarAsignatura(asignatura);

        verify(asignaturaRepository, times(2)).save(any(Asignatura.class));
    }

    /**
     * Verifica que {@code actualizarAsignatura} lanza {@link RuntimeException}
     * cuando las horas semanales estan fuera del rango permitido.
     */
    @Test
    @DisplayName("actualizarAsignatura lanza excepcion si horas semanales invalidas")
    void actualizarAsignatura_lanzaExcepcionSiHorasInvalidas() {
        Asignatura datos = new Asignatura("Matematicas", Curso.PRIMERO, 0, "desc");
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.actualizarAsignatura(1L, datos);
        });
    }

    /**
     * Verifica que {@code borrarAsignatura} invoca {@code deleteById}
     * exactamente una vez.
     */
    @Test
    @DisplayName("borrarAsignatura llama a deleteById una vez")
    void borrarAsignatura_llamaDeleteById() {
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        asignaturaService.borrarAsignatura(1L);

        verify(asignaturaRepository, times(1)).deleteById(1L);
    }
}

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
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    private Asignatura asignatura;

    @BeforeEach
    void setUp() {
        asignatura = new Asignatura("Matematicas", Curso.PRIMERO, 4, "Aritmetica basica");
        asignatura.setCodigo("ASG-1");
    }

    @Test
    @DisplayName("listarAsignaturas devuelve lista correcta")
    void listarAsignaturas_devuelveListaCorrecta() {
        when(asignaturaRepository.findAll()).thenReturn(Arrays.asList(asignatura));

        List<Asignatura> resultado = asignaturaService.listarAsignaturas();

        assertEquals(1, resultado.size());
        assertEquals("Matematicas", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscarAsignaturaPorId devuelve la asignatura correcta")
    void buscarAsignaturaPorId_devuelveAsignaturaCorrecta() {
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        Asignatura resultado = asignaturaService.buscarAsignaturaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Matematicas", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarAsignaturaPorId lanza excepcion si no existe")
    void buscarAsignaturaPorId_lanzaExcepcionSiNoExiste() {
        when(asignaturaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.buscarAsignaturaPorId(99L);
        });
    }

    @Test
    @DisplayName("buscarAsignaturasPorCurso devuelve lista correcta")
    void buscarAsignaturasPorCurso_devuelveListaCorrecta() {
        when(asignaturaRepository.findByCurso(Curso.PRIMERO)).thenReturn(Arrays.asList(asignatura));

        List<Asignatura> resultado = asignaturaService.buscarAsignaturasPorCurso(Curso.PRIMERO);

        assertEquals(1, resultado.size());
        assertEquals(Curso.PRIMERO, resultado.get(0).getCurso());
    }

    @Test
    @DisplayName("guardarAsignatura lanza excepcion si nombre y curso duplicados")
    void guardarAsignatura_lanzaExcepcionSiNombreYCursoDuplicados() {
        when(asignaturaRepository.existsByNombreAndCurso("Matematicas", Curso.PRIMERO))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.guardarAsignatura(asignatura);
        });
    }

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

    @Test
    @DisplayName("guardarAsignatura llama a save dos veces para asignar codigo")
    void guardarAsignatura_llamaSaveDosVeces() {
        when(asignaturaRepository.existsByNombreAndCurso("Matematicas", Curso.PRIMERO))
                .thenReturn(false);
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);

        asignaturaService.guardarAsignatura(asignatura);

        verify(asignaturaRepository, times(2)).save(any(Asignatura.class));
    }

    @Test
    @DisplayName("actualizarAsignatura lanza excepcion si horas semanales invalidas")
    void actualizarAsignatura_lanzaExcepcionSiHorasInvalidas() {
        Asignatura datos = new Asignatura("Matematicas", Curso.PRIMERO, 0, "desc");
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        assertThrows(RuntimeException.class, () -> {
            asignaturaService.actualizarAsignatura(1L, datos);
        });
    }

    @Test
    @DisplayName("borrarAsignatura llama a deleteById una vez")
    void borrarAsignatura_llamaDeleteById() {
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        asignaturaService.borrarAsignatura(1L);

        verify(asignaturaRepository, times(1)).deleteById(1L);
    }
}

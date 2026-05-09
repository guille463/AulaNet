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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.colegio.model.Asignatura;
import com.colegio.repository.AsignaturaRepository;

/**
 * Pruebas unitarias del servicio {@link AsignaturaService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
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
        asignatura = new Asignatura("Matematicas", "1º", 5, "Calculo basico");
    }

    @Test
    @DisplayName("listarAsignaturas devuelve la lista correcta")
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
    @DisplayName("borrarAsignatura llama a deleteById")
    void borrarAsignatura_llamaDeleteById() {
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        asignaturaService.borrarAsignatura(1L);

        verify(asignaturaRepository, times(1)).deleteById(1L);
    }
}

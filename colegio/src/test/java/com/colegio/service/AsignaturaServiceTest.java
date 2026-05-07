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

import com.colegio.entity.Asignatura;
import com.colegio.repository.AsignaturaRepository;

@ExtendWith(MockitoExtension.class)
class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    private Asignatura asignatura;

    @BeforeEach

    void setUp() {
        asignatura = new Asignatura("ASG-1", 6, "1ºA", 5, "Matematicas", "Calculo basico");
    }

    // ============================================================
    // TESTS
    // ============================================================
    @Test
    @DisplayName("ListarAsignaturas devuelve la lista correctad de asignaturas")
    void listarAsignaturas_devuelveListaCorrecta() {
        //Arrange
        when(asignaturaRepository.findAll()).thenReturn(Arrays.asList(asignatura));

        //Act
        List<Asignatura> resultado = asignaturaService.listarAsignaturas();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals("Matematicas", resultado.get(0).getNombre());

    }

    @Test
    @DisplayName("buscarAsignaturaPorid devuelve la asignatura correcta")

    void buscarAsignaturaPorid_devuelveAsignaturaCorrecta() {
        //Arrange
        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        //Act
        Asignatura asignatura = asignaturaService.buscarAsignaturaPorId(1L);

        //Assert
        assertNotNull(asignatura);
        assertEquals("Matematicas", asignatura.getNombre());

    }

    @Test
    @DisplayName("buscarAsignaturaPorId lanza excepcion si no existe")
    void buscarAsignaturaPorId_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(asignaturaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act y Assert
        assertThrows(RuntimeException.class, () -> {
            asignaturaService.buscarAsignaturaPorId(99L);
        });
    }

    @Test
    @DisplayName("borrarAsignatura llama a deleteById")
    void borrarAsignatura_llamaDeleteById() {
        // Act
        asignaturaService.borrarAsignatura(1L);

        // Assert
        verify(asignaturaRepository, times(1)).deleteById(1L);
    }

}

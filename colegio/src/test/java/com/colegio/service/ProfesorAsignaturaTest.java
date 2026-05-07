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
import com.colegio.entity.Profesor;
import com.colegio.entity.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorAsignaturaService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ProfesorAsignaturaServiceTest {

    @Mock
    private ProfesorAsignaturaRepository profesorAsignaturaRepository;

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private ProfesorAsignaturaService profesorAsignaturaService;

    private ProfesorAsignatura profesorAsignatura;

    private Profesor profesor;

    private Asignatura asignatura;

    @BeforeEach
    void setUp() {
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", null, "Matematicas", false, null);
        asignatura = new Asignatura("ASG-1", 6, "1ºA", 5, "Matematicas", "Calculo basico");
        profesorAsignatura = new ProfesorAsignatura("1ºA", 5, profesor, asignatura);
    }

    // ============================================================
    // TESTS
    // ============================================================
    @Test
    @DisplayName("listar devuelve lista correcta")
    void listar_devuelveListaCorrecta() {
        // Arrange
        when(profesorAsignaturaRepository.findAll()).thenReturn(Arrays.asList(profesorAsignatura));

        // Act
        List<ProfesorAsignatura> resultado = profesorAsignaturaService.listar();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("1ºA", resultado.get(0).getCurso());
    }

    @Test
    @DisplayName("buscarPorId devuelve la relacion correcta")
    void buscarPorId_devuelveRelacionCorrecta() {
        // Arrange
        when(profesorAsignaturaRepository.findById(1L)).thenReturn(Optional.of(profesorAsignatura));

        // Act
        ProfesorAsignatura resultado = profesorAsignaturaService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("1ºA", resultado.getCurso());
    }

    @Test
    @DisplayName("buscarPorId lanza excepcion si no existe")
    void buscarPorId_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(profesorAsignaturaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act y Assert
        assertThrows(RuntimeException.class, () -> {
            profesorAsignaturaService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("borrar llama a deleteById una vez")
    void borrar_llamaDeleteById() {
        // Act
        profesorAsignaturaService.borrar(1L);

        // Assert
        verify(profesorAsignaturaRepository, times(1)).deleteById(1L);
    }
}

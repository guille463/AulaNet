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

import com.colegio.model.Alumno;
import com.colegio.model.Asignatura;
import com.colegio.model.Matricula;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.MatriculaRepository;

/**
 * Pruebas unitarias del servicio {@link MatriculaService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    private Matricula matricula;

    private Alumno alumno;

    private Asignatura asignatura;

    @BeforeEach
    void setUp() {
        alumno = new Alumno("juan@colegio.com", "Juan", "Garcia", null, "1ºA");
        asignatura = new Asignatura("ASG-1", 6, "1ºA", 5, "Matematicas", "Calculo basico");
        matricula = new Matricula(alumno, asignatura, null, 8.5, "1ºA");
        matricula.setCodigo("MTR-1");
    }

    // ============================================================
    // TESTS
    // ============================================================
    @Test
    @DisplayName("listarMatriculas devuelve lista correcta")
    void listarMatriculas_devuelveListaCorrecta() {
        // Arrange
        when(matriculaRepository.findAll()).thenReturn(Arrays.asList(matricula));

        // Act
        List<Matricula> resultado = matriculaService.listarMatriculas();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(8.5, resultado.get(0).getNota());
    }

    @Test
    @DisplayName("buscarMatriculaPorId devuelve la matricula correcta")
    void buscarMatriculaPorId_devuelveMatriculaCorrecta() {
        // Arrange
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));

        // Act
        Matricula resultado = matriculaService.buscarMatriculaPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(8.5, resultado.getNota());
    }

    @Test
    @DisplayName("buscarMatriculaPorId lanza excepcion si no existe")
    void buscarMatriculaPorId_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(matriculaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act y Assert
        assertThrows(RuntimeException.class, () -> {
            matriculaService.buscarMatriculaPorId(99L);
        });
    }

    @Test
    @DisplayName("borrarmatricula llama a deleteById una vez")
    void borrarmatricula_llamaDeleteById() {
        // Act
        matriculaService.borrarmatricula(1L);

        // Assert
        verify(matriculaRepository, times(1)).deleteById(1L);
    }
}

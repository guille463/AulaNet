package com.colegio.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.colegio.entity.Alumno;
import com.colegio.repository.AlumnoRepository;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno;

    @BeforeEach
    void setUp() {
        alumno = new Alumno("juan@colegio.com", "Juan", "Garcia", null, "1ºA");
        alumno.setCodigo("ALUM-1");
    }

    // ============================================================
    // TESTS
    // ============================================================
    @Test
    void listarAlumnos_devuelveListaCorrecta() {
        // Arrange
        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno));

        // Act
        List<Alumno> resultado = alumnoService.ListarAlumnos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorid_devuelveAlumnoCorrecto() {
        // Arrange
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        // Act
        Alumno resultado = alumnoService.buscarPorid(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarPorid_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(alumnoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act Y Assert
        assertThrows(RuntimeException.class, () -> {
            alumnoService.buscarPorid(99L);
        });
    }

    @Test
    void borrarAlumno_llamaDeleteById() {
        // Act
        alumnoService.borrarAlumno(1L);

        // Assert
        verify(alumnoRepository, times(1)).deleteById(1L);
    }
}

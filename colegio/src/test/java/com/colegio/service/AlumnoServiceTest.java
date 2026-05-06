package com.colegio.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    //===================================
    // TESTS
//=====================================
    @Test
    void listarAlumnos_devuelveListaCorrecta() {
        //Arrange
        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno));

        //Act
        List<Alumno> resultadoBusqueda = alumnoService.ListarAlumnos();

        //Assert
        assertNotNull(resultadoBusqueda);
    }

    @Test
    void buscarPorid_devuelveAlumnoCorrecto() {
        // Arrange
        when(alumnoRepository.findById(1L));

        // Act
        Alumno resultado = alumnoService.buscarPorid(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    /**
     * Verifica que buscarPorid lanza excepcion si el alumno no existe.
     */
    @Test
    void buscarPorid_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(alumnoRepository.findById(99L));

        // Act y Assert
        assertThrows(RuntimeException.class, () -> {
            alumnoService.buscarPorid(99L);
        });
    }

    /**
     * Verifica que borrarAlumno llama al metodo deleteById del repositorio.
     */
    @Test
    void borrarAlumno_llamaDeleteById() {
        // Act
        alumnoService.borrarAlumno(1L);

    }
}

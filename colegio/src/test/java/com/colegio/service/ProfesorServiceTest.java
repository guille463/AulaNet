package com.colegio.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.colegio.entity.Profesor;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor profesor;
    private Profesor profesorTutor;

    @BeforeEach
    void setUp() {
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", null, "Matematicas", false, null);
        profesor.setCodigo("PROF-1");
        profesorTutor = new Profesor("Pepe", "Martinez", "carlos@colegio.com", null, "Matematicas", true, "1ºA");
        profesorTutor.setCodigo("PROF-2");

    }

    @Test
    @DisplayName("listarProfesores devuelve lista correcta")
    void listarProfesores_devuelveListaCorrecta() {
        // Arrange
        when(profesorRepository.findAll()).thenReturn(Arrays.asList(profesor, profesorTutor));

        // Act
        List<Profesor> resultado = profesorService.listarProfesores();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscarProfesorPorId devuelve el profesor correcto")
    void buscarProfesorPorId_devuelveProfesorCorrecto() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        // Act
        Profesor resultado = profesorService.buscarProfesorPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarProfesorPorId lanza excepcion si no existe")
    void buscarProfesorPorId_lanzaExcepcionSiNoExiste() {
        // Arrange
        when(profesorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act y Assert
        assertThrows(RuntimeException.class, () -> {
            profesorService.buscarProfesorPorId(99L);
        });
    }

    @Test
    @DisplayName("profesorTutor tiene esTutor true")
    void profesorTutor_tieneEsTutorTrue() {
        // Assert
        assertTrue(profesorTutor.isEsTutor());
        assertEquals("1ºA", profesorTutor.getTutorDeCurso());
    }

    @Test
    @DisplayName("borrarProfesor llama a deleteById una vez")
    void borrarProfesor_llamaDeleteById() {
        // Act
        profesorService.borrarProfesor(1L);

        // Assert
        verify(profesorRepository, times(1)).deleteById(1L);
    }
}

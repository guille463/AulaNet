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

import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor profesor;
    private Profesor profesor2;

    @BeforeEach
    void setUp() {
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", null, "Matematicas");
        profesor.setCodigo("PROF-1");
        profesor2 = new Profesor("Pepe", "Garcia", "pepe@colegio.com", null, "Lengua");
        profesor2.setCodigo("PROF-2");
    }

    @Test
    @DisplayName("listarProfesores devuelve lista correcta")
    void listarProfesores_devuelveListaCorrecta() {
        when(profesorRepository.findAll()).thenReturn(Arrays.asList(profesor, profesor2));

        List<Profesor> resultado = profesorService.listarProfesores();

        assertEquals(2, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscarProfesorPorId devuelve el profesor correcto")
    void buscarProfesorPorId_devuelveProfesorCorrecto() {
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        Profesor resultado = profesorService.buscarProfesorPorId(1L);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarProfesorPorId lanza excepcion si no existe")
    void buscarProfesorPorId_lanzaExcepcionSiNoExiste() {
        when(profesorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            profesorService.buscarProfesorPorId(99L);
        });
    }

    @Test
    @DisplayName("borrarProfesor llama a deleteById una vez")
    void borrarProfesor_llamaDeleteById() {
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        profesorService.borrarProfesor(1L);

        verify(profesorRepository, times(1)).deleteById(1L);
    }
}

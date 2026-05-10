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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.colegio.model.Especialidad;
import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
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
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", Especialidad.GENERAL);
        profesor.setCodigo("PROF-1");
        profesor2 = new Profesor("Pepe", "Garcia", "pepe@colegio.com", Especialidad.EDUCACION_FISICA);
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
    @DisplayName("buscarProfesorPorEmail devuelve el profesor correcto")
    void buscarProfesorPorEmail_devuelveProfesorCorrecto() {
        when(profesorRepository.findByEmail("carlos@colegio.com")).thenReturn(Optional.of(profesor));

        Profesor resultado = profesorService.buscarProfesorPorEmail("carlos@colegio.com");

        assertNotNull(resultado);
        assertEquals("carlos@colegio.com", resultado.getEmail());
    }

    @Test
    @DisplayName("buscarProfesorPorEmail lanza excepcion si no existe")
    void buscarProfesorPorEmail_lanzaExcepcionSiNoExiste() {
        when(profesorRepository.findByEmail("noexiste@colegio.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            profesorService.buscarProfesorPorEmail("noexiste@colegio.com");
        });
    }

    @Test
    @DisplayName("buscarPorEspecialidad devuelve lista correcta")
    void buscarPorEspecialidad_devuelveListaCorrecta() {
        when(profesorRepository.findByEspecialidad(Especialidad.GENERAL))
                .thenReturn(Arrays.asList(profesor));

        List<Profesor> resultado = profesorService.buscarPorEspecialidad(Especialidad.GENERAL);

        assertEquals(1, resultado.size());
        assertEquals(Especialidad.GENERAL, resultado.get(0).getEspecialidad());
    }

    @Test
    @DisplayName("guardarProfesor lanza excepcion si email duplicado")
    void guardarProfesor_lanzaExcepcionSiEmailDuplicado() {
        when(profesorRepository.existsByEmail("carlos@colegio.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            profesorService.guardarProfesor(profesor);
        });
    }

    @Test
    @DisplayName("guardarProfesor llama a save dos veces para asignar codigo")
    void guardarProfesor_llamaSaveDosVeces() {
        when(profesorRepository.existsByEmail("carlos@colegio.com")).thenReturn(false);
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        profesorService.guardarProfesor(profesor);

        verify(profesorRepository, times(2)).save(any(Profesor.class));
    }

    @Test
    @DisplayName("actualizarProfesor lanza excepcion si email ya pertenece a otro profesor")
    void actualizarProfesor_lanzaExcepcionSiEmailDuplicado() {
        Profesor datos = new Profesor("Carlos", "Martinez", "otro@colegio.com", Especialidad.GENERAL);
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(profesorRepository.existsByEmail("otro@colegio.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            profesorService.actualizarProfesor(1L, datos);
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

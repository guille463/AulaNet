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
import com.colegio.model.Aula;
import com.colegio.model.Profesor;
import com.colegio.model.ProfesorAsignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.ProfesorAsignaturaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link ProfesorAsignaturaService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
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
    private Aula aula;

    @BeforeEach
    void setUp() {
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", null, "Matematicas");
        asignatura = new Asignatura("Matematicas", "1º", 5, "Calculo basico");
        aula = new Aula("1º", "A", 30);
        profesorAsignatura = new ProfesorAsignatura("1º", 5, profesor, asignatura);
    }

    @Test
    @DisplayName("listar devuelve lista correcta")
    void listar_devuelveListaCorrecta() {
        when(profesorAsignaturaRepository.findAll()).thenReturn(Arrays.asList(profesorAsignatura));

        List<ProfesorAsignatura> resultado = profesorAsignaturaService.listar();

        assertEquals(1, resultado.size());
        assertEquals("1º", resultado.get(0).getCurso());
    }

    @Test
    @DisplayName("buscarPorId devuelve la relacion correcta")
    void buscarPorId_devuelveRelacionCorrecta() {
        when(profesorAsignaturaRepository.findById(1L)).thenReturn(Optional.of(profesorAsignatura));

        ProfesorAsignatura resultado = profesorAsignaturaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("1º", resultado.getCurso());
    }

    @Test
    @DisplayName("buscarPorId lanza excepcion si no existe")
    void buscarPorId_lanzaExcepcionSiNoExiste() {
        when(profesorAsignaturaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            profesorAsignaturaService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("borrar llama a deleteById una vez")
    void borrar_llamaDeleteById() {
        when(profesorAsignaturaRepository.findById(1L)).thenReturn(Optional.of(profesorAsignatura));

        profesorAsignaturaService.borrar(1L);

        verify(profesorAsignaturaRepository, times(1)).deleteById(1L);
    }
}

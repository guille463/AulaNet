package com.colegio.service;

import java.time.LocalDate;
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
import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.model.Grupo;
import com.colegio.repository.AlumnoAsignaturaRepository;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.AulaRepository;

/**
 * Pruebas unitarias del servicio {@link AlumnoService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private AlumnoAsignaturaRepository alumnoAsignaturaRepository;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno;
    private Aula aula;

    @BeforeEach
    void setUp() {
        aula = new Aula(Curso.PRIMERO, Grupo.A, 25);
        alumno = new Alumno("Juan", "Garcia", LocalDate.of(2015, 6, 15), aula);
        alumno.setCodigo("ALUM-1");
    }

    @Test
    @DisplayName("listarAlumnos devuelve lista correcta")
    void listarAlumnos_devuelveListaCorrecta() {
        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno));

        List<Alumno> resultado = alumnoService.listarAlumnos();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscarPorId devuelve el alumno correcto")
    void buscarPorId_devuelveAlumnoCorrecto() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        Alumno resultado = alumnoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId lanza excepcion si el alumno no existe")
    void buscarPorId_lanzaExcepcionSiNoExiste() {
        when(alumnoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            alumnoService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("borrarAlumno llama a deleteById una vez")
    void borrarAlumno_llamaDeleteById() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        alumnoService.borrarAlumno(1L);

        verify(alumnoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("buscarPorCurso devuelve lista correcta")
    void buscarPorCurso_devuelveListaCorrecta() {
        when(alumnoRepository.findByAulaCurso(Curso.PRIMERO)).thenReturn(Arrays.asList(alumno));

        List<Alumno> resultado = alumnoService.buscarPorCurso(Curso.PRIMERO);

        assertEquals(1, resultado.size());
        assertEquals(Curso.PRIMERO, resultado.get(0).getCurso());
    }
}

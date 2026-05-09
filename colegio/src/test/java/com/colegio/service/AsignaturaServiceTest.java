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

import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.model.Especialidad;
import com.colegio.model.Grupo;
import com.colegio.model.Profesor;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.ProfesorRepository;

/**
 * Pruebas unitarias del servicio {@link AulaService}.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private AulaService aulaService;

    private Aula aula;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        aula = new Aula(Curso.PRIMERO, Grupo.A, 25);
        profesor = new Profesor("Carlos", "Martinez", "carlos@colegio.com", Especialidad.GENERAL);
        profesor.setCodigo("PROF-1");
    }

    @Test
    @DisplayName("listarAulas devuelve lista correcta")
    void listarAulas_devuelveListaCorrecta() {
        when(aulaRepository.findAll()).thenReturn(Arrays.asList(aula));

        List<Aula> resultado = aulaService.listarAulas();

        assertEquals(1, resultado.size());
        assertEquals(Curso.PRIMERO, resultado.get(0).getCurso());
    }

    @Test
    @DisplayName("buscarAulaPorId devuelve el aula correcta")
    void buscarAulaPorId_devuelveAulaCorrecta() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));

        Aula resultado = aulaService.buscarAulaPorId(1L);

        assertNotNull(resultado);
        assertEquals(Curso.PRIMERO, resultado.getCurso());
    }

    @Test
    @DisplayName("buscarAulaPorId lanza excepcion si no existe")
    void buscarAulaPorId_lanzaExcepcionSiNoExiste() {
        when(aulaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            aulaService.buscarAulaPorId(99L);
        });
    }

    @Test
    @DisplayName("borrarAula llama a deleteById una vez")
    void borrarAula_llamaDeleteById() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));

        aulaService.borrarAula(1L);

        verify(aulaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("guardarAula lanza excepcion si capacidad invalida")
    void guardarAula_lanzaExcepcionSiCapacidadInvalida() {
        Aula invalida = new Aula(Curso.PRIMERO, Grupo.A, 10);

        assertThrows(RuntimeException.class, () -> {
            aulaService.guardarAula(invalida);
        });
    }

    @Test
    @DisplayName("actualizarAula lanza excepcion si capacidad invalida")
    void actualizarAula_lanzaExcepcionSiCapacidadInvalida() {
        Aula datos = new Aula(Curso.PRIMERO, Grupo.A, 50);
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));

        assertThrows(RuntimeException.class, () -> {
            aulaService.actualizarAula(1L, datos);
        });
    }

    @Test
    @DisplayName("asignarTutor lanza excepcion si profesor ya es tutor de otra aula")
    void asignarTutor_lanzaExcepcionSiProfesorYaEsTutor() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(aulaRepository.existsByTutor(profesor)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            aulaService.asignarTutor(1L, 1L);
        });
    }

    @Test
    @DisplayName("asignarTutor asigna el tutor correctamente")
    void asignarTutor_asignaTutorCorrectamente() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(aulaRepository.existsByTutor(profesor)).thenReturn(false);
        when(aulaRepository.save(aula)).thenReturn(aula);

        Aula resultado = aulaService.asignarTutor(1L, 1L);

        assertEquals(profesor, resultado.getTutor());
    }
}

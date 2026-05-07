package com.colegio.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.colegio.entity.Asignatura;
import com.colegio.repository.AsignaturaRepository;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    private Asignatura asignatura;

    @BeforeEach
    void setUp() {
        asignatura = new Asignatura("3ºA",);

    }

    // ============================================================
    // TESTS
    // ============================================================
}

package com.colegio.dataloader;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.config.InicializadorConfig;
import com.colegio.model.Alumno;
import com.colegio.repository.AlumnoRepository;
import com.colegio.util.Constantes;

@Component
public class AlumnoInitializer {

    @Autowired
    private AlumnoService alumnoService

    @Autowired
    private InicializadorConfig config;

    private final Faker faker = new Faker(new Locale("es"));

}

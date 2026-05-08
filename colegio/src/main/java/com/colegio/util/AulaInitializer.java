package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.colegio.model.Aula;
import com.colegio.repository.AulaRepository;

@Component
@Order(1)
public class AulaInitializer implements CommandLineRunner {

    @Autowired
    private AulaRepository aulaRepository;

    private static final String[] CURSOS = {"1º", "2º", "3º", "4º", "5º", "6º"};
    private static final String[] GRUPOS = {"A", "B"};

    @Override
    public void run(String... args) {
        for (String curso : Constantes.CURSOS) {
            for (String grupo : Constantes.GRUPOS) {
                String cursoCompleto = curso + grupo;
                if (!aulaRepository.existsByCurso(cursoCompleto)) {
                    Aula aula = new Aula();
                    aula.setCurso(cursoCompleto);
                    aula.setCapacidad(30);
                    aulaRepository.save(aula);
                }
            }
        }
    }
}

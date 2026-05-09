package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Aula;
import com.colegio.repository.AulaRepository;

@Component
public class AulaInitializater {

    @Autowired
    private AulaRepository aulaRepository;

    public void iniciarAulas() {
        for (String curso : Constantes.CURSOS) {
            for (String grupo : Constantes.GRUPOS) {
                String codigo = curso + grupo;
                if (!aulaRepository.existsByCodigo(codigo)) {
                    Aula aula = new Aula(curso, grupo, 30);
                    aulaRepository.save(aula);
                }
            }
        }
    }
}

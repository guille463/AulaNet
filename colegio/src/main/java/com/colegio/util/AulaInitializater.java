package com.colegio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colegio.model.Aula;
import com.colegio.model.Curso;
import com.colegio.model.Grupo;
import com.colegio.repository.AulaRepository;

/**
 * Inicializador de aulas del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Component
public class AulaInitializater {

    @Autowired
    private AulaRepository aulaRepository;

    public void iniciarAulas() {
        for (Curso curso : Curso.values()) {
            for (Grupo grupo : Grupo.values()) {
                String codigo = curso.getEtiqueta() + grupo.getEtiqueta();
                if (!aulaRepository.existsByCodigo(codigo)) {
                    Aula aula = new Aula(curso, grupo, 25);
                    aulaRepository.save(aula);
                }
            }
        }
    }
}

package com.colegio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.model.Aula;
import com.colegio.repository.AulaRepository;

@Service
public class AulaService {

    @Autowired
    AulaRepository aulaRepository;

    public List<Aula> findAll() {
        return aulaRepository.findAll();
    }

    public List<Aula> findByCurso(String curso) {
        return aulaRepository.findByCurso(curso);
    }

    public Optional<Aula> findByCodigo(String codigo) {
        return aulaRepository.findByCodigo(codigo);
    }

    public List<Aula> findAulasConPlazasLibres() {
        return aulaRepository.findAulasConPlazasLibres();
    }

    public int countAlumnos(Long aulaId) {
        return aulaRepository.countAlumnosByAulaId(aulaId);
    }
}

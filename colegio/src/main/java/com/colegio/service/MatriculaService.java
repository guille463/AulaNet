package com.colegio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Alumno;
import com.colegio.entity.Asignatura;
import com.colegio.entity.Matricula;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.repository.MatriculaRepository;
import com.colegio.util.Constantes;

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id).orElseThrow(() -> new RuntimeException("La Matricula con id: " + id + " no existe."));
    }

    public Matricula guardarMatricula(Matricula matricula) {

        Alumno alumno = alumnoRepository.findById(matricula.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(matricula.getAsignatura().getId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        matricula.setAlumno(alumno);
        matricula.setAsignatura(asignatura);

        Matricula guardada = matriculaRepository.save(matricula);
        guardada.setCodigo(Constantes.PREFIJO_MATR + guardada.getId());
        return matriculaRepository.save(guardada);
    }

    public Matricula actualizarMatricula(Long id, Matricula matricula) {
        Matricula existente = buscarMatriculaPorId(id);
        existente.setAlumno(matricula.getAlumno());
        existente.setAsignatura(matricula.getAsignatura());
        existente.setCurso(matricula.getCurso());
        existente.setFechaMatr(matricula.getFechaMatr());
        existente.setNota(matricula.getNota());
        return matriculaRepository.save(existente);
    }

    public void borrarmatricula(Long id) {
        matriculaRepository.deleteById(id);
    }
}

package com.colegio.service; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Alumno;
import com.colegio.repository.AlumnoRepository;


@Service
public class AlumnoService{
    @Autowired
    private AlumnoRepository alumnoRepository; 

    public List<Alumno> ListarAlumnos(){
         return alumnoRepository.findAll();
       
    }

    public Alumno buscarPorid(Long id){
        return  alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno con id: " + id + " no encontrado")); 
    }

    public Alumno guardarAlumno(Alumno alumno){
        return alumnoRepository.save(alumno); 
    }

  
}


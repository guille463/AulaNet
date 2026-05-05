package com.colegio.service; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Profesor;
import com.colegio.repository.ProfesorRepository;
import com.colegio.util.Constantes;


@Service 
public class ProfesorService{
    @Autowired
    ProfesorRepository profesorRepository; 

    public List<Profesor>listarProfesores(){
        return profesorRepository.findAll(); 
    }

    public Profesor buscarProfesorPorId(Long id){
        return profesorRepository.findById(id).orElseThrow(() -> new RuntimeException("El profesor con id: " + id + " no existe")); 

    }

    public Profesor guardarProfesor(Profesor profesor){
         Profesor guardado = profesorRepository.save(profesor);
    guardado.setCodigo(Constantes.PREFIJO_PROFESOR + guardado.getId());
    return profesorRepository.save(guardado);
    }

    public Profesor actualizarProfesor(Long id, Profesor profesor){
    Profesor existente = buscarProfesorPorId(id); 
    existente.setNombre(profesor.getNombre());
    existente.setApellido(profesor.getApellido());
    existente.setEmail(profesor.getEmail());
    existente.setFechaNac(profesor.getFechaNac());
    existente.setCurso(profesor.getCurso());
    return profesorRepository.save(existente);
    }

    public void borrarProfesor(Long id) {
    profesorRepository.deleteById(id);
}

}
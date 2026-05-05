package com.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.entity.Alumno;
import com.colegio.service.AlumnoService;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController{
    
@Autowired
private AlumnoService alumnoService;

@GetMapping
public List<Alumno> listar(){
return alumnoService.ListarAlumnos(); 
}

@GetMapping("/{id}")
public Alumno obtenerPorid(@PathVariable Long id){
    return alumnoService.buscarPorid(id); 
}

@PostMapping
public Alumno guardar(@RequestBody Alumno alumno){
    return  alumnoService.guardarAlumno(alumno);
}
    
@PutMapping("/{id}")
public Alumno actualizarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
   return alumnoService.actualizarAlumno(id, alumno); 

}



}




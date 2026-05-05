package com.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.entity.Asignatura;
import com.colegio.service.AsignaturaService;

@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController{
    
@Autowired
private AsignaturaService asignaturaService;

@GetMapping
public List<Asignatura> listar(){
return asignaturaService.listarAsignaturas(); 
}

@GetMapping("/{id}")
public Asignatura obtenerPorid(@PathVariable Long id){
    return asignaturaService.buscarAsignaturaPorId(id); 
}

@PostMapping
public Asignatura guardar(@RequestBody Asignatura Asignatura){
    return  asignaturaService.guardarAsignatura(Asignatura);
}
    
@PutMapping("/{id}")
public Asignatura actualizarAsignatura(@RequestBody Asignatura Asignatura, @PathVariable Long id){
   return asignaturaService.actualizarAsignatura(id, Asignatura); 

}

@DeleteMapping("/{id}")
public void borrar(@PathVariable Long id) {
    asignaturaService.borrarAsignatura(id);
}

}
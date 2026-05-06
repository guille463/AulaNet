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

import com.colegio.entity.Matricula;
import com.colegio.service.MatriculaService;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController{
    @Autowired
   private MatriculaService matriculaService; 

   @GetMapping 
   public List<Matricula>mostrarMatriculas(){
    return matriculaService.listarMatriculas(); 
   }

   @GetMapping ("/{id}")
   public Matricula mostrarMatriculaPorid(@PathVariable Long id){
    return matriculaService.buscarMatriculaPorId(id); 
   }

   @PostMapping
   public Matricula guardarMatricula(@RequestBody Matricula matricula){
    return matriculaService.guardarMatricula(matricula); 
   }
    
   @PutMapping("/{id}")
public Matricula actualizarMatricula(@RequestBody Matricula matricula, @PathVariable Long id){
   return matriculaService.actualizarMatricula(id, matricula);  

}

@DeleteMapping("/{id}")
public void borrar(@PathVariable Long id) {
    matriculaService.borrarmatricula(id);
    
}
}



package com.colegio.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alumnos")
public class Alumno{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id; 
   private String nombre;
   private String apellido; 
   private String email; 
   private LocalDate fechaNac; 
   private String curso; 

   public Alumno(){

   }

    public Alumno(String email, String nombre, String apellido, LocalDate fechaNac, String curso) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido; 
        this.fechaNac = fechaNac; 
        this.curso = curso; 
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

     public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac( LocalDate  fechaNac) {
        this.fechaNac = fechaNac;
    }

      public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }



    @Override
  
      public String toString() {
    return "Alumno {id = " + id + ", nombre = " + nombre + ", apellido = " + apellido + ", email = " + email + ", fechaNacimiento = " + fechaNac + ", curso = " + curso + "}";
}

  
   
   

}
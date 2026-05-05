package com.colegio.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="profesores")
public class Profesor{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    private String nombre; 
    private String apellido; 
    private String email; 
    private LocalDate fechaNac; 
    private String curso; 
    private String especialidad;
    private String tutorDeCurso;

    public Profesor(){

    }

    public Profesor(String nombre, String apellido,String email, LocalDate fechaNac, String curso, String especialidad,  String tutorDeCurso ) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNac = fechaNac;
        this.curso = curso;
        this.especialidad = especialidad; 
        this.tutorDeCurso = tutorDeCurso; 
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTutorDeCurso() {
        return tutorDeCurso;
    }

    public void setTutorDeCurso(String tutorDeCurso) {
        this.tutorDeCurso = tutorDeCurso;
    }

    public Long getId() {
        return id;
    }

    

    
}



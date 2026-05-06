package com.colegio.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="matriculas")

public class Matricula{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    private String codigo; 
    private double nota; 
    private LocalDate fechaMatr;
    private Alumno alumno; 
    private Asignatura asignatura;  
    private String curso; 

    public Matricula() {
    }

    public Matricula(Alumno alumno, Asignatura asignatura, LocalDate fechaMatr, double nota, String curso) {
        this.id = id; 
        this.codigo = codigo; 
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.fechaMatr = fechaMatr;
        this.nota = nota;
        this.curso = curso; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public LocalDate getFechaMatr() {
        return fechaMatr;
    }

    public void setFechaMatr(LocalDate fechaMatr) {
        this.fechaMatr = fechaMatr;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

      public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

      @Override
  
      public String toString() {
    return "Alumno {id = " + id + ", Nota: " + nota +  ", curso = " + curso + ", Fecha de Matricula: " + fechaMatr + ", Asignatura: " + asignatura + "}";
      
}
  
}




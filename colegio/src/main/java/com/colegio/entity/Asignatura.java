package com.colegio.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    private String codigo;
    private String nombre; 
    private int creditos; 
    private String curso; 
    private int horasSemana;  
    private String descripcion; 

    public Asignatura() {
    }
    
    public Asignatura(String codigo, int creditos, String curso, int horasSemana, String nombre, String descripcion) {
        this.codigo = codigo;
        this.creditos = creditos;
        this.curso = curso;
        this.horasSemana = horasSemana;
        this.nombre = nombre;
        this.descripcion = descripcion; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

      public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   
    
     @Override
  
      public String toString() {
    return "Alumno {id = " + id + ", nombre = " + nombre + ", Descripcion: " + descripcion + ", Creditos: " + creditos + ", Curso: " + curso + ",HorasSemana: " + horasSemana  + "}";
      
}
  
}



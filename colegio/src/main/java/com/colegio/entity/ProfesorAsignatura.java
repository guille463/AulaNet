package com.colegio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor_asignatura")
public class ProfesorAsignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String curso;
    private int horasSemanales;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    public ProfesorAsignatura() {
    }

    public ProfesorAsignatura(String curso, int horasSemanales, Profesor profesor, Asignatura asignatura) {
        this.curso = curso;
        this.horasSemanales = horasSemanales;
        this.profesor = profesor;
        this.asignatura = asignatura;
    }

    public Long getId() {
        return id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public String toString() {
        return "ProfesorAsignatura{id:" + id + ", curso:" + curso + ", horasSemanales:" + horasSemanales + ", profesor:" + profesor.getId() + ", asignatura:" + asignatura.getId() + "}";
    }
}

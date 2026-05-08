package com.colegio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String curso;
    private int capacidad;

    public Aula() {

    }

    public Aula(int capacidad, String codigo, String curso) {
        this.capacidad = capacidad;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getcapacidad() {
        return capacidad;
    }

    public void setcapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Aula{id:" + id + ", curso:" + curso + ", capacidad:" + capacidad
                + "}";
    }

    public void setCapacidad(int i) {
        throw new UnsupportedOperationException("NO");
    }

}

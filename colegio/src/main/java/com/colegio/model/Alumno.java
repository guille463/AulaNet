package com.colegio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entidad que representa a un alumno del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador con prefijo {@code ALUM-}
     */
    @Column(unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    /**
     * Fecha de nacimiento del alumno
     */
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Aula a la que pertenece el alumno
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Alumno() {
    }

    public Alumno(String nombre, String apellido, LocalDate fechaNacimiento, Aula aula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.aula = aula;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    public Long getId() {
        return id;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     * Curso derivado del aula asignada, no persistido
     */
    @Transient
    public Curso getCurso() {
        return aula != null ? aula.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Alumno{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento
                + ", aula=" + (aula != null ? aula.getCodigo() : "sin aula") + "}";
    }
}

package com.colegio.model;

import java.time.LocalDate;

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
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
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
    private String codigo;

    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNac;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aula_id")
    private Aula aula;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Alumno() {
    }

    public Alumno(String email, String nombre, String apellido,
            LocalDate fechaNac, Aula aula) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
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

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     * Curso del aula asignada
     */
    @Transient
    public String getCurso() {
        return aula != null ? aula.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Alumno{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", email=" + email
                + ", fechaNac=" + fechaNac + ", aula=" + (aula != null ? aula.getCodigo() : "sin aula") + "}";
    }
}

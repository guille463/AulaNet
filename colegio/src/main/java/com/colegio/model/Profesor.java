package com.colegio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un profesor del colegio.
 *
 *
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador con prefijo {@code PROF-}
     */
    private String codigo;

    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNac;
    private String especialidad;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Profesor() {
    }

    public Profesor(String nombre, String apellido, String email,
            LocalDate fechaNac, String especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNac = fechaNac;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Profesor{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", email=" + email
                + ", fechaNac=" + fechaNac + ", especialidad=" + especialidad + "}";
    }
}

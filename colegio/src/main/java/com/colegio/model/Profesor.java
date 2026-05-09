package com.colegio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un profesor del colegio.
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
    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Profesor() {
    }

    public Profesor(String nombre, String apellido, String email, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Profesor{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", email=" + email
                + ", especialidad=" + especialidad + "}";
    }
}

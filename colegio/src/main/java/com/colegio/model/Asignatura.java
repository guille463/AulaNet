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
 * Entidad que representa una asignatura del colegio.
 *
 * <p>
 * tabla {@code asignaturas}, relacion con profesores
 * {@link ProfesorAsignatura}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Entity
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador con prefijo {@code ASG-}
     */
    @Column(unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    /**
     * Curso al que pertenece la asignatura
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Curso curso;

    /**
     * Horas semanales de la asignatura, entre 1 y 6
     */
    @Column(nullable = false)
    private int horasSemana;

    private String descripcion;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Asignatura() {
    }

    public Asignatura(String nombre, Curso curso, int horasSemana, String descripcion) {
        this.nombre = nombre;
        this.curso = curso;
        this.horasSemana = horasSemana;
        this.descripcion = descripcion;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
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

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Asignatura{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", curso=" + curso + ", horasSemana=" + horasSemana
                + ", descripcion=" + descripcion + "}";
    }
}

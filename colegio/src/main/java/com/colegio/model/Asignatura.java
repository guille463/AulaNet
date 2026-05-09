package com.colegio.model;

import jakarta.persistence.Entity;
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
 * @version 2.0
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
    private String codigo;

    private String nombre;

    /**
     * Curso al que pertenece la asignatura, ej: {@code 3º}
     */
    private String curso;

    /**
     * Horas semanales de la asignatura
     */
    private int horasSemana;

    private String descripcion;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Asignatura() {
    }

    public Asignatura(String nombre, String curso, int horasSemana, String descripcion) {
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

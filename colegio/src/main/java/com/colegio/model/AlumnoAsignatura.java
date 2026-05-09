package com.colegio.model;

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
 * Entidad que representa la matricula de un alumno en una asignatura.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Entity
@Table(name = "alumno_asignatura")
public class AlumnoAsignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo {@code MTR-}
     */
    @Column(unique = true, nullable = false)
    private String codigo;

    /**
     * Nota del alumno en la asignatura
     */
    @Column(nullable = false)
    private double nota;

    /**
     * Alumno matriculado en la asignatura
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    /**
     * Asignatura en la que esta matriculado el alumno
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public AlumnoAsignatura() {
    }

    public AlumnoAsignatura(double nota, Alumno alumno, Asignatura asignatura) {
        this.nota = nota;
        this.alumno = alumno;
        this.asignatura = asignatura;
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
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

    @Transient
    public Curso getCurso() {
        return asignatura != null ? asignatura.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "AlumnoAsignatura{id=" + id + ", codigo=" + codigo
                + ", nota=" + nota
                + ", alumno=" + (alumno != null ? alumno.getCodigo() : "sin alumno")
                + ", asignatura=" + (asignatura != null ? asignatura.getCodigo() : "sin asignatura")
                + "}";
    }
}

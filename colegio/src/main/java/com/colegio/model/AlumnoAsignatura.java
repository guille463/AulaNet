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
 * <p>
 * Actua como clase de asociacion entre {@link Alumno} y {@link Asignatura}.
 * Almacena la nota obtenida y se identifica con un codigo de prefijo
 * {@code MTR-}. Se crea automaticamente al guardar un alumno.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 * @see Alumno
 * @see Asignatura
 */
@Entity
@Table(name = "alumno_asignatura")
public class AlumnoAsignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador con prefijo {@code MTR-}.
     */
    @Column(unique = true)
    private String codigo;

    /**
     * Nota del alumno en la asignatura. No puede ser nula.
     */
    @Column(nullable = false)
    private double nota;

    /**
     * Alumno matriculado en la asignatura.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    /**
     * Asignatura en la que esta matriculado el alumno.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio.
     */
    public AlumnoAsignatura() {
    }

    /**
     * Crea una matricula con sus datos basicos.
     *
     * @param nota       nota inicial del alumno en la asignatura
     * @param alumno     alumno matriculado
     * @param asignatura asignatura en la que se matricula
     */
    public AlumnoAsignatura(double nota, Alumno alumno, Asignatura asignatura) {
        this.nota = nota;
        this.alumno = alumno;
        this.asignatura = asignatura;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el id de la matricula.
     *
     * @return id unico de la matricula
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo con prefijo {@code MTR-}.
     *
     * @return codigo de la matricula, o {@code null}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo de la matricula.
     *
     * @param codigo codigo con formato {@code MTR-<id>}
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve la nota del alumno en la asignatura.
     *
     * @return nota de la matricula
     */
    public double getNota() {
        return nota;
    }

    /**
     * Establece la nota del alumno en la asignatura.
     *
     * @param nota nueva nota de la matricula
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    /**
     * Devuelve el alumno matriculado.
     *
     * @return alumno de la matricula
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno matriculado.
     *
     * @param alumno nuevo alumno de la matricula
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Devuelve la asignatura de la matricula.
     *
     * @return asignatura de la matricula
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura de la matricula.
     *
     * @param asignatura nueva asignatura de la matricula
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Devuelve el curso al que pertenece la asignatura.
     *
     * <p>
     * ({@code @Transient}). Si la asignatura no esta asignada, devuelve
     * {@code null}.
     * </p>
     *
     * @return curso de la asignatura, o {@code null} si no tiene asignatura
     */
    @Transient
    public Curso getCurso() {
        return asignatura != null ? asignatura.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve el texto de la matricula con sus campos principales.
     *
     * @return cadena con los datos de la matricula
     */
    @Override
    public String toString() {
        return "AlumnoAsignatura{id=" + id + ", codigo=" + codigo
                + ", nota=" + nota
                + ", alumno=" + (alumno != null ? alumno.getCodigo() : "sin alumno")
                + ", asignatura=" + (asignatura != null ? asignatura.getCodigo() : "sin asignatura")
                + "}";
    }
}

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
 * Entidad que representa la relacion entre un profesor y una asignatura.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 */
@Entity
@Table(name = "profesor_asignatura")
public class ProfesorAsignatura {

    /**
     * Identificador unico generado automaticamente por JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Horas semanales que el profesor dedica a la asignatura, entre 1 y 6
     */
    @Column(nullable = false)
    private int horasSemanales;

    /**
     * Profesor que imparte la asignatura, cargado con {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    /**
     * Asignatura impartida por el profesor, cargada con {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio requerido por JPA.
     */
    public ProfesorAsignatura() {
    }

    /**
     * Constructor con parametros para crear una relacion profesor-asignatura.
     *
     * @param horasSemanales horas semanales dedicadas a la asignatura
     * @param profesor profesor que imparte la asignatura
     * @param asignatura asignatura impartida
     */
    public ProfesorAsignatura(int horasSemanales, Profesor profesor, Asignatura asignatura) {
        this.horasSemanales = horasSemanales;
        this.profesor = profesor;
        this.asignatura = asignatura;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico de la relacion.
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve las horas semanales dedicadas a la asignatura.
     */
    public int getHorasSemanales() {
        return horasSemanales;
    }

    /**
     * Establece las horas semanales dedicadas a la asignatura.
     */
    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    /**
     * Devuelve el profesor que imparte la asignatura.
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Establece el profesor que imparte la asignatura.
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * Devuelve la asignatura impartida por el profesor.
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura impartida por el profesor.
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Curso derivado de la asignatura, no persistido.
     */
    @Transient
    public Curso getCurso() {
        return asignatura != null ? asignatura.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve una representacion en texto de la relacion profesor-asignatura.
     */
    @Override
    public String toString() {
        return "ProfesorAsignatura{id=" + id + ", horasSemanales=" + horasSemanales
                + ", profesor=" + (profesor != null ? profesor.getCodigo() : "sin profesor")
                + ", asignatura=" + (asignatura != null ? asignatura.getCodigo() : "sin asignatura")
                + "}";
    }
}

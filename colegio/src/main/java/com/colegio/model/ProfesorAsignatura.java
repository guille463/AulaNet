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
 * <p>
 * Actua como clase de asociacion entre {@link Profesor} y {@link Asignatura}.
 * Almacena las horas semanales que el profesor dedica a la asignatura.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see Profesor
 * @see Asignatura
 */
@Entity
@Table(name = "profesor_asignatura")
public class ProfesorAsignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Horas semanales que el profesor dedica a la asignatura, entre 1 y 6.
     */
    @Column(nullable = false)
    private int horasSemanales;

    /**
     * Profesor que imparte la asignatura.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    /**
     * Asignatura impartida por el profesor.
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
    public ProfesorAsignatura() {
    }

    /**
     * Crea una relacion profesor-asignatura con sus datos basicos.
     *
     * @param horasSemanales horas semanales dedicadas a la asignatura
     * @param profesor       profesor que imparte la asignatura
     * @param asignatura     asignatura impartida
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
     * Devuelve el id de la relacion.
     *
     * @return id unico de la relacion
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve las horas semanales del profesor en la asignatura.
     *
     * @return horas semanales
     */
    public int getHorasSemanales() {
        return horasSemanales;
    }

    /**
     * Establece las horas semanales del profesor en la asignatura.
     *
     * @param horasSemanales nuevas horas semanales
     */
    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    /**
     * Devuelve el profesor de la relacion.
     *
     * @return profesor que imparte la asignatura
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Establece el profesor de la relacion.
     *
     * @param profesor nuevo profesor de la relacion
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * Devuelve la asignatura de la relacion.
     *
     * @return asignatura impartida
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura de la relacion.
     *
     * @param asignatura nueva asignatura de la relacion
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
     * Devuelve el texto de la relacion con sus campos principales.
     *
     * @return cadena con los datos de la relacion
     */
    @Override
    public String toString() {
        return "ProfesorAsignatura{id=" + id + ", horasSemanales=" + horasSemanales
                + ", profesor=" + (profesor != null ? profesor.getCodigo() : "sin profesor")
                + ", asignatura=" + (asignatura != null ? asignatura.getCodigo() : "sin asignatura")
                + "}";
    }
}

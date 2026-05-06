package com.colegio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre un profesor y una asignatura.
 *
 * <p>
 * Implementa la relacion N:M entre {@link Profesor} y {@link Asignatura} con
 * campos extra como el curso y las horas semanales. Se mapea a la tabla
 * {@code profesor_asignatura} en la base de datos.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
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
     * Curso en el que el profesor imparte la asignatura
     */
    private String curso;

    /**
     * Horas semanales que el profesor dedica a la asignatura
     */
    private int horasSemanales;

    /**
     * Profesor que imparte la asignatura, cargado con {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    /**
     * Asignatura impartida por el profesor, cargada con {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id")
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
     * @param curso curso en el que se imparte la asignatura
     * @param horasSemanales horas semanales dedicadas a la asignatura
     * @param profesor profesor que imparte la asignatura
     * @param asignatura asignatura impartida
     */
    public ProfesorAsignatura(String curso, int horasSemanales, Profesor profesor, Asignatura asignatura) {
        this.curso = curso;
        this.horasSemanales = horasSemanales;
        this.profesor = profesor;
        this.asignatura = asignatura;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico de la relacion.
     *
     * @return id de la relacion
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el curso en el que se imparte la asignatura.
     *
     * @return curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso en el que se imparte la asignatura.
     *
     * @param curso curso a asignar
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Devuelve las horas semanales dedicadas a la asignatura.
     *
     * @return horas semanales
     */
    public int getHorasSemanales() {
        return horasSemanales;
    }

    /**
     * Establece las horas semanales dedicadas a la asignatura.
     *
     * @param horasSemanales horas semanales a asignar
     */
    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    /**
     * Devuelve el profesor que imparte la asignatura.
     *
     * @return objeto {@link Profesor}
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Establece el profesor que imparte la asignatura.
     *
     * @param profesor profesor a asignar
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * Devuelve la asignatura impartida por el profesor.
     *
     * @return objeto {@link Asignatura}
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura impartida por el profesor.
     *
     * @param asignatura asignatura a asignar
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve una representacion en texto de la relacion profesor-asignatura.
     *
     * @return cadena con los datos de la relacion
     */
    @Override
    public String toString() {
        return "ProfesorAsignatura{id=" + id + ", curso=" + curso + ", horasSemanales=" + horasSemanales
                + ", profesor=" + profesor.getId() + ", asignatura=" + asignatura.getId() + "}";
    }
}

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
 * Cada asignatura pertenece a un {@link Curso} y tiene un numero de horas
 * semanales asignadas. Los profesores que la imparten se gestionan a traves de
 * {@link ProfesorAsignatura}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see Curso
 * @see ProfesorAsignatura
 */
@Entity
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador unico con prefijo {@code ASG-}.
     */
    @Column(unique = true)
    private String codigo;

    /**
     * Nombre de la asignatura. No puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Curso al que pertenece la asignatura.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Curso curso;

    /**
     * Horas semanales de la asignatura, entre 1 y 6.
     */
    @Column(nullable = false)
    private int horasSemana;

    /**
     * Descripcion opcional de la asignatura.
     */
    private String descripcion;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio.
     */
    public Asignatura() {
    }

    /**
     * Crea una asignatura con sus datos basicos.
     *
     * @param nombre      nombre de la asignatura
     * @param curso       curso al que pertenece
     * @param horasSemana horas semanales de la asignatura
     * @param descripcion descripcion de la asignatura
     */
    public Asignatura(String nombre, Curso curso, int horasSemana, String descripcion) {
        this.nombre = nombre;
        this.curso = curso;
        this.horasSemana = horasSemana;
        this.descripcion = descripcion;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el id de la asignatura.
     *
     * @return id unico de la asignatura
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo con prefijo {@code ASG-}.
     *
     * @return codigo de la asignatura, o {@code null}.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo de la asignatura.
     *
     * @param codigo codigo con formato {@code ASG-<id>}
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre de la asignatura.
     *
     * @return nombre de la asignatura
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la asignatura.
     *
     * @param nombre nuevo nombre de la asignatura
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el curso al que pertenece la asignatura.
     *
     * @return curso de la asignatura
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Establece el curso de la asignatura.
     *
     * @param curso nuevo curso de la asignatura
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * Devuelve las horas semanales de la asignatura.
     *
     * @return horas semanales
     */
    public int getHorasSemana() {
        return horasSemana;
    }

    /**
     * Establece las horas semanales de la asignatura.
     *
     * @param horasSemana nuevas horas semanales
     */
    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

    /**
     * Devuelve la descripcion de la asignatura.
     *
     * @return descripcion de la asignatura
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripcion de la asignatura.
     *
     * @param descripcion nueva descripcion de la asignatura
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve el texto de la asignatura con sus campos principales.
     *
     * @return cadena con los datos de la asignatura
     */
    @Override
    public String toString() {
        return "Asignatura{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", curso=" + curso + ", horasSemana=" + horasSemana
                + ", descripcion=" + descripcion + "}";
    }
}

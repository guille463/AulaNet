package com.colegio.model;

import java.time.LocalDate;

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
 * Entidad que representa a un alumno del colegio.
 *
 * <p>
 * Cada alumno pertenece a un {@link Aula} y a traves de ella a un
 * {@link Curso}. Al ser creado, se le asigna automaticamente un codigo con
 * prefijo {@code ALUM-} y se matricula en todas las asignaturas
 * correspondientes a su curso.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 3.0
 * @see Aula
 * @see Curso
 */
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador unico con prefijo {@code ALUM-}.
     */
    @Column(unique = true)
    private String codigo;

    /**
     * Nombre del alumno. No puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Apellido del alumno. No puede ser nulo.
     */
    @Column(nullable = false)
    private String apellido;

    /**
     * Fecha de nacimiento del alumno. Usamosa {@link LocalDate}. No puede ser
     * nula.
     */
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Aula a la que pertenece el alumno.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio.
     */
    public Alumno() {
    }

    /**
     * Crea un alumno con sus datos basicos.
     *
     * @param nombre          nombre del alumno
     * @param apellido        apellido del alumno
     * @param fechaNacimiento fecha de nacimiento del alumno
     * @param aula            aula del alumno
     */
    public Alumno(String nombre, String apellido, LocalDate fechaNacimiento, Aula aula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.aula = aula;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el id.
     *
     * @return id unico del alumno
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo con prefijo {@code ALUM-}.
     *
     * @return codigo del alumno o {@code null}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el codigo del alumno.
     *
     * @param codigo codigo con formato {@code ALUM-<id>}
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre del alumno.
     *
     * @return nombre del alumno
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre nuevo nombre del alumno
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el apellido del alumno.
     *
     * @return apellido del alumno
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece apellido del alumno.
     *
     * @param apellido nuevo apellido del alumno
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Devuelve la fecha de nacimiento del alumno.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del alumno.
     *
     * @param fechaNacimiento nueva fecha de nacimiento
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Devuelve el aula del alumno.
     *
     * @return aula del alumno
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * Establece el aula del alumno.
     *
     * @param aula nueva aula asignada
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     * Devuelve el curso al que pertenece el alumno.
     *
     * <p>
     * ({@code @Transient}).Si el alumno no tiene aula asignada, devuelve
     * {@code null}.
     * </p>
     *
     * @return curso del alumno, o {@code null} si no tiene aula asignada
     */
    @Transient
    public Curso getCurso() {
        return aula != null ? aula.getCurso() : null;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve el texto del alumno con sus campos principales.
     *
     * @return cadena con los datos del alumno
     */
    @Override
    public String toString() {
        return "Alumno{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento
                + ", aula=" + (aula != null ? aula.getCodigo() : "sin aula") + "}";
    }
}

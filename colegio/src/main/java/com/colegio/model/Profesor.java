package com.colegio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entidad que representa a un profesor del colegio.
 *
 * <p>
 * Cada profesor tiene una {@link Especialidad} asignada y se identifica con un
 * codigo de prefijo {@code PROF-}. Las asignaturas que imparte se gestionan a
 * traves de {@link ProfesorAsignatura}.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see Especialidad
 * @see ProfesorAsignatura
 */
@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador unico con prefijo {@code PROF-}.
     */
    @Column(unique = true)
    private String codigo;

    /**
     * Nombre del profesor. No puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Apellido del profesor. No puede ser nulo.
     */
    @Column(nullable = false)
    private String apellido;

    /**
     * Email del profesor. Unico y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Especialidad del profesor.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    /**
     * Codigo del aula donde el profesor ejerce de tutor. No se persiste en la
     * base de datos ({@code @Transient}).
     */
    @Transient
    private String codigoAula;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio.
     */
    public Profesor() {
    }

    /**
     * Crea un profesor con sus datos basicos.
     *
     * @param nombre nombre del profesor
     * @param apellido apellido del profesor
     * @param email email del profesor
     * @param especialidad especialidad del profesor
     */
    public Profesor(String nombre, String apellido, String email, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.especialidad = especialidad;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el id del profesor.
     *
     * @return id unico del profesor
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo con prefijo {@code PROF-}.
     *
     * @return codigo del profesor, o {@code null} si aun no ha sido persistido
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del profesor.
     *
     * @param codigo codigo con formato {@code PROF-<id>}
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre del profesor.
     *
     * @return nombre del profesor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del profesor.
     *
     * @param nombre nuevo nombre del profesor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el apellido del profesor.
     *
     * @return apellido del profesor
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del profesor.
     *
     * @param apellido nuevo apellido del profesor
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Devuelve el email del profesor.
     *
     * @return email del profesor
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del profesor.
     *
     * @param email nuevo email del profesor
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la especialidad del profesor.
     *
     * @return especialidad del profesor
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del profesor.
     *
     * @param especialidad nueva especialidad del profesor
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Devuelve el codigo del aula donde el profesor ejerce de tutor.
     *
     * <p>
     * ({@code @Transient}). Si el profesor no es tutor de ninguna aula,
     * devuelve {@code null}.</p>
     *
     * @return codigo del aula, o {@code null} si no es tutor de ninguna
     */
    public String getCodigoAula() {
        return codigoAula;
    }

    /**
     * Establece el codigo del aula donde el profesor ejerce de tutor.
     *
     * @param codigoAula codigo del aula asignada
     */
    public void setCodigoAula(String codigoAula) {
        this.codigoAula = codigoAula;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve el texto del profesor con sus campos principales.
     *
     * @return cadena con los datos del profesor
     */
    @Override
    public String toString() {
        return "Profesor{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
                + ", apellido=" + apellido + ", email=" + email
                + ", especialidad=" + especialidad + "}";
    }
}

package com.colegio.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un alumno del colegio.
 *
 * <p>
 * Contiene la informacion personal y academica del alumno.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Entity
@Table(name = "alumnos")
public class Alumno {

    /**
     * Identificador unico generado automaticamente por JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador del alumno con prefijo {@code ALUM-}
     */
    private String codigo;

    /**
     * Nombre del alumno
     */
    private String nombre;

    /**
     * Apellido del alumno
     */
    private String apellido;

    /**
     * Correo electronico del alumno
     */
    private String email;

    /**
     * Fecha de nacimiento del alumno
     */
    private LocalDate fechaNac;

    /**
     * Curso en el que esta matriculado el alumno
     */
    private String curso;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio requerido por JPA.
     */
    public Alumno() {
    }

    /**
     * Constructor con parametros para crear un alumno.
     *
     * @param email correo electronico del alumno
     * @param nombre nombre del alumno
     * @param apellido apellido del alumno
     * @param fechaNac fecha de nacimiento del alumno
     * @param curso curso en el que esta matriculado
     */
    public Alumno(String email, String nombre, String apellido, LocalDate fechaNac, String curso) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.curso = curso;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico del alumno.
     *
     * @return id del alumno
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo identificador del alumno.
     *
     * @return codigo con formato {@code ALUM-X}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del alumno.
     *
     * @param codigo codigo a asignar
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
     * @param nombre nombre a asignar
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
     * Establece el apellido del alumno.
     *
     * @param apellido apellido a asignar
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Devuelve el email del alumno.
     *
     * @return email del alumno
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del alumno.
     *
     * @param email email a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la fecha de nacimiento del alumno.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getFechaNac() {
        return fechaNac;
    }

    /**
     * Establece la fecha de nacimiento del alumno.
     *
     * @param fechaNac fecha de nacimiento a asignar
     */
    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     * Devuelve el curso del alumno.
     *
     * @return curso del alumno
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso del alumno.
     *
     * @param curso curso a asignar
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve una representacion en texto del alumno.
     *
     * @return cadena con los datos del alumno
     */
    @Override
    public String toString() {
        return "Alumno{id=" + id + ", nombre=" + nombre + ", apellido=" + apellido
                + ", email=" + email + ", fechaNac=" + fechaNac + ", curso=" + curso + "}";
    }
}

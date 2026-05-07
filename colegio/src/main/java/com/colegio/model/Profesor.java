package com.colegio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un profesor del colegio.
 *
 * <p>
 * Contiene la informacion personal y profesional del profesor. Se mapea a la
 * tabla {@code profesores} en la base de datos. La relacion con las asignaturas
 * se gestiona a traves de {@link ProfesorAsignatura}.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Entity
@Table(name = "profesores")
public class Profesor {

    /**
     * Identificador unico generado automaticamente por JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador del profesor con prefijo {@code PROF-}
     */
    private String codigo;

    /**
     * Nombre del profesor
     */
    private String nombre;

    /**
     * Apellido del profesor
     */
    private String apellido;

    /**
     * Correo electronico del profesor
     */
    private String email;

    /**
     * Fecha de nacimiento del profesor
     */
    private LocalDate fechaNac;

    /**
     * Especialidad o area de conocimiento del profesor
     */
    private String especialidad;

    /**
     * Indica si el profesor es tutor de algun curso
     */
    private boolean esTutor;

    /**
     * Curso del que es tutor el profesor, {@code null} si no es tutor
     */
    private String tutorDeCurso;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio requerido por JPA.
     */
    public Profesor() {
    }

    /**
     * Constructor con parametros para crear un profesor.
     *
     * @param nombre nombre del profesor
     * @param apellido apellido del profesor
     * @param email correo electronico del profesor
     * @param fechaNac fecha de nacimiento del profesor
     * @param especialidad especialidad del profesor
     * @param esTutor indica si es tutor de algun curso
     * @param tutorDeCurso curso del que es tutor, {@code null} si no es tutor
     */
    public Profesor(String nombre, String apellido, String email, LocalDate fechaNac,
            String especialidad, boolean esTutor, String tutorDeCurso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNac = fechaNac;
        this.especialidad = especialidad;
        this.esTutor = esTutor;
        this.tutorDeCurso = tutorDeCurso;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico del profesor.
     *
     * @return id del profesor
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo identificador del profesor.
     *
     * @return codigo con formato {@code PROF-X}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del profesor.
     *
     * @param codigo codigo a asignar
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
     * @param nombre nombre a asignar
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
     * @param apellido apellido a asignar
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
     * @param email email a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la fecha de nacimiento del profesor.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getFechaNac() {
        return fechaNac;
    }

    /**
     * Establece la fecha de nacimiento del profesor.
     *
     * @param fechaNac fecha de nacimiento a asignar
     */
    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     * Devuelve la especialidad del profesor.
     *
     * @return especialidad del profesor
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del profesor.
     *
     * @param especialidad especialidad a asignar
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Indica si el profesor es tutor de algun curso.
     *
     * @return {@code true} si es tutor, {@code false} si no lo es
     */
    public boolean isEsTutor() {
        return esTutor;
    }

    /**
     * Establece si el profesor es tutor de algun curso.
     *
     * @param esTutor {@code true} si es tutor, {@code false} si no lo es
     */
    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    /**
     * Devuelve el curso del que es tutor el profesor.
     *
     * @return curso de tutoria, {@code null} si no es tutor
     */
    public String getTutorDeCurso() {
        return tutorDeCurso;
    }

    /**
     * Establece el curso de tutoria del profesor.
     *
     * @param tutorDeCurso curso a asignar, {@code null} si no es tutor
     */
    public void setTutorDeCurso(String tutorDeCurso) {
        this.tutorDeCurso = tutorDeCurso;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve una representacion en texto del profesor.
     *
     * @return cadena con los datos del profesor
     */
    @Override
    public String toString() {
        return "Profesor{id=" + id + ", nombre=" + nombre + ", apellido=" + apellido
                + ", email=" + email + ", fechaNac=" + fechaNac + ", especialidad=" + especialidad
                + ", esTutor=" + esTutor + ", tutorDeCurso=" + tutorDeCurso + "}";
    }
}

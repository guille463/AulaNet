package com.colegio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa una asignatura del colegio.
 *
 * <p>
 * Contiene la informacion academica de la asignatura. Se mapea a la tabla
 * {@code asignaturas} en la base de datos.
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Entity
@Table(name = "asignaturas")
public class Asignatura {

    /**
     * Identificador unico generado automaticamente por JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador de la asignatura con prefijo {@code ASG-}
     */
    private String codigo;

    /**
     * Nombre de la asignatura
     */
    private String nombre;

    /**
     * Numero de creditos de la asignatura
     */
    private int creditos;

    /**
     * Curso al que pertenece la asignatura
     */
    private String curso;

    /**
     * Horas semanales de la asignatura
     */
    private int horasSemana;

    /**
     * Descripcion breve de la asignatura
     */
    private String descripcion;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio requerido por JPA.
     */
    public Asignatura() {
    }

    /**
     * Constructor con parametros para crear una asignatura.
     *
     * @param codigo codigo identificador de la asignatura
     * @param creditos numero de creditos
     * @param curso curso al que pertenece
     * @param horasSemana horas semanales
     * @param nombre nombre de la asignatura
     * @param descripcion descripcion breve
     */
    public Asignatura(String codigo, int creditos, String curso, int horasSemana, String nombre, String descripcion) {
        this.codigo = codigo;
        this.creditos = creditos;
        this.curso = curso;
        this.horasSemana = horasSemana;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico de la asignatura.
     *
     * @return id de la asignatura
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la asignatura.
     *
     * @param id id a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el codigo identificador de la asignatura.
     *
     * @return codigo con formato {@code ASG-X}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo de la asignatura.
     *
     * @param codigo codigo a asignar
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
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el numero de creditos de la asignatura.
     *
     * @return creditos de la asignatura
     */
    public int getCreditos() {
        return creditos;
    }

    /**
     * Establece el numero de creditos de la asignatura.
     *
     * @param creditos creditos a asignar
     */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    /**
     * Devuelve el curso al que pertenece la asignatura.
     *
     * @return curso de la asignatura
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso de la asignatura.
     *
     * @param curso curso a asignar
     */
    public void setCurso(String curso) {
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
     * @param horasSemana horas semanales a asignar
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
     * @param descripcion descripcion a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve una representacion en texto de la asignatura.
     *
     * @return cadena con los datos de la asignatura
     */
    @Override
    public String toString() {
        return "Asignatura{id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", creditos=" + creditos + ", curso=" + curso + ", horasSemana=" + horasSemana + "}";
    }
}

package com.colegio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa la matricula de un alumno en una asignatura.
 *
 * <p>
 * Actua como tabla intermedia entre {@link Alumno} y {@link Asignatura},
 * </p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 1.0
 */
@Entity
@Table(name = "matriculas")
public class Matricula {

    /**
     * Identificador unico generado automaticamente por JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo identificador de la matricula con prefijo {@code MTR-}
     */
    private String codigo;

    /**
     * Nota obtenida por el alumno en la asignatura
     */
    private double nota;

    /**
     * Fecha en la que se realizo la matricula
     */
    private LocalDate fechaMatr;

    /**
     * Alumno matriculado, cargado con {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    /**
     * Asignatura en la que esta matriculado el alumno, cargada con
     * {@code FetchType.EAGER}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    /**
     * Curso al que corresponde la matricula
     */
    private String curso;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio requerido por JPA.
     */
    public Matricula() {
    }

    /**
     * Constructor con parametros para crear una matricula.
     *
     * @param alumno alumno que se matricula
     * @param asignatura asignatura en la que se matricula
     * @param fechaMatr fecha de matriculacion
     * @param nota nota obtenida
     * @param curso curso al que corresponde
     */
    public Matricula(Alumno alumno, Asignatura asignatura, LocalDate fechaMatr, double nota, String curso) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.fechaMatr = fechaMatr;
        this.nota = nota;
        this.curso = curso;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el identificador unico de la matricula.
     *
     * @return id de la matricula
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la matricula.
     *
     * @param id id a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el codigo identificador de la matricula.
     *
     * @return codigo con formato {@code MTR-X}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo de la matricula.
     *
     * @param codigo codigo a asignar
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
     * @param nota nota a asignar
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    /**
     * Devuelve la fecha de matriculacion.
     *
     * @return fecha de matricula
     */
    public LocalDate getFechaMatr() {
        return fechaMatr;
    }

    /**
     * Establece la fecha de matriculacion.
     *
     * @param fechaMatr fecha a asignar
     */
    public void setFechaMatr(LocalDate fechaMatr) {
        this.fechaMatr = fechaMatr;
    }

    /**
     * Devuelve el alumno matriculado.
     *
     * @return objeto {@link Alumno}
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno matriculado.
     *
     * @param alumno alumno a asignar
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Devuelve la asignatura en la que esta matriculado el alumno.
     *
     * @return objeto {@link Asignatura}
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura de la matricula.
     *
     * @param asignatura asignatura a asignar
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Devuelve el curso de la matricula.
     *
     * @return curso de la matricula
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso de la matricula.
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
     * Devuelve una representacion en texto de la matricula.
     *
     * @return cadena con los datos de la matricula
     */
    @Override
    public String toString() {
        return "Matricula{id=" + id + ", nota=" + nota + ", curso=" + curso
                + ", fechaMatr=" + fechaMatr + ", asignatura=" + asignatura + "}";
    }
}

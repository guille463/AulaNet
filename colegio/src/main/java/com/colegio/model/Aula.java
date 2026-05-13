package com.colegio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un grupo-clase del colegio.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo del grupo-clase, generado como curso+grupo {@code 1ºA}
     */
    @Column(unique = true, nullable = false)
    private String codigo;

    /**
     * Curso academico
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Curso curso;

    /**
     * Grupo dentro del curso
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grupo grupo;

    /**
     * Numero maximo de alumnos, entre 15 y 30
     */
    @Column(nullable = false)
    private int capacidad;

    /**
     * Profesor tutor del aula
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id")
    private Profesor tutor;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public Aula() {
    }

    public Aula(Curso curso, Grupo grupo, int capacidad) {
        this.curso = curso;
        this.grupo = grupo;
        this.codigo = curso.getEtiqueta() + grupo.getEtiqueta();
        this.capacidad = 30;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Profesor getTutor() {
        return tutor;
    }

    public void setTutor(Profesor tutor) {
        this.tutor = tutor;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    @Override
    public String toString() {
        return "Aula{id=" + id + ", codigo=" + codigo + ", curso=" + curso
                + ", grupo=" + grupo + ", capacidad=" + capacidad
                + ", tutor=" + (tutor != null ? tutor.getCodigo() : "sin tutor") + "}";
    }
}

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
 * Entidad que representa un aula del colegio.
 *
 * <p>
 * Cada aula pertenece a un {@link Curso} y un {@link Grupo}, y puede tener un
 * {@link Profesor} asignado como tutor. Su codigo se genera automaticamente
 * combinando la etiqueta del curso y la del grupo.</p>
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 * @see Curso
 * @see Grupo
 * @see Profesor
 */
@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo del aula generado como curso mas grupo, por ejemplo {@code 1ºA}.
     */
    @Column(unique = true, nullable = false)
    private String codigo;

    /**
     * Curso academico al que pertenece el aula.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Curso curso;

    /**
     * Grupo dentro del curso.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grupo grupo;

    /**
     * Numero maximo de alumnos del aula, entre 15 y 30.
     */
    @Column(nullable = false)
    private int capacidad;

    /**
     * Profesor tutor del aula. Puede ser nulo si no tiene tutor asignado.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id", nullable = true)
    private Profesor tutor;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    /**
     * Constructor vacio.
     */
    public Aula() {
    }

    /**
     * Crea un aula con sus datos basicos.
     *
     * <p>
     * El codigo se genera automaticamente combinando las etiquetas de
     * {@code curso} y {@code grupo}. La capacidad se fija en 30.</p>
     *
     * @param curso curso academico del aula
     * @param grupo grupo dentro del curso
     * @param capacidad capacidad maxima de alumnos
     */
    public Aula(Curso curso, Grupo grupo, int capacidad) {
        this.curso = curso;
        this.grupo = grupo;
        this.codigo = curso.getEtiqueta() + grupo.getEtiqueta();
        this.capacidad = 30;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
    /**
     * Devuelve el id del aula.
     *
     * @return id unico del aula
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el codigo del aula.
     *
     * @return codigo del aula
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del aula.
     *
     * @param codigo nuevo codigo del aula
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el curso del aula.
     *
     * @return curso del aula
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Establece el curso del aula.
     *
     * @param curso nuevo curso del aula
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * Devuelve el grupo del aula.
     *
     * @return grupo del aula
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Establece el grupo del aula.
     *
     * @param grupo nuevo grupo del aula
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * Devuelve la capacidad maxima del aula.
     *
     * @return capacidad del aula
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad maxima del aula.
     *
     * @param capacidad nueva capacidad del aula
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Devuelve el profesor tutor del aula.
     *
     * @return tutor del aula, o {@code null} si no tiene tutor asignado
     */
    public Profesor getTutor() {
        return tutor;
    }

    /**
     * Establece el profesor tutor del aula.
     *
     * @param tutor nuevo tutor del aula
     */
    public void setTutor(Profesor tutor) {
        this.tutor = tutor;
    }

    // ============================================================
    // TO STRING
    // ============================================================
    /**
     * Devuelve el texto del aula con sus campos principales.
     *
     * @return cadena con los datos del aula
     */
    @Override
    public String toString() {
        return "Aula{id=" + id + ", codigo=" + codigo + ", curso=" + curso
                + ", grupo=" + grupo + ", capacidad=" + capacidad
                + ", tutor=" + (tutor != null ? tutor.getCodigo() : "sin tutor") + "}";
    }
}

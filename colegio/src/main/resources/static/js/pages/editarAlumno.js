/**
 * Pagina de edicion de un alumno existente.
 *
 * <p>Carga los datos actuales del alumno, permite modificarlos
 * y los envia a {@link AlumnoAPI} para actualizarlos.</p>
 *
 * @module editarAlumno
 * @see {@link AlumnoAPI}
 * @see {@link AulaAPI}
 */

import { AlumnoAPI } from "../api/alumnoApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

// ============================================================
// VARIABLES
// ============================================================

/** @type {number} Ano actual usado para calcular el rango de fechas validas. */
const anioActual = new Date().getFullYear();

/** @type {string} Fecha minima de nacimiento permitida (14 anos atras). */
const fechaMin = `${anioActual - 14}-01-01`;

/** @type {string} Fecha maxima de nacimiento permitida (5 anos atras). */
const fechaMax = `${anioActual - 5}-12-31`;

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id || !REGEX.SOLO_NUMEROS.test(id)) {
        root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    } else {
        const respuesta = await AlumnoAPI.obtenerPorId(id);
        const alumno = respuesta.datos;

        if (!alumno) {
            root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
        } else {
            // Las opciones con "selected" dependen del alumno cargado.
            let opcionesCurso = "";
            for (const curso of CURSOS) {
                const seleccionado = alumno.aula.curso === curso ? "selected" : "";
                opcionesCurso += `<option value="${curso}" ${seleccionado}>${curso}</option>`;
            }

            let opcionesGrupo = "";
            for (const grupo of GRUPOS) {
                const seleccionado = alumno.aula.grupo === grupo ? "selected" : "";
                opcionesGrupo += `<option value="${grupo}" ${seleccionado}>Grupo ${grupo}</option>`;
            }

            root.innerHTML = `
            <div class="containerEditar">
                <h1>Editar Alumno</h1>
                <div class="card">
                    <div class="card-body">
                        <p><strong>Codigo:</strong> ${alumno.codigo}</p>
                        <label>Nombre</label>
                        <input type="text" id="inputNombre" value="${alumno.nombre}">
                        <label>Apellido</label>
                        <input type="text" id="inputApellido" value="${alumno.apellido}">
                        <label>Fecha de Nacimiento</label>
                        <input type="date" id="inputFecha" value="${alumno.fechaNacimiento}">
                        <label>Curso</label>
                        <select id="inputCurso">${opcionesCurso}</select>
                        <label>Grupo</label>
                        <select id="inputGrupo">${opcionesGrupo}</select>
                        <p id="mensaje"></p>
                        <button id="btnGuardar">Guardar</button>
                        <a href="menuAlumno.html">Cancelar</a>
                    </div>
                </div>
            </div>
        `;

            /** Boton guardar: valida los campos y envia los cambios a la API. */
            document.getElementById("btnGuardar").addEventListener("click", async function () {
                const curso = document.getElementById("inputCurso").value;
                const grupo = document.getElementById("inputGrupo").value;
                const mensaje = document.getElementById("mensaje");

                const nombre = document.getElementById("inputNombre").value.trim();
                const apellido = document.getElementById("inputApellido").value.trim();
                const fecha = document.getElementById("inputFecha").value;

                if (!nombre || !apellido || !fecha) {
                    mensaje.textContent = "Todos los campos son obligatorios";
                    mensaje.className = "mensaje--error";
                } else if (fecha < fechaMin || fecha > fechaMax) {
                    mensaje.textContent = "La fecha de nacimiento debe estar entre " + fechaMin + " y " + fechaMax;
                    mensaje.className = "mensaje--error";
                } else if (!REGEX.SOLO_LETRAS.test(nombre)) {
                    mensaje.textContent = "El nombre solo puede contener letras";
                    mensaje.className = "mensaje--error";
                } else if (!REGEX.SOLO_LETRAS.test(apellido)) {
                    mensaje.textContent = "El apellido solo puede contener letras";
                    mensaje.className = "mensaje--error";
                } else {
                    // El codigo del aula se forma concatenando curso y grupo, ej: "1º" + "A" = "1ºA"
                    const respuestaAula = await AulaAPI.obtenerPorCodigo(curso + grupo);

                    if (!respuestaAula.datos) {
                        mensaje.textContent = "Aula no encontrada";
                        mensaje.className = "mensaje--error";
                    } else {
                        alumno.nombre = nombre;
                        alumno.apellido = apellido;
                        alumno.fechaNacimiento = fecha;
                        // El backend solo necesita el ID del aula.
                        alumno.aula = {};
                        alumno.aula.id = respuestaAula.datos.id;

                        const resultado = await AlumnoAPI.actualizar(id, alumno);

                        if (resultado.datos) {
                            window.location.href = "menuAlumno.html";
                        } else {
                            mensaje.textContent = resultado.error.mensaje;
                            mensaje.className = "mensaje--error";
                        }
                    }
                }
            });
        }
    }
});
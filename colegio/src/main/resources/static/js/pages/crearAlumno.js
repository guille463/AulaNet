/**
 * Pagina de creacion de un nuevo alumno.
 *
 * <p>Recoge los datos del formulario, valida los campos y llama a
 * {@link AlumnoAPI} para persistir el registro.</p>
 *
 * @module crearAlumno
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
// INICIALIZACION DE OPCIONES
// ============================================================

/** @type {string} HTML de opciones para el selector de curso, con placeholder inicial. */
let opcionesCurso = `<option value="">-- Selecciona curso --</option>`;
for (const curso of CURSOS) {
  opcionesCurso += `<option value="${curso}">${curso}</option>`;
}

/** @type {string} HTML de opciones para el selector de grupo, con placeholder inicial. */
let opcionesGrupo = `<option value="">-- Selecciona grupo --</option>`;
for (const grupo of GRUPOS) {
  opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
}

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = añadirContenedorCrearAlumno();

  // ============================================================
  // SELECCION AULA PARA AÑADIR ALUMNO DESDE AULA
  // ============================================================
  const params = new URLSearchParams(window.location.search);
  const codigoAula = params.get("aula");
  if (codigoAula) {
    const curso = codigoAula.slice(0, -1);
    const grupo = codigoAula.slice(-1);
    document.getElementById("inputCurso").value = curso;
    document.getElementById("inputGrupo").value = grupo;
  }

  // ============================================================
  // LISTENER
  // ============================================================

  /** Boton crear: valida el formulario, resuelve el aula por codigo y envia el alumno a la API. */
  document
    .getElementById("btnCrear")
    .addEventListener("click", async function () {
      const nombre = document.getElementById("inputNombre").value.trim();
      const apellido = document.getElementById("inputApellido").value.trim();
      const fecha = document.getElementById("inputFecha").value;
      const curso = document.getElementById("inputCurso").value;
      const grupo = document.getElementById("inputGrupo").value;
      const mensaje = document.getElementById("mensaje");

      if (!nombre || !apellido || !fecha || !curso || !grupo) {
        mensaje.textContent = "Todos los campos son obligatorios";
        mensaje.className = "mensaje--error";
      } else if (fecha < fechaMin || fecha > fechaMax) {
        mensaje.textContent =
          "La fecha de nacimiento debe estar entre " +
          fechaMin +
          " y " +
          fechaMax;
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
          mensaje.textContent =
            "No se encontro el aula para el curso y grupo seleccionados";
          mensaje.className = "mensaje--error";
        } else {
          const alumno = {};
          alumno.nombre = nombre;
          alumno.apellido = apellido;
          alumno.fechaNacimiento = fecha;
          // El backend solo necesita el ID del aula.
          alumno.aula = {};
          alumno.aula.id = respuestaAula.datos.id;

          const resultado = await AlumnoAPI.crear(alumno);

          if (resultado.datos) {
            window.location.href = "menuAlumno.html";
          } else {
            mensaje.textContent = resultado.error.mensaje;
            mensaje.className = "mensaje--error";
          }
        }
      }
    });
});

// ============================================================
// FUNCIONES
// ============================================================

function añadirContenedorCrearAlumno() {
  return `
   <div class="containerCrear">
            <h1>Crear Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <label for="inputNombre">Nombre</label>
                    <input type="text" id="inputNombre" placeholder="Ej: Juan">
                    <label for="inputApellido">Apellido</label>
                    <input type="text" id="inputApellido" placeholder="Ej: Garcia Lopez">
                    <label for="inputFecha">Fecha de Nacimiento</label>
                    <input type="date" id="inputFecha" min="${fechaMin}" max="${fechaMax}">
                    <label for="inputCurso">Curso</label>
                    <select id="inputCurso">${opcionesCurso}</select>
                    <label for="inputGrupo">Grupo</label>
                    <select id="inputGrupo">${opcionesGrupo}</select>
                    <button id="btnCrear">Crear</button>
                    <a href="menuAlumno.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
  `;
}

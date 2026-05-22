/**
 * Pagina de edicion de un profesor existente.
 *
 * <p>Carga los datos actuales del profesor, permite modificarlos
 * y los envia a {@link ProfesorAPI} para actualizarlos.</p>
 *
 * @module editarProfesor
 * @see {@link ProfesorAPI}
 */

import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { ESPECIALIDADES, CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

// ============================================================
// INICIALIZACION DE OPCIONES
// ============================================================

/** @type {string} HTML de opciones para el selector de curso. */
let opcionesCurso = "";
for (const curso of CURSOS) {
  opcionesCurso += `<option value="${curso}">${curso}</option>`;
}

/** @type {string} HTML de opciones para el selector de grupo. */
let opcionesGrupo = "";
for (const grupo of GRUPOS) {
  opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
}

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id || !REGEX.SOLO_NUMEROS.test(id)) {
    root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
  } else {
    const respuestaProfesor = await ProfesorAPI.obtenerPorId(id);
    const profesor = respuestaProfesor.datos;

    if (!profesor) {
      root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
    } else {
      // Las opciones con "selected" dependen del profesor cargado.
      let opcionesEspecialidad = "";
      for (const especialidad of ESPECIALIDADES) {
        const seleccionado =
          profesor.especialidad === especialidad.valor ? "selected" : "";
        opcionesEspecialidad += `<option value="${especialidad.valor}" ${seleccionado}>${especialidad.etiqueta}</option>`;
      }

      root.innerHTML = añadirFormularioEditarProfesor(
        profesor,
        opcionesEspecialidad,
        opcionesCurso,
        opcionesGrupo,
      );

      /** Cambio de especialidad: muestra u oculta el selector de aula si es tutor GENERAL. */
      document
        .getElementById("inputEspecialidad")
        .addEventListener("change", function () {
          const selectAula = document.getElementById("selectAula");
          if (this.value === "GENERAL") {
            selectAula.style.display = "block";
          } else {
            selectAula.style.display = "none";
          }
        });

      // ============================================================
      // LISTENER
      // ============================================================

      /** Boton guardar: valida los campos y envia los cambios a la API. */
      document
        .getElementById("btnGuardar")
        .addEventListener("click", async function () {
          const mensaje = document.getElementById("mensaje");

          profesor.nombre = document.getElementById("inputNombre").value.trim();
          profesor.apellido = document
            .getElementById("inputApellido")
            .value.trim();
          profesor.email = document.getElementById("inputEmail").value.trim();
          profesor.especialidad =
            document.getElementById("inputEspecialidad").value;

          if (profesor.especialidad === "GENERAL") {
            const curso = document.getElementById("inputCurso").value;
            const grupo = document.getElementById("inputGrupo").value;
            // El codigo del aula se forma concatenando curso y grupo, ej: "1º" + "A" = "1ºA"
            profesor.codigoAula = curso + grupo;
          } else {
            profesor.codigoAula = null;
          }

          if (!profesor.nombre || !profesor.apellido || !profesor.email) {
            mensaje.textContent = "Todos los campos son obligatorios";
            mensaje.className = "mensaje--error";
          } else if (!REGEX.SOLO_LETRAS.test(profesor.nombre)) {
            mensaje.textContent = "El nombre solo puede contener letras";
            mensaje.className = "mensaje--error";
          } else if (!REGEX.SOLO_LETRAS.test(profesor.apellido)) {
            mensaje.textContent = "El apellido solo puede contener letras";
            mensaje.className = "mensaje--error";
          } else if (!REGEX.EMAIL.test(profesor.email)) {
            mensaje.textContent = "El formato del email no es valido";
            mensaje.className = "mensaje--error";
          } else {
            const resultadoActualizar = await ProfesorAPI.actualizar(
              id,
              profesor,
            );

            if (resultadoActualizar.datos) {
              window.location.href = "menuProfesor.html";
            } else {
              mensaje.textContent = resultadoActualizar.error.mensaje;
              mensaje.className = "mensaje--error";
            }
          }
        });
    }
  }
});

// ============================================================
// FUNCIONES
// ============================================================

function añadirFormularioEditarProfesor(
  profesor,
  opcionesEspecialidad,
  opcionesCurso,
  opcionesGrupo,
) {
  return `
    <div class="containerEditar">
      <h1>Editar Profesor</h1>
      <div class="card">
        <div class="card-body">
          <p><strong>Codigo:</strong> ${profesor.codigo}</p>
          <label>Nombre</label>
          <input type="text" id="inputNombre" value="${profesor.nombre}">
          <label>Apellido</label>
          <input type="text" id="inputApellido" value="${profesor.apellido}">
          <label>Email</label>
          <input type="text" id="inputEmail" value="${profesor.email}">
          <label>Especialidad</label>
          <select id="inputEspecialidad">${opcionesEspecialidad}</select>
          <div id="selectAula" style="display:${profesor.especialidad === "GENERAL" ? "block" : "none"};">
            <select id="inputCurso">${opcionesCurso}</select>
            <select id="inputGrupo">${opcionesGrupo}</select>
          </div>
          <p id="mensaje"></p>
          <button id="btnGuardar">Guardar</button>
          <a href="menuProfesor.html">Cancelar</a>
        </div>
      </div>
    </div>
  `;
}

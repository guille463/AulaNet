/**
 * Pagina de gestion del listado de asignaturas.
 *
 * <p>Muestra las asignaturas agrupadas por nombre y permite desplegar
 * los cursos de cada una para ver su detalle.</p>
 *
 * @module menuAsignatura
 * @see {@link AsignaturaAPI}
 * @see {@link crearTarjetaAsignatura}
 */

import { AsignaturaAPI } from "../api/asignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAsignatura } from "../components/asignaturaComponente.js";
import { CURSOS } from "../utils/constantes.js";

// ============================================================
// VARIABLES
// ============================================================

/** @type {Array} Todas las asignaturas cargadas desde la API. */
let todasAsignaturas = [];

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
        <div class="containerGestion">
            <h1>Gestionar Asignaturas</h1>
            <div id="listaAsignaturas"></div>
        </div>
    `;

  await cargarTodos();

  /** Gestiona clicks en nombre y curso. */
  document
    .getElementById("listaAsignaturas")
    .addEventListener("click", function (evento) {

      // Click en el nombre: muestra u oculta los cursos de esa asignatura.
      if (evento.target.classList.contains("btnNombreAsignatura")) {
        const nombreAsignatura = evento.target.getAttribute("data-nombre");
        const divCursos = document.getElementById("cursos-" + nombreAsignatura);
        const divTarjeta = document.getElementById("tarjeta-" + nombreAsignatura);

        if (divCursos.style.display === "none") {
          divCursos.style.display = "flex";
        } else {
          divCursos.style.display = "none";
          divTarjeta.innerHTML = "";
        }
      }

      // Busca la asignatura y muestra su tarjeta.
      if (evento.target.classList.contains("btnCurso")) {
        const nombreAsignatura = evento.target.getAttribute("data-nombre");
        const cursoSeleccionado = evento.target.getAttribute("data-curso");
        const divTarjeta = document.getElementById("tarjeta-" + nombreAsignatura);

        let asignaturaEncontrada = null;

        for (const asignatura of todasAsignaturas) {
          if (
            asignatura.nombre === nombreAsignatura &&
            asignatura.curso === cursoSeleccionado
          ) {
            asignaturaEncontrada = asignatura;
          }
        }

        if (asignaturaEncontrada) {
          divTarjeta.innerHTML = crearTarjetaAsignatura(asignaturaEncontrada);
        } else {
          divTarjeta.innerHTML = `<p class="mensaje--error">Asignatura no encontrada</p>`;
        }
      }
    });
});

// ============================================================
// FUNCIONES
// ============================================================

/**
 * Carga todas las asignaturas y construye la lista agrupada por nombre.
 *
 * @returns {Promise<void>}
 */
async function cargarTodos() {
  const listaAsignaturas = document.getElementById("listaAsignaturas");
  const respuesta = await AsignaturaAPI.obtenerTodos();

  if (respuesta.datos && respuesta.datos.length > 0) {
    todasAsignaturas = respuesta.datos;

    // Se extraen los nombres unicos para evitar repetir el mismo nombre por cada curso.
    const nombresUnicos = [];
    for (const asignatura of todasAsignaturas) {
      if (!nombresUnicos.includes(asignatura.nombre)) {
        nombresUnicos.push(asignatura.nombre);
      }
    }

    let html = "";

    for (const nombreAsignatura of nombresUnicos) {
      let cursosHtml = "";
      for (const curso of CURSOS) {
        cursosHtml += `<button class="btnCurso" data-nombre="${nombreAsignatura}" data-curso="${curso}">${curso}</button>`;
      }
      html += `
                <div class="asignaturaItem">
                    <button class="btnNombreAsignatura" data-nombre="${nombreAsignatura}">${nombreAsignatura}</button>
                    <div id="cursos-${nombreAsignatura}" style="display:none;">
                        ${cursosHtml}
                    </div>
                    <div id="tarjeta-${nombreAsignatura}"></div>
                </div>
            `;
    }

    listaAsignaturas.innerHTML = html;
  } else {
    listaAsignaturas.innerHTML = `<p class="mensaje--error">No hay asignaturas</p>`;
  }
}
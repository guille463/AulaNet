/**
 * Pagina de gestion del listado de aulas.
 *
 * <p>Muestra las aulas agrupadas por curso y permite desplegar
 * los grupos de cada uno para ver el detalle del aula.</p>
 *
 * @module menuAula
 * @see {@link AulaAPI}
 * @see {@link crearTarjetaAula}
 */

import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAula } from "../components/aulaComponente.js";
import { CURSOS, GRUPOS } from "../utils/constantes.js";

// ============================================================
// VARIABLES
// ============================================================

/** @type {Array} Todas las aulas cargadas desde la API. */
let todasAulas = [];

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
      <div class="containerGestion">
        <h1>Gestionar Aulas</h1>
        <div id="listaAulas"></div>
    </div>
    `;

  await cargarTodos();

  /** gestiona clicks en curso y grupo. */
  document
    .getElementById("listaAulas")
    .addEventListener("click", async function (evento) {

      // Click en un curso: muestra u oculta sus grupos.
      if (evento.target.classList.contains("btnCurso")) {
        const cursoSeleccionado = evento.target.getAttribute("data-curso");
        const divGrupos = document.getElementById("grupos-" + cursoSeleccionado);
        const divTarjeta = document.getElementById("tarjeta-" + cursoSeleccionado);

        if (divGrupos.style.display === "none") {
          divGrupos.style.display = "flex";
        } else {
          divGrupos.style.display = "none";
          divTarjeta.innerHTML = "";
        }
      }

      // Click en un grupo: busca el aula y muestra su tarjeta.
      if (evento.target.classList.contains("btnGrupo")) {
        const cursoSeleccionado = evento.target.getAttribute("data-curso");
        const grupoSeleccionado = evento.target.getAttribute("data-grupo");
        const divTarjeta = document.getElementById("tarjeta-" + cursoSeleccionado);

        let aulaEncontrada = null;

        for (const aula of todasAulas) {
          if (
            aula.curso === cursoSeleccionado &&
            aula.grupo === grupoSeleccionado
          ) {
            aulaEncontrada = aula;
          }
        }

        if (aulaEncontrada) {
          divTarjeta.innerHTML = await crearTarjetaAula(aulaEncontrada);
        } else {
          divTarjeta.innerHTML = `<p class="mensaje--error">Aula no encontrada</p>`;
        }
      }
    });
});

// ============================================================
// FUNCIONES
// ============================================================

/**
 * Carga todas las aulas y construye la lista agrupada por curso.
 *
 * @returns {Promise<void>}
 */
async function cargarTodos() {
  const listaAulas = document.getElementById("listaAulas");
  const respuesta = await AulaAPI.obtenerTodos();

  if (respuesta.datos && respuesta.datos.length > 0) {
    todasAulas = respuesta.datos;
    let html = "";

    for (const curso of CURSOS) {
      let gruposHtml = "";
      for (const grupo of GRUPOS) {
        gruposHtml += `<button class="btnGrupo" data-curso="${curso}" data-grupo="${grupo}">${grupo}</button>`;
      }
      html += `
                <div class="aulaItem">
                    <button class="btnCurso" data-curso="${curso}">${curso}</button>
                    <div id="grupos-${curso}" style="display:none;">
                        ${gruposHtml}
                    </div>
                    <div id="tarjeta-${curso}"></div>
                </div>
            `;
    }

    listaAulas.innerHTML = html;
  } else {
    listaAulas.innerHTML = `<p class="mensaje--error">No hay aulas</p>`;
  }
}

/**
 * Busca un aula por su ID y muestra el resultado.
 * 
 * @param {string} id - ID numerico del aula.
 * @returns {Promise<void>}
 * @todo Funcion declarada pero no utilizada en el flujo actual.
 */
async function buscarPorId(id) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (id) {
    const respuesta = await AulaAPI.obtenerPorId(id);
    if (respuesta.datos) {
      resultado.innerHTML = await crearTarjetaAula(respuesta.datos);
    } else {
      resultado.innerHTML = `<p class="mensaje--error">Aula con id: ${id} no encontrada</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un ID</p>`;
  }
}

/**
 * Busca un aula por su codigo y muestra el resultado.
 *
 * @param {string} codigoAula - Codigo del aula, ej {@code "1ºA"}.
 * @returns {Promise<void>}
 * @todo Funcion declarada pero no utilizada en el flujo actual.
 */
async function buscarPorCodigo(codigoAula) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  const respuesta = await AulaAPI.obtenerPorCodigo(codigoAula);
  if (respuesta.datos) {
    resultado.innerHTML = await crearTarjetaAula(respuesta.datos);
  } else {
    resultado.innerHTML = `<p class="mensaje--error">Aula con codigo: ${codigoAula} no encontrada</p>`;
  }
}
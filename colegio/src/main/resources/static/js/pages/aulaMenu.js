import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAula } from "../components/aulaComponente.js";
import { CURSOS, GRUPOS } from "../utils/constantes.js";

let todasAulas = [];

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

  document
    .getElementById("listaAulas")
    .addEventListener("click", async function (evento) {
      if (evento.target.classList.contains("btnCurso")) {
        const cursoSeleccionado = evento.target.getAttribute("data-curso");
        const divGrupos = document.getElementById(
          "grupos-" + cursoSeleccionado,
        );
        const divTarjeta = document.getElementById(
          "tarjeta-" + cursoSeleccionado,
        );

        if (divGrupos.style.display === "none") {
          divGrupos.style.display = "flex";
        } else {
          divGrupos.style.display = "none";
          divTarjeta.innerHTML = "";
        }
      }

      if (evento.target.classList.contains("btnGrupo")) {
        const cursoSeleccionado = evento.target.getAttribute("data-curso");
        const grupoSeleccionado = evento.target.getAttribute("data-grupo");
        const divTarjeta = document.getElementById(
          "tarjeta-" + cursoSeleccionado,
        );

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
          divTarjeta.innerHTML = `<p class="error">Aula no encontrada</p>`;
        }
      }
    });
});

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
    listaAulas.innerHTML = `<p class="error">No hay aulas</p>`;
  }
}

async function buscarPorId(id) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (id) {
    const respuesta = await AulaAPI.obtenerPorId(id);
    if (respuesta.datos) {
      resultado.innerHTML = await crearTarjetaAula(respuesta.datos);
    } else {
      resultado.innerHTML = `<p class="error">Aula con id: ${id} no encontrada</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un ID</p>`;
  }
}

async function buscarPorCodigo(codigoAula) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  const respuesta = await AulaAPI.obtenerPorCodigo(codigoAula);
  if (respuesta.datos) {
    resultado.innerHTML = await crearTarjetaAula(respuesta.datos);
  } else {
    resultado.innerHTML = `<p class="error">Aula con codigo: ${codigoAula} no encontrada</p>`;
  }
}

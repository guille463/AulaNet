import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAula } from "../components/aulaComponente.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
      <div class="containerBuscarAula">
        <h1>Gestionar Aulas</h1>
        <div class="card">
          <div class="card-body">
            <h2 class="card-title">Buscar Aula</h2>
            <select id="tipoBusqueda" class="form-select mb-2">
              <option value="id">Por ID</option>
              <option value="curso">Por Curso y Grupo</option>
            </select>
            <div id="inputContainer">
              <input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">
            </div>
            <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
          </div>
        </div>
        <div id="resultado"></div>
        <hr>
        <h2>Lista de aulas</h2>
        <div id="listaAulas"></div>
      </div>
    `;


  cargarTodos();

  document.getElementById("btnBuscar").addEventListener("click", function () {
    const tipo = document.getElementById("tipoBusqueda").value;
    if (tipo === "id") {
      const valor = document.getElementById("inputBusqueda").value.trim();
      buscarPorId(valor);
    }
  });
});



async function cargarTodos() {
  const listaAulas = document.getElementById("listaAulas");
  listaAulas.innerHTML = "";
  const resultado = await AulaAPI.obtenerTodos();

  if (resultado.datos && resultado.datos.length > 0) {
    let html = "";
    for (const aula of resultado.datos) {
      html += await crearTarjetaAula(aula);
    }

    listaAulas.innerHTML = html;
  } else {
    listaAulas.innerHTML = `<p class="error">No hay alumnos</p>`;
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
      resultado.innerHTML = `<p class="error">Aula no encontrada</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un ID</p>`;
  }
}




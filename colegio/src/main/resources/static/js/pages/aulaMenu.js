import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAula } from "../components/aulaComponente.js";
import { CURSOS, GRUPOS } from "../utils/constantes.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    let opcionesCurso = "";
    for (const curso of CURSOS) {
        opcionesCurso += `<option value="${curso}">${curso}</option>`;
    }

    let opcionesGrupo = "";
    for (const grupo of GRUPOS) {
        opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
    }

    root.innerHTML = `
        <div class="containerBuscarAula">
            <h1>Gestionar Aulas</h1>
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Buscar Aula</h2>
                    <select id="tipoBusqueda" class="form-select mb-2">
                        <option value="id">Por ID</option>
                        <option value="codigo">Por Curso y Grupo</option>
                    </select>
                    <div id="inputContainer">
                        <input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">
                    </div>
                    <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
                </div>
            </div>
            <div id="resultado"></div>
            <hr>
            <h2>Lista de Aulas</h2>
            <div id="listaAulas"></div>
        </div>
    `;

    cargarTodos();

    document.getElementById("tipoBusqueda").addEventListener("change", function () {
        const container = document.getElementById("inputContainer");
        const tipo = this.value;
        if (tipo === "id") {
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">`;
        } else if (tipo === "codigo") {
            container.innerHTML = `
                <select id="inputCurso" class="form-select mb-2">${opcionesCurso}</select>
                <select id="inputGrupo" class="form-select mb-2">${opcionesGrupo}</select>`;
        }
    });

    document.getElementById("btnBuscar").addEventListener("click", function () {
        const tipo = document.getElementById("tipoBusqueda").value;
        if (tipo === "id") {
            const valor = document.getElementById("inputBusqueda").value.trim();
            buscarPorId(valor);
        } else if (tipo === "codigo") {
            const curso = document.getElementById("inputCurso").value;
            const grupo = document.getElementById("inputGrupo").value;
            buscarPorCodigo(curso + grupo);
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
            resultado.innerHTML = `<p class="error">Aula no encontrada</p>`;
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
        resultado.innerHTML = `<p class="error">Aula no encontrada</p>`;
    }
}
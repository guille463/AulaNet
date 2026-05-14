import { AsignaturaAPI } from "../api/asignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAsignatura } from "../components/asignaturaComponente.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
        <div class="containerBuscarAsignatura">
            <h1>Gestionar Asignaturas</h1>
            <div id="resultado"></div>
            <hr>
            <h2>Lista de Asignaturas</h2>
            <div id="listaAsignaturas"></div>
        </div>
    `;

    cargarTodos();
});

async function cargarTodos() {
    const listaAsignaturas = document.getElementById("listaAsignaturas");
    listaAsignaturas.innerHTML = "";
    const respuesta = await AsignaturaAPI.obtenerTodos();
    if (respuesta.datos && respuesta.datos.length > 0) {
        let html = "";
        respuesta.datos.forEach(function (asignatura) {
            html += crearTarjetaAsignatura(asignatura);
        });
        listaAsignaturas.innerHTML = html;
    } else {
        listaAsignaturas.innerHTML = `<p class="error">No hay asignaturas</p>`;
    }
}
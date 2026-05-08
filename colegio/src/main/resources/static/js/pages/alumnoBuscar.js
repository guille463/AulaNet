import { getAlumnoPorId } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
        <div class="containerBuscarAlumno">
            <h1>Buscar Alumno</h1>

            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Buscar por ID</h2>
                    <input type="number" id="inputId" class="form-control" placeholder="Introduce el ID">
                    <button class="btn btn-BuscarPorId" id="btnBuscarId">Buscar</button>
                </div>
            </div>

            <div id="resultado"></div>
        </div>
    `;

    document.getElementById("btnBuscarId").addEventListener("click", function() {
        buscarPorId(document.getElementById("inputId").value.trim());
    });
});

async function buscarPorId(id) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";

    if (id) {
        const alumno = await getAlumnoPorId(id);
        if (alumno) {
            resultado.innerHTML = crearTarjetaAlumno(alumno);
        } else {
            resultado.innerHTML = `<p class="error">Alumno no encontrado</p>`;
        }
    } else {
        resultado.innerHTML = `<p class="error">Introduce un ID</p>`;
    }
}
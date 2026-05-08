import { getAlumnos } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const alumnos = await getAlumnos();
    console.log(alumnos);

    let listaAlumnos = "";
    for (const alumno of alumnos) {
        listaAlumnos += `
            <li class="list-group-item">
                <span class="fw-bold">${alumno.codigo}</span>
                <span> ${alumno.nombre} ${alumno.apellido}</span>
                <span class="text-muted"> - ${alumno.curso}</span>
            </li>
        `;
    }

    root.innerHTML = `
        <div class="container mt-4">
            <h1>Alumnos</h1>
            <ul class="list-group mt-3">${listaAlumnos}</ul>
        </div>
    `;
});
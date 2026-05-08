import {crearBarraNavegacion} from "../components/navbar.js"

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();

    document.getElementById("root").innerHTML = `
        <div class="containerMenuAlumnos">
            <h1 class="MenuAlumnos">Gestion de Alumnos</h1>
                <a href="alumnoLista.html" class="btn btn-Lista">Ver todos los alumnos</a>
            </div>
    `;
});
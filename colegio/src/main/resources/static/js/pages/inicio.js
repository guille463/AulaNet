import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () =>{
    document.getElementById("navbar").innerHTML = crearBarraNavegacion(); 
    const root = document.getElementById("root"); 
    root.innerHTML = `
        <div class="containerAlumnos">
            <h1>Sistema de Gestion del Colegio</h1>
            <p class="lead">Bienvenido al sistema de gestion.</p>
            <a href="alumnoMenu.html" class="btn btn-Lista">Gestionar Alumnos</a>
        </div>
    `;
})
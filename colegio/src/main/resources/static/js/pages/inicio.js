import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () =>{
    document.getElementById("navbar").innerHTML = crearBarraNavegacion(); 
    const root = document.getElementById("root"); 
    root.innerHTML = `
        <div class="containerAlumnos">
            <h1>Gestion colegio</h1>
            <a href="alumnoMenu.html" class="btn btn-Lista">Gestionar Alumnos</a>
        </div>
    `;
})
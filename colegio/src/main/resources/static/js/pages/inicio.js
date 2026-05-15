import { crearBarraNavegacion } from "../components/navbar.js";
document.addEventListener("DOMContentLoaded", async () =>{
    document.getElementById("navbar").innerHTML = crearBarraNavegacion(); 
    const root = document.getElementById("root"); 
    root.innerHTML = `
        <div class="containerInicio">
            <h1>Gestion colegio</h1>
        </div>
    `;
})
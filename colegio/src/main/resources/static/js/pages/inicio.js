import { crearBarraNavegacion } from "../components/navbar.js";
document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
});
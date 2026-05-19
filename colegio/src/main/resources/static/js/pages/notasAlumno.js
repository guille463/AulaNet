import { crearBarraNavegacion } from "../components/navbarNotas.js";
import { generarNotasAlumno } from "../pages/detalleAlumno.js";

document.addEventListener("DOMContentLoaded", async () => {

  document.getElementById("navbar").innerHTML =
    crearBarraNavegacion();

  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);

  const id = params.get("id");

  root.innerHTML = await generarNotasAlumno(id);
});
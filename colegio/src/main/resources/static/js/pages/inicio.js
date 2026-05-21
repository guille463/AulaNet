/**
 * Pagina de inicio de la aplicacion.
 *
 * <p>Inicializa la barra de navegacion.</p>
 *
 * @module inicio
 * @see {@link crearBarraNavegacion}
 */

import { crearBarraNavegacion } from "../components/navbar.js";

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
});

/**
 * Pagina de detalle de un profesor.
 *
 * <p>Muestra los datos del profesor y las asignaturas que imparte.</p>
 *
 * @module detalleProfesor
 * @see {@link ProfesorAPI}
 * @see {@link ProfesorAsignaturaAPI}
 */

import { ProfesorAPI } from "../api/profesorApi.js";
import { ProfesorAsignaturaAPI } from "../api/profesorAsignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) {
    root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
  } else {
    const respuestaProfesor = await ProfesorAPI.obtenerPorId(id);
    const profesor = respuestaProfesor.datos;

    if (!profesor) {
      root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
    } else {
      const respuestaAsignaturas =
        await ProfesorAsignaturaAPI.obtenerPorProfesor(id);
      const asignaturas = respuestaAsignaturas.datos;

      // Se genera una fila por cada asignatura que imparte el profesor.
      let asignaturasHtml = "";
      if (asignaturas && asignaturas.length > 0) {
        for (const pa of asignaturas) {
          asignaturasHtml += `
                        <tr>
                           <td><a href="detalleAsignatura.html?id=${pa.asignatura.id}">${pa.asignatura.nombre}</a></td>
                            <td>${pa.asignatura.curso}</td>
                            <td>${pa.horasSemanales}</td>
                        </tr>
                    `;
        }
      } else {
        asignaturasHtml = `<tr><td colspan="3">Sin asignaturas</td></tr>`;
      }

      root.innerHTML = `
  <div class="containerDetalle">
    <h1>Detalle del Profesor</h1>
    <div class="card--detalle">
      ${añadirCardProfesor(profesor)}
    </div>
    <h2>Asignaturas que imparte</h2>
    ${añadirTablaAsignaturasProfesor(asignaturasHtml)}
    <a href="menuProfesor.html">Volver</a>
  </div>
`;
    }
  }
});

// ============================================================
// FUNCIONES
// ============================================================

function añadirCardProfesor(profesor) {
  return `
    <div class="card--detalle--body">
      <h2>${profesor.codigo}</h2>
      <hr>
      <p><strong>Nombre:</strong> ${profesor.nombre} ${profesor.apellido}</p>
      <p><strong>Email:</strong> ${profesor.email}</p>
      <p><strong>Especialidad:</strong> ${profesor.especialidad}</p>
    </div>
  `;
}

function añadirTablaAsignaturasProfesor(asignaturasHtml) {
  return `
    <table>
      <thead>
        <tr>
          <th>Asignatura</th>
          <th>Curso</th>
          <th>Horas semanales</th>
        </tr>
      </thead>
      <tbody>${asignaturasHtml}</tbody>
    </table>
  `;
}

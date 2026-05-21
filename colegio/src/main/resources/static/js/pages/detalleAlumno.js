/**
 * Pagina de detalle de un alumno.
 *
 * <p>Muestra los datos del alumno, sus asignaturas matriculadas
 * y permite editar las notas de cada una.</p>
 *
 * @module detalleAlumno
 * @see {@link AlumnoAPI}
 * @see {@link AlumnoAsignaturaAPI}
 */

import { AlumnoAPI } from "../api/alumnoApi.js";
import { AlumnoAsignaturaAPI } from "../api/alumnoAsignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  const respuesta = await AlumnoAPI.obtenerPorId(id);
  const alumno = respuesta.datos;

  if (!alumno) {
    root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
  } else {
    const aula = alumno.aula;

    const respuestaMatriculas = await AlumnoAsignaturaAPI.obtenerPorAlumno(id);
    const matriculas = respuestaMatriculas.datos;

    // Se genera una fila por cada matricula con un input de nota editable
    let asignaturasHtml = "";
    if (matriculas && matriculas.length > 0) {
      for (const matricula of matriculas) {
        asignaturasHtml += `
         ${crearTablaNotas(matricula)}
        `;
      }
    }

    root.innerHTML = `
      <div class="containerDetalle">
          <h1>Detalle del Alumno</h1>
          <div class="card--detalle">
${añadirDetalleAlumno(alumno, aula)}
          </div>
          <h2 id="Tab.Asig">Asignaturas</h2>
         ${añadirDetalleAlumnoNotas(asignaturasHtml)}
          <button class="imprimir" id="btnImprimir">Imprimir boletin</button>
          <p id="mensaje"></p>
          <a href="menuAlumno.html" class="volver">Volver</a>
      </div>
    `;

    /** Delegacion de eventos: detecta el boton guardar nota de cada fila y actualiza via API. */
    document.getElementById("root").addEventListener("click", async function (evento) {
      if (evento.target.classList.contains("btnEditarNota")) {
        const matriculaId = evento.target.getAttribute("data-id");
        const nota = document.getElementById("nota-" + matriculaId).value;
        const resultado = await AlumnoAsignaturaAPI.actualizarNota(matriculaId, nota);
        if (resultado.datos) {
          document.getElementById("notaActual-" + matriculaId).textContent = nota;
          document.getElementById("mensaje").textContent = "Nota actualizada correctamente";
          document.getElementById("mensaje").className = "mensaje--exito";
        } else {
          document.getElementById("mensaje").textContent = "Error al actualizar la nota";
          document.getElementById("mensaje").className = "mensaje--error";
        }
      }
    });

    /** Boton imprimir: lanza el dialogo de impresion del navegador. */
    document.querySelector(".imprimir").addEventListener("click", function () {
      window.print();
    });
  }
});

// ============================================================
// FUNCIONES
// ============================================================

function añadirDetalleAlumno(alumno, aula) {
  return `
  <div class="card--detalle--body">
                  <h2>${alumno.codigo}</h2>
                  <hr>
                  <p><strong>Nombre:</strong> ${alumno.nombre} ${alumno.apellido}</p>
                  <p><strong>Fecha de Nacimiento:</strong> ${alumno.fechaNacimiento}</p>
                  <p><strong>Curso:</strong> ${aula.curso}</p>
                  <p><strong>Grupo:</strong> ${aula.grupo}</p>
                 <p><strong>Aula:</strong> <a href="detalleAula.html?id=${aula.id}">${aula.codigo}</a></p>
                  <p><strong>Tutor:</strong> ${aula.tutor ? aula.tutor.nombre + " " + aula.tutor.apellido : "Sin tutor"}</p>
              </div>
  `
}

function añadirDetalleAlumnoNotas(asignaturasHtml) {
  return `
   <table>
              <thead>
                  <tr>
                      <th>Asignatura</th>
                      <th>Nota actual</th>
                      <th class="editarNota">Editar nota</th>
                  </tr>
              </thead>
              <tbody>${asignaturasHtml}</tbody>
          </table>
  
  `
}

function crearTablaNotas(matricula) {
  return `
   <tr>
            <td><a href="detalleAsignatura.html?id=${matricula.asignatura.id}">${matricula.asignatura.nombre}</a></td>
              <td id="notaActual-${matricula.id}">${matricula.nota}</td>
              <td class="cambiarNotas">
                  <input type="number" id="nota-${matricula.id}" value="${matricula.nota}" min="0" max="10" step="0.1">
                  <button class="btnEditarNota" data-id="${matricula.id}">Guardar</button>
              </td>
          </tr>
  
  `
}
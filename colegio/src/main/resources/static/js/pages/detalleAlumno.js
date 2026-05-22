/**
 * Pagina de detalle de un alumno.
 *
 * <p>Muestra los datos del alumno, sus asignaturas matriculadas,
 * permite editar las notas y gestionar las matriculas mediante checkboxes.</p>
 *
 * @module detalleAlumno
 * @see {@link AlumnoAPI}
 * @see {@link AlumnoAsignaturaAPI}
 * @see {@link AsignaturaAPI}
 */

import { AlumnoAPI } from "../api/alumnoApi.js";
import { AlumnoAsignaturaAPI } from "../api/alumnoAsignaturaApi.js";
import { AsignaturaAPI } from "../api/asignaturaApi.js";
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
        asignaturasHtml += crearTablaNotas(matricula);
      }
    }

    // Se genera un checkbox por cada asignatura del curso del alumno
    const respuestaAsignaturas = await AsignaturaAPI.obtenerPorCurso(
      aula.curso,
    );
    const asignaturas = respuestaAsignaturas.datos;
    // Conjunto de IDs de asignaturas ya matriculadas para marcar los checkboxes
    const idsMatriculados = new Set();
    if (matriculas && matriculas.length > 0) {
      for (const matricula of matriculas) {
        idsMatriculados.add(matricula.asignatura.id);
      }
    }

    let checkboxesHtml = "";
    if (asignaturas && asignaturas.length > 0) {
      for (const asignatura of asignaturas) {
        const marcado = idsMatriculados.has(asignatura.id) ? "checked" : "";
        checkboxesHtml += `
          <label>
            <input type="checkbox" class="checkAsignatura" data-id="${asignatura.id}" ${marcado}>
            ${asignatura.nombre}
          </label>
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
    <p id="mensaje"></p>
    <div class="no-imprimir">
      <h2>Gestionar matriculas</h2>
      ${añadirCheckboxesMatriculas(checkboxesHtml)}
    </div>
    <button class="imprimir" id="btnImprimir">Imprimir boletin</button>
    <a href="menuAlumno.html" class="volver">Volver</a>
  </div>
    `;

    // ============================================================
    // LISTENERS
    // ============================================================

    /** Delegacion de eventos: detecta el boton guardar nota de cada fila y actualiza via API. */
    document
      .getElementById("root")
      .addEventListener("click", async function (evento) {
        if (evento.target.classList.contains("btnEditarNota")) {
          const matriculaId = evento.target.getAttribute("data-id");
          const nota = document.getElementById("nota-" + matriculaId).value;
          const resultado = await AlumnoAsignaturaAPI.actualizarNota(
            matriculaId,
            nota,
          );
          if (resultado.datos) {
            document.getElementById("notaActual-" + matriculaId).textContent =
              nota;
            document.getElementById("mensaje").textContent =
              "Nota actualizada correctamente";
            document.getElementById("mensaje").className = "mensaje--exito";
          } else {
            document.getElementById("mensaje").textContent =
              "Error al actualizar la nota";
            document.getElementById("mensaje").className = "mensaje--error";
          }
        }
      });

    /** Boton guardar matriculas: procesa los checkboxes y crea o elimina matriculas. */
    document
      .getElementById("btnGuardarMatriculas")
      .addEventListener("click", async function () {
        const checkboxes = document.querySelectorAll(".checkAsignatura");

        for (const checkbox of checkboxes) {
          const asignaturaId = Number(checkbox.getAttribute("data-id"));
          const estaMarcado = checkbox.checked;
          const estabaMatriculado = idsMatriculados.has(asignaturaId);

          if (estaMarcado && !estabaMatriculado) {
            const matricula = {};
            matricula.alumno = {};
            matricula.alumno.id = Number(id);
            matricula.asignatura = {};
            matricula.asignatura.id = asignaturaId;
            matricula.nota = 0;
            await AlumnoAsignaturaAPI.crear(matricula);
          } else if (!estaMarcado && estabaMatriculado) {
            const matricula = matriculas.find(
              (matricula) => matricula.asignatura.id === asignaturaId,
            );
            if (matricula) {
              await AlumnoAsignaturaAPI.eliminar(matricula.id);
            }
          }
        }

        window.location.href = "detalleAlumno.html?id=" + id;
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
  `;
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
  `;
}

function añadirCheckboxesMatriculas(checkboxesHtml) {
  return `
    <div class="card">
      <div class="card-body">
        ${checkboxesHtml}
        <button id="btnGuardarMatriculas">Guardar cambios</button>
      </div>
    </div>
  `;
}

function crearTablaNotas(matricula) {
  return `
      <tr>
      <td><a href="detalleAsignatura.html?id=${matricula.asignatura.id}">${matricula.asignatura.nombre}</a></td>
      <td id="notaActual-${matricula.id}">${matricula.nota > 0 ? matricula.nota : "--"}</td>
      <td class="cambiarNotas">
        <input type="number" id="nota-${matricula.id}" value="${matricula.nota}" min="0" max="10" step="0.1">
        <button class="btnEditarNota" data-id="${matricula.id}">Guardar</button>
      </td>
    </tr>
  `;
}

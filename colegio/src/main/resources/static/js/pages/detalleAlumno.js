import { AlumnoAPI } from "../api/alumnoApi.js";
import { AlumnoAsignaturaAPI } from "../api/alumnoAsignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

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

    let asignaturasHtml = "";
    if (matriculas && matriculas.length > 0) {
      for (const matricula of matriculas) {
        asignaturasHtml += `
          <tr>
            <td><a href="detalleAsignatura.html?id=${matricula.asignatura.id}">${matricula.asignatura.nombre}</a></td>
              <td id="notaActual-${matricula.id}">${matricula.nota}</td>
              <td>
                  <input type="number" id="nota-${matricula.id}" value="${matricula.nota}" min="0" max="10" step="0.1">
                  <button class="btnEditarNota" data-id="${matricula.id}">Guardar</button>
              </td>
          </tr>
        `;
      }
    }

    root.innerHTML = `
      <div class="containerDetalle">
          <h1>Detalle del Alumno</h1>
          <div class="--detalle">
              <div class="card--detalle--body">
                  <h2>${alumno.codigo}</h2>
                  <hr>
                  <p><strong>Nombre:</strong> ${alumno.nombre} ${alumno.apellido}</p>
                  <p><strong>Email:</strong> ${alumno.email}</p>
                  <p><strong>Curso:</strong> ${aula.curso}</p>
                  <p><strong>Grupo:</strong> ${aula.grupo}</p>
                 <p><strong>Aula:</strong> <a href="detalleAula.html?id=${aula.id}">${aula.codigo}</a></p>
                  <p><strong>Tutor:</strong> ${aula.tutor ? aula.tutor.nombre + " " + aula.tutor.apellido : "Sin tutor"}</p>
              </div>
          </div>
          <h2 id="Tab.Asig">Asignaturas</h2>
          <table>
              <thead>
                  <tr>
                      <th>Asignatura</th>
                      <th>Nota actual</th>
                      <th>Editar nota</th>
                  </tr>
              </thead>
              <tbody>${asignaturasHtml}</tbody>
          </table>
          <p id="mensaje"></p>
          <a href="alumnoMenu.html">Volver</a>
      </div>
    `;

    document.getElementById("root").addEventListener("click", async function (evento) {
      if (evento.target.classList.contains("btnEditarNota")) {
        const matriculaId = evento.target.getAttribute("data-id");
        const nota = document.getElementById("nota-" + matriculaId).value;
        const resultado = await AlumnoAsignaturaAPI.actualizarNota(matriculaId, nota);
        if (resultado.datos) {
          document.getElementById("notaActual-" + matriculaId).textContent = nota;
          document.getElementById("mensaje").textContent = "Nota actualizada correctamente";
        } else {
          document.getElementById("mensaje").textContent = "Error al actualizar la nota";
        }
      }
    });
  }
});
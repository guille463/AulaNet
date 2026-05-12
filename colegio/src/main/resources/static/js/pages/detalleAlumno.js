import { getAlumnoPorId } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import {
  getAlumnoAsignaturasPorAlumno,
  putNotaAlumno,
} from "../api/alumnoAsignaturaApi.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) {
    root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    return;
  }

  const alumno = await getAlumnoPorId(id);

  if (!alumno) {
    root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    return;
  }

  const aula = alumno.aula;
  const tutor = aula.tutor;

  let tutorTexto;
  if (tutor) {
    tutorTexto = tutor.nombre + " " + tutor.apellido;
  } else {
    tutorTexto = "Sin tutor";
  }

  const matriculas = await getAlumnoAsignaturasPorAlumno(id);
  let asignaturasHtml = "";
  if (matriculas && matriculas.length > 0) {
    matriculas.array.forEach(function (matricula) {
      asignaturasHtml += `
                <tr>
                    <td>${matricula.asignatura.nombre}</td>
                    <td>${matricula.nota}</td>
                    <td>
                        <input type="number" id="nota-${matricula.id}" value="${matricula.nota}" min="0" max="10" step="0.1">
                        <button class="btnEditarNota" data-id="${matricula.id}">Guardar</button>
                    </td>
                </tr>
            `;
    });
  }

  root.innerHTML = `
         <div class="containerDetalle">
            <h1>Detalle del Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <h2>${alumno.codigo}</h2>
                    <hr>
                    <p><strong>Nombre:</strong> ${alumno.nombre} ${alumno.apellido}</p>
                    <p><strong>Email:</strong> ${alumno.email}</p>
                    <p><strong>Curso:</strong> ${aula.curso}</p>
                    <p><strong>Grupo:</strong> ${aula.grupo}</p>
                    <p><strong>Aula:</strong> ${aula.codigo}</p>
                    <p><strong>Tutor:</strong> ${tutorTexto}</p>
                </div>
            </div>
            <h2>Asignaturas</h2>
            <table>
                <thead>
                    <tr>
                        <th>Asignatura</th>
                        <th>Nota actual</th>
                        <th>Editar nota</th>
                    </tr>
                </thead>
                <tbody>
                    ${asignaturasHtml}
                </tbody>
            </table>
            <p id="mensaje"></p>
            <a href="alumnoMenu.html">Volver</a>
        </div>
    `;

     document.getElementById("root").addEventListener("click", async function (e) {
        if (e.target.classList.contains("btnEditarNota")) {
            const matriculaId = e.target.getAttribute("data-id");
            const nota = document.getElementById("nota-" + matriculaId).value;
            const resultado = await putNotaAlumno(matriculaId, nota);
            if (resultado) {
                document.getElementById("mensaje").textContent = "Nota actualizada correctamente";
            } else {
                document.getElementById("mensaje").textContent = "Error al actualizar la nota";
            }
        }
    });
});

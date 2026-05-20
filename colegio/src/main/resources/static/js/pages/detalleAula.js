/**
 * Pagina de detalle de un aula.
 *
 * <p>Muestra los datos del aula, sus alumnos, los profesores asignados
 * y permite gestionar el tutor del aula.</p>
 *
 * @module detalleAula
 * @see {@link AulaAPI}
 * @see {@link AlumnoAPI}
 * @see {@link ProfesorAPI}
 * @see {@link ProfesorAsignaturaAPI}
 */

import { AulaAPI } from "../api/aulaApi.js";
import { AlumnoAPI } from "../api/alumnoApi.js";
import { ProfesorAsignaturaAPI } from "../api/profesorAsignaturaApi.js";
import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { REGEX } from "../utils/constantes.js";

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id || !REGEX.SOLO_NUMEROS.test(id)) {
    root.innerHTML = `<p class="error">Aula no encontrada</p>`;
  } else {
    const respuestaAula = await AulaAPI.obtenerPorId(id);
    const aula = respuestaAula.datos;

    if (!aula) {
      root.innerHTML = `<p class="error">Aula no encontrada</p>`;
    } else {
      const respuestaAlumnos = await AlumnoAPI.obtenerPorAula(id);
      const alumnos = respuestaAlumnos.datos;
      const totalAlumnos = alumnos ? alumnos.length : 0;

      // Se genera una fila por cada alumno matriculado en el aula.
      let alumnosHtml = "";
      if (alumnos && alumnos.length > 0) {
        for (const alumno of alumnos) {
          alumnosHtml += `
                        <tr>
                            <td>${alumno.codigo}</td>
                            <td><a href="detalleAlumno.html?id=${alumno.id}">${alumno.nombre} ${alumno.apellido}</a></td>
                            <td>${alumno.fechaNacimiento}</td>
                        </tr>
                    `;
        }
      } else {
        alumnosHtml = `<tr><td colspan="3">Sin alumnos</td></tr>`;
      }

      const respuestaProfesores =
        await ProfesorAsignaturaAPI.obtenerPorAula(id);
      const profesorAsignaturas = respuestaProfesores.datos;

      // Se genera una fila por cada relacion profesor-asignatura del aula.
      let profesoresHtml = "";
      if (profesorAsignaturas && profesorAsignaturas.length > 0) {
        for (const pa of profesorAsignaturas) {
          profesoresHtml += `
                        <tr>
                            <td>${pa.profesor.nombre} ${pa.profesor.apellido}</td>
                            <td>${pa.asignatura.nombre}</td>
                            <td>${pa.horasSemanales}</td>
                        </tr>
                    `;
        }
      } else {
        profesoresHtml = `<tr><td colspan="3">Sin profesores asignados</td></tr>`;
      }
      root.innerHTML = /* html */ `
        <div class="containerDetalle">
            <h1>Detalle del Aula</h1>

            <div class="card--detalle">
                <div class="card--detalle--body">
                    <h2>${aula.codigo}</h2>

                    <hr>

                    <p><strong>Curso:</strong> ${aula.curso}</p>
                    <p><strong>Grupo:</strong> ${aula.grupo}</p>
                    <p><strong>Ocupacion:</strong> ${totalAlumnos}/${aula.capacidad}</p>

                    <p>
                        <strong>Tutor:</strong>
                        ${aula.tutor
              ? aula.tutor.nombre + " " + aula.tutor.apellido
              : "Sin tutor"
            }
                    </p>
                </div>
            </div>

            <h2>Gestionar Tutor</h2>

            <div id="gestionTutor">
                <select id="selectProfesor"></select>

                <button id="btnAsignarTutor">Asignar tutor</button>

                <button id="btnEliminarTutor">Eliminar tutor</button>

                <p id="mensajeTutor"></p>
            </div>

            <h2>Alumnos</h2>

            <a href="crearAlumno.html">Añadir alumno</a>

            <table>
                <thead>
                    <tr>
                        <th>Codigo</th>
                        <th>Nombre</th>
                        <th>Fecha Nacimiento</th>
                    </tr>
                </thead>

                <tbody>
                    ${alumnosHtml}
                </tbody>
            </table>

            <h2>Profesores y asignaturas</h2>

            <table>
                <thead>
                    <tr>
                        <th>Profesor</th>
                        <th>Asignatura</th>
                        <th>Horas semanales</th>
                    </tr>
                </thead>

                <tbody>
                    ${profesoresHtml}
                </tbody>
            </table>

            <a href="menuAula.html">Volver</a>
        </div>
`;

      const respuestaTodosProfesores = await ProfesorAPI.obtenerTodos();
      const todosProfesores = respuestaTodosProfesores.datos;
      const selectProfesor = document.getElementById("selectProfesor");

      if (todosProfesores && todosProfesores.length > 0) {
        for (const profesor of todosProfesores) {
          const opcion = document.createElement("option");
          opcion.value = profesor.id;
          opcion.textContent =
            profesor.nombre +
            " " +
            profesor.apellido +
            " (" +
            profesor.especialidad +
            ")";
          selectProfesor.appendChild(opcion);
        }
      }

      /** Boton asignar tutor: comprueba si ya hay tutor, si no envia el profesor seleccionado. */
      document
        .getElementById("btnAsignarTutor")
        .addEventListener("click", async function () {
          const mensajeTutor = document.getElementById("mensajeTutor");
          mensajeTutor.textContent = "";
          mensajeTutor.className = "";

          if (aula.tutor) {
            mensajeTutor.textContent =
              "Ya hay un tutor asignado. Eliminalo primero.";
            mensajeTutor.className = "mensaje--error";
          } else {
            const profesorId = selectProfesor.value;
            const resultado = await AulaAPI.asignarTutor(id, profesorId);
            if (resultado.datos) {
              window.location.reload();
            } else {
              mensajeTutor.textContent = resultado.error.mensaje;
              mensajeTutor.className = "mensaje--error";
            }
          }
        });
      /** Boton eliminar tutor: desvincula el tutor actual del aula y recarga. */
      document
        .getElementById("btnEliminarTutor")
        .addEventListener("click", async function () {
          const mensajeTutor = document.getElementById("mensajeTutor");
          mensajeTutor.textContent = "";
          mensajeTutor.className = "";
          const resultado = await AulaAPI.eliminarTutor(id);
          if (resultado.datos) {
            window.location.reload();
          } else {
            mensajeTutor.textContent = "Error al eliminar el tutor";
            mensajeTutor.className = "mensaje--error";
          }
        });
    }
  }
});

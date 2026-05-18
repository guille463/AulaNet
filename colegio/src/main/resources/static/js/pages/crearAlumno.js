import { AlumnoAPI } from "../api/alumnoApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { CURSOS, GRUPOS } from "../utils/constantes.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  let opcionesCurso = "";
  for (const curso of CURSOS) {
    opcionesCurso += `<option value="${curso}">${curso}</option>`;
  }

  let opcionesGrupo = "";
  for (const grupo of GRUPOS) {
    opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
  }

  root.innerHTML = `
        <div class="containerCrear">
            <h1>Crear Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <input type="text" id="inputNombre" placeholder="Nombre">
                    <input type="text" id="inputApellido" placeholder="Apellido">
                    <input type="date" id="inputFecha">
                    <select id="inputCurso">${opcionesCurso}</select>
                    <select id="inputGrupo">${opcionesGrupo}</select>
                    <button id="btnCrear">Crear</button>
                    <a href="menuAlumno.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
    `;

  document.getElementById("btnCrear").addEventListener("click", async function () {
    const nombre = document.getElementById("inputNombre").value.trim();
    const apellido = document.getElementById("inputApellido").value.trim();
    const fecha = document.getElementById("inputFecha").value;
    const curso = document.getElementById("inputCurso").value;
    const grupo = document.getElementById("inputGrupo").value;
    const mensaje = document.getElementById("mensaje");

    if (!nombre || !apellido || !fecha) {
      mensaje.textContent = "Todos los campos son obligatorios";
      mensaje.className = "mensaje--error";
    } else {
      const respuestaAula = await AulaAPI.obtenerPorCodigo(curso + grupo);

      if (!respuestaAula.datos) {
        mensaje.textContent = "Aula no encontrada";
        mensaje.className = "mensaje--error";
      } else {
        const alumno = {};
        alumno.nombre = nombre;
        alumno.apellido = apellido;
        alumno.fechaNacimiento = fecha;
        alumno.aula = {};
        alumno.aula.id = respuestaAula.datos.id;

        const resultado = await AlumnoAPI.crear(alumno);

        if (resultado.datos) {
          window.location.href = "alumnoMenu.html";
        } else {
          const errorCrear = JSON.parse(resultado.error);
          if (errorCrear.mensaje.includes("llena")) {
            mensaje.textContent = "Error: el aula está llena.";
          } else {
            mensaje.textContent = "Error: " + errorCrear.mensaje;
          }
          mensaje.className = "mensaje--error";
        }
      }
    }
  });
});
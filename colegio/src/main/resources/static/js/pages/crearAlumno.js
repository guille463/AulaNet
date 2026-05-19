import { AlumnoAPI } from "../api/alumnoApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const anioActual = new Date().getFullYear();
  const fechaMin = `${anioActual - 14}-01-01`;
  const fechaMax = `${anioActual - 5}-12-31`;

  let opcionesCurso = `<option value="">-- Selecciona curso --</option>`;
  for (const curso of CURSOS) {
    opcionesCurso += `<option value="${curso}">${curso}</option>`;
  }

  let opcionesGrupo = `<option value="">-- Selecciona grupo --</option>`;
  for (const grupo of GRUPOS) {
    opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
  }

  root.innerHTML = `
        <div class="containerCrear">
            <h1>Crear Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <label for="inputNombre">Nombre</label>
                    <input type="text" id="inputNombre" placeholder="Ej: Juan">
                    <label for="inputApellido">Apellido</label>
                    <input type="text" id="inputApellido" placeholder="Ej: García López">
                    <label for="inputFecha">Fecha de Nacimiento</label>
                    <input type="date" id="inputFecha" min="${fechaMin}" max="${fechaMax}">
                    <label for="inputCurso">Curso</label>
                    <select id="inputCurso">${opcionesCurso}</select>
                    <label for="inputGrupo">Grupo</label>
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

    if (!nombre || !apellido || !fecha || !curso || !grupo) {
      mensaje.textContent = "Todos los campos son obligatorios";
      mensaje.className = "mensaje--error";
    } else if (fecha < fechaMin || fecha > fechaMax) {
      mensaje.textContent = "La fecha de nacimiento debe estar entre " + fechaMin + " y " + fechaMax;
      mensaje.className = "mensaje--error";
    } else if (!REGEX.SOLO_LETRAS.test(nombre)) {
      mensaje.textContent = "El nombre solo puede contener letras";
      mensaje.className = "mensaje--error";
    } else if (!REGEX.SOLO_LETRAS.test(apellido)) {
      mensaje.textContent = "El apellido solo puede contener letras";
      mensaje.className = "mensaje--error";
    } else {
      const respuestaAula = await AulaAPI.obtenerPorCodigo(curso + grupo);

      if (!respuestaAula.datos) {
        mensaje.textContent = "No se encontró el aula para el curso y grupo seleccionados";
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
          window.location.href = "menuAlumno.html";
        } else {
          mensaje.textContent = resultado.error.mensaje;
          mensaje.className = "mensaje--error";
        }
      }
    }
  });
});
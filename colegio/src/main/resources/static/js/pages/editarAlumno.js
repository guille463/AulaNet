import { AlumnoAPI } from "../api/alumnoApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { CURSOS, GRUPOS } from "../utils/constantes.js";

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
        let opcionesCurso = "";
        for (const curso of CURSOS) {
            const seleccionado = alumno.aula.curso === curso ? "selected" : "";
            opcionesCurso += `<option value="${curso}" ${seleccionado}>${curso}</option>`;
        }

        let opcionesGrupo = "";
        for (const grupo of GRUPOS) {
            const seleccionado = alumno.aula.grupo === grupo ? "selected" : "";
            opcionesGrupo += `<option value="${grupo}" ${seleccionado}>Grupo ${grupo}</option>`;
        }

        root.innerHTML = `
            <div class="containerEditar">
                <h1>Editar Alumno</h1>
                <div class="card">
                    <div class="card-body">
                        <p><strong>Código:</strong> ${alumno.codigo}</p>
                        <label>Nombre</label>
                        <input type="text" id="inputNombre" value="${alumno.nombre}">
                        <label>Apellido</label>
                        <input type="text" id="inputApellido" value="${alumno.apellido}">
                        <label>Fecha de Nacimiento</label>
                        <input type="date" id="inputFecha" value="${alumno.fechaNacimiento}">
                        <label>Curso</label>
                        <select id="inputCurso">${opcionesCurso}</select>
                        <label>Grupo</label>
                        <select id="inputGrupo">${opcionesGrupo}</select>
                        <p id="mensaje"></p>
                        <button id="btnGuardar">Guardar</button>
                        <a href="menuAlumno.html">Cancelar</a>
                    </div>
                </div>
            </div>
        `;

        document.getElementById("btnGuardar").addEventListener("click", async function () {
            const curso = document.getElementById("inputCurso").value;
            const grupo = document.getElementById("inputGrupo").value;
            const mensaje = document.getElementById("mensaje");

            const respuestaAula = await AulaAPI.obtenerPorCodigo(curso + grupo);

            if (!respuestaAula.datos) {
                mensaje.textContent = "Aula no encontrada";
                mensaje.className = "mensaje--error";
            } else {
                alumno.nombre = document.getElementById("inputNombre").value.trim();
                alumno.apellido = document.getElementById("inputApellido").value.trim();
                alumno.fechaNacimiento = document.getElementById("inputFecha").value;
                alumno.aula = {};
                alumno.aula.id = respuestaAula.datos.id;

                const resultado = await AlumnoAPI.actualizar(id, alumno);

                if (resultado.datos) {
                    window.location.href = "alumnoMenu.html";
                } else {
                    mensaje.textContent = "Error al actualizar el alumno";
                    mensaje.className = "mensaje--error";
                }
            }
        });
    }
});
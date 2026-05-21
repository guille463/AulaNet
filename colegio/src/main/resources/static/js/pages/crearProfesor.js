/**
 * Pagina de creacion de un nuevo profesor.
 *
 * <p>Recoge los datos del formulario, valida los campos y llama a
 * {@link ProfesorAPI} para persistir el registro.</p>
 *
 * @module crearProfesor
 * @see {@link ProfesorAPI}
 */

import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { ESPECIALIDADES, CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

// ============================================================
// INICIALIZACION DE OPCIONES
// ============================================================

/** @type {string} HTML de opciones para el selector de especialidad, con placeholder inicial. */
let opcionesEspecialidad = `<option value="">-- Selecciona especialidad --</option>`;
for (const especialidad of ESPECIALIDADES) {
    opcionesEspecialidad += `<option value="${especialidad.valor}">${especialidad.etiqueta}</option>`;
}

/** @type {string} HTML de opciones para el selector de curso, con placeholder inicial. */
let opcionesCurso = `<option value="">-- Selecciona curso --</option>`;
for (const curso of CURSOS) {
    opcionesCurso += `<option value="${curso}">${curso}</option>`;
}

/** @type {string} HTML de opciones para el selector de grupo, con placeholder inicial. */
let opcionesGrupo = `<option value="">-- Selecciona grupo --</option>`;
for (const grupo of GRUPOS) {
    opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
}

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = añadirContainerCrearProfesor(); 

    /** Cambio de especialidad: muestra u oculta el selector de aula si es tutor GENERAL. */
    document.getElementById("inputEspecialidad").addEventListener("change", function () {
        const selectAula = document.getElementById("selectAula");
        selectAula.style.display = this.value === "GENERAL" ? "block" : "none";
    });

    /** Boton crear: valida el formulario y envia el profesor a la API. */
    document.getElementById("btnCrear").addEventListener("click", async function () {
        const nombre = document.getElementById("inputNombre").value.trim();
        const apellido = document.getElementById("inputApellido").value.trim();
        const email = document.getElementById("inputEmail").value.trim();
        const especialidad = document.getElementById("inputEspecialidad").value;
        const mensaje = document.getElementById("mensaje");

        if (!nombre || !apellido || !email || !especialidad) {
            mensaje.textContent = "Todos los campos son obligatorios";
            mensaje.className = "mensaje--error";
        } else if (!REGEX.SOLO_LETRAS.test(nombre)) {
            mensaje.textContent = "El nombre solo puede contener letras";
            mensaje.className = "mensaje--error";
        } else if (!REGEX.SOLO_LETRAS.test(apellido)) {
            mensaje.textContent = "El apellido solo puede contener letras";
            mensaje.className = "mensaje--error";
        } else if (!REGEX.EMAIL.test(email)) {
            mensaje.textContent = "El formato del email no es valido";
            mensaje.className = "mensaje--error";
        } else if (especialidad === "GENERAL" && (!document.getElementById("inputCurso").value || !document.getElementById("inputGrupo").value)) {
            mensaje.textContent = "Selecciona el curso y grupo del aula que tutorizara";
            mensaje.className = "mensaje--error";
        } else {
            const profesor = {};
            profesor.nombre = nombre;
            profesor.apellido = apellido;
            profesor.email = email;
            profesor.especialidad = especialidad;

            if (especialidad === "GENERAL") {
                const curso = document.getElementById("inputCurso").value;
                const grupo = document.getElementById("inputGrupo").value;
                // El codigo del aula se forma concatenando curso y grupo, ej: "1º" + "A" = "1ºA"
                profesor.codigoAula = curso + grupo;
            }

            const resultado = await ProfesorAPI.crear(profesor);

            if (resultado.datos) {
                window.location.href = "menuProfesor.html";
            } else {
                mensaje.textContent = resultado.error.mensaje;
                mensaje.className = "mensaje--error";
            }
        }
    });
});

function añadirContainerCrearProfesor(){
return`
 <div class="containerCrear">
            <h1>Crear Profesor</h1>
            <div class="card">
                <div class="card-body">
                    <label for="inputNombre">Nombre</label>
                    <input type="text" id="inputNombre" placeholder="Ej: Carlos">
                    <label for="inputApellido">Apellido</label>
                    <input type="text" id="inputApellido" placeholder="Ej: Martinez Ruiz">
                    <label for="inputEmail">Email</label>
                    <input type="email" id="inputEmail" placeholder="Ej: carlos.martinez@colegio.es">
                    <label for="inputEspecialidad">Especialidad</label>
                    <select id="inputEspecialidad">${opcionesEspecialidad}</select>
                    <div id="selectAula" style="display:none;">
                        <label for="inputCurso">Curso (tutor)</label>
                        <select id="inputCurso">${opcionesCurso}</select>
                        <label for="inputGrupo">Grupo (tutor)</label>
                        <select id="inputGrupo">${opcionesGrupo}</select>
                    </div>
                    <button id="btnCrear">Crear</button>
                    <a href="menuProfesor.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
`

}
import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { ESPECIALIDADES, CURSOS, GRUPOS } from "../utils/constantes.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    let opcionesEspecialidad = "";
    for (const especialidad of ESPECIALIDADES) {
        opcionesEspecialidad += `<option value="${especialidad.valor}">${especialidad.etiqueta}</option>`;
    }

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
            <h1>Crear Profesor</h1>
            <div class="card">
                <div class="card-body">
                    <input type="text" id="inputNombre" placeholder="Nombre">
                    <input type="text" id="inputApellido" placeholder="Apellido">
                    <input type="text" id="inputEmail" placeholder="Email">
                    <select id="inputEspecialidad">${opcionesEspecialidad}</select>
                    <div id="selectAula">
                        <select id="inputCurso">${opcionesCurso}</select>
                        <select id="inputGrupo">${opcionesGrupo}</select>
                    </div>
                    <button id="btnCrear">Crear</button>
                    <a href="profesorMenu.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
    `;

    document.getElementById("inputEspecialidad").addEventListener("change", function () {
        const selectAula = document.getElementById("selectAula");
        if (this.value === "GENERAL") {
            selectAula.style.display = "block";
        } else {
            selectAula.style.display = "none";
        }
    });

    document.getElementById("btnCrear").addEventListener("click", async function () {
        const nombre = document.getElementById("inputNombre").value.trim();
        const apellido = document.getElementById("inputApellido").value.trim();
        const email = document.getElementById("inputEmail").value.trim();
        const especialidad = document.getElementById("inputEspecialidad").value;
        const mensaje = document.getElementById("mensaje");

       if (!nombre || !apellido || !email) {
    mensaje.textContent = "Todos los campos son obligatorios";
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
        profesor.codigoAula = curso + grupo;
    }

    const resultado = await ProfesorAPI.crear(profesor);

    if (resultado.datos) {
        window.location.href = "profesorMenu.html";
    } else {
        const errorProfe = JSON.parse(resultado.error);
        if (errorProfe.mensaje.includes("email")) {
            mensaje.textContent = "Error: este email ya está registrado.";
        } else {
            mensaje.textContent = "Error: " + errorProfe.mensaje;
        }
        mensaje.className = "mensaje--error";
    }
}
    });
});
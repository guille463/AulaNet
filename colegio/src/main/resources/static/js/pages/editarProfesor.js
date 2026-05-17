import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { ESPECIALIDADES, CURSOS, GRUPOS } from "../utils/constantes.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
    } else {
        const respuestaProfesor = await ProfesorAPI.obtenerPorId(id);
        const profesor = respuestaProfesor.datos;

        if (!profesor) {
            root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
        } else {
            let opcionesEspecialidad = "";
            for (const especialidad of ESPECIALIDADES) {
                const seleccionado = profesor.especialidad === especialidad.valor ? "selected" : "";
                opcionesEspecialidad += `<option value="${especialidad.valor}" ${seleccionado}>${especialidad.etiqueta}</option>`;
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
                <div class="containerEditar">
                    <h1>Editar Profesor</h1>
                    <div class="card">
                        <div class="card-body">
                            <p><strong>Código:</strong> ${profesor.codigo}</p>
                            <label>Nombre</label>
                            <input type="text" id="inputNombre" value="${profesor.nombre}">
                            <label>Apellido</label>
                            <input type="text" id="inputApellido" value="${profesor.apellido}">
                            <label>Email</label>
                            <input type="text" id="inputEmail" value="${profesor.email}">
                            <label>Especialidad</label>
                            <select id="inputEspecialidad">${opcionesEspecialidad}</select>
                            <div id="selectAula" style="display:${profesor.especialidad === "GENERAL" ? "block" : "none"};">
                                <select id="inputCurso">${opcionesCurso}</select>
                                <select id="inputGrupo">${opcionesGrupo}</select>
                            </div>
                            <p id="mensaje"></p>
                            <button id="btnGuardar">Guardar</button>
                            <a href="profesorMenu.html">Cancelar</a>
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

            document.getElementById("btnGuardar").addEventListener("click", async function () {
                const mensaje = document.getElementById("mensaje");

                profesor.nombre = document.getElementById("inputNombre").value.trim();
                profesor.apellido = document.getElementById("inputApellido").value.trim();
                profesor.email = document.getElementById("inputEmail").value.trim();
                profesor.especialidad = document.getElementById("inputEspecialidad").value;

                if (profesor.especialidad === "GENERAL") {
                    const curso = document.getElementById("inputCurso").value;
                    const grupo = document.getElementById("inputGrupo").value;
                    profesor.codigoAula = curso + grupo;
                } else {
                    profesor.codigoAula = null;
                }

                const resultadoActualizar = await ProfesorAPI.actualizar(id, profesor);

               if (resultadoActualizar.datos) {
    window.location.href = "profesorMenu.html";
} else {
    mensaje.textContent = "Error al actualizar el profesor";
    mensaje.className = "mensaje--error";
}
            });
        }
    }
});
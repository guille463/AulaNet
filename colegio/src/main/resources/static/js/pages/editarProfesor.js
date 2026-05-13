import { getProfesorPorId, putProfesor } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
        return;
    }

    const profesor = await getProfesorPorId(id);

    if (!profesor) {
        root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
        return;
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
                    <select id="inputEspecialidad">
                        <option value="GENERAL" ${profesor.especialidad === "GENERAL" ? "selected" : ""}>General</option>
                        <option value="EDUCACION_FISICA" ${profesor.especialidad === "EDUCACION_FISICA" ? "selected" : ""}>Educación Física</option>
                        <option value="INGLES" ${profesor.especialidad === "INGLES" ? "selected" : ""}>Inglés</option>
                        <option value="MUSICA" ${profesor.especialidad === "MUSICA" ? "selected" : ""}>Música</option>
                        <option value="LOGOPEDA" ${profesor.especialidad === "LOGOPEDA" ? "selected" : ""}>Logopeda</option>
                        <option value="RELIGION" ${profesor.especialidad === "RELIGION" ? "selected" : ""}>Religión</option>
                    </select>
                    <div id="selectAula" style="display:${profesor.especialidad === "GENERAL" ? "block" : "none"};">
                        <select id="inputCurso">
                            <option value="1º">1º</option>
                            <option value="2º">2º</option>
                            <option value="3º">3º</option>
                            <option value="4º">4º</option>
                            <option value="5º">5º</option>
                            <option value="6º">6º</option>
                        </select>
                        <select id="inputGrupo">
                            <option value="A">Grupo A</option>
                            <option value="B">Grupo B</option>
                        </select>
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

        const resultado = await putProfesor(id, profesor);

        if (resultado) {
            window.location.href = "profesorMenu.html";
        } else {
            mensaje.textContent = "Error al actualizar el profesor";
        }
    });
});
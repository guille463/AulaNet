import { ProfesorAPI } from "../api/profesorApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
        <div class="containerCrearProfesor">
            <h1>Crear Profesor</h1>
            <div class="card">
                <div class="card-body">
                    <input type="text" id="inputNombre" placeholder="Nombre">
                    <input type="text" id="inputApellido" placeholder="Apellido">
                    <input type="text" id="inputEmail" placeholder="Email">
                    <select id="inputEspecialidad">
                        <option value="GENERAL">General</option>
                        <option value="EDUCACION_FISICA">Educación Física</option>
                        <option value="INGLES">Inglés</option>
                        <option value="MUSICA">Música</option>
                        <option value="LOGOPEDA">Logopeda</option>
                        <option value="RELIGION">Religión</option>
                    </select>
                    <div id="selectAula">
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
            return;
        }

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
        }
    });
});
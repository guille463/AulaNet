import { postProfesor } from "../api/profesorApi.js";
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

});
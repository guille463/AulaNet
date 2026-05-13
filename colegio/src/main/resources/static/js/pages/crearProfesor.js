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
                    <button id="btnCrear">Crear</button>
                    <a href="profesorMenu.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
    `;

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

        const profesor = {
            nombre: nombre,
            apellido: apellido,
            email: email,
            especialidad: especialidad
        };

        const resultado = await postProfesor(profesor);

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
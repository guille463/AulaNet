import { postAlumno } from "../api/alumnoApi.js";
import { getAulaPorCodigo } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
        <div class="containerCrearAlumno">
            <h1>Crear Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <input type="text" id="inputNombre" placeholder="Nombre">
                    <input type="text" id="inputApellido" placeholder="Apellido">
                    <input type="text" id="inputEmail" placeholder="Email">
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
                    <button id="btnCrear">Crear</button>
                    <a href="alumnoMenu.html">Cancelar</a>
                    <p id="mensaje"></p>
                </div>
            </div>
        </div>
    `;

  document
    .getElementById("btnCrear")
    .addEventListener("click", async function () {
      const nombre = document.getElementById("inputNombre").value.trim();
      const apellido = document.getElementById("inputApellido").value.trim();
      const email = document.getElementById("inputEmail").value.trim();
      const curso = document.getElementById("inputCurso").value;
      const grupo = document.getElementById("inputGrupo").value;
      const mensaje = document.getElementById("mensaje");

      if (!nombre || !apellido || !email) {
        mensaje.textContent = "Todos los campos son obligatorios";
        return;
      }

      const aula = await getAulaPorCodigo(curso + grupo);

      if (!aula) {
        mensaje.textContent = "Aula no encontrada";
        return;
      }

      const alumno = {
        nombre: nombre,
        apellido: apellido,
        email: email,
        aula: { id: aula.id },
      };

      const resultado = await postAlumno(alumno);

      if (resultado.datos) {
        window.location.href = "alumnoMenu.html";
      } else {
        const errorCrear = JSON.parse(resultado.error);
        if (errorCrear.mensaje.includes("email")) {
          mensaje.textContent = "Error: este email ya está registrado.";
        } else if (errorCrear.mensaje.includes("llena")) {
          mensaje.textContent = "Error: el aula está llena.";
        } else {
          mensaje.textContent = "Error: " + errorCrear.mensaje;
        }
      }
    });
});

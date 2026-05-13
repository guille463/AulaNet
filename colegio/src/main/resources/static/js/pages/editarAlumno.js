import { AlumnoAPI } from "../api/alumnoApi.js";
import { AulaAPI } from "../api/aulaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    const respuesta = await AlumnoAPI.obtenerPorId(id);
    const alumno = respuesta.datos;

    if (!alumno) {
        root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
        return;
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
                <label>Email</label>
                <input type="text" id="inputEmail" value="${alumno.email}">
                <label>Curso</label>
                <select id="inputCurso">
                    <option value="1º">1º</option>
                    <option value="2º">2º</option>
                    <option value="3º">3º</option>
                    <option value="4º">4º</option>
                    <option value="5º">5º</option>
                    <option value="6º">6º</option>
                </select>
                <label>Grupo</label>
                <select id="inputGrupo">
                    <option value="A">Grupo A</option>
                    <option value="B">Grupo B</option>
                </select>
                <p id="mensaje"></p>
                <button id="btnGuardar">Guardar</button>
                <a href="alumnoMenu.html">Cancelar</a>
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
        } else {
            alumno.nombre = document.getElementById("inputNombre").value.trim();
            alumno.apellido = document.getElementById("inputApellido").value.trim();
            alumno.email = document.getElementById("inputEmail").value.trim();
            alumno.aula = { id: respuestaAula.datos.id };

            const resultado = await AlumnoAPI.actualizar(id, alumno);

            if (resultado.datos) {
                window.location.href = "alumnoMenu.html";
            } else {
                mensaje.textContent = "Error al actualizar el alumno";
            }
        }
    });
});
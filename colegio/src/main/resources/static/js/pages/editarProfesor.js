import { getAlumnoPorId, putAlumno } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        root.innerHTML = `<p class="error">Alumno no encontrado</p>`;
        return;
    }

    const alumno = await getAlumnoPorId(id);

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
                    <input type="text" id="inputNombre" class="form-control mb-2" value="${alumno.nombre}">
                    <label>Apellido</label>
                    <input type="text" id="inputApellido" class="form-control mb-2" value="${alumno.apellido}">
                    <label>Email</label>
                    <input type="text" id="inputEmail" class="form-control mb-2" value="${alumno.email}">
                    <button class="btn btn-Guardar" id="btnGuardar">Guardar</button>
                    <a href="alumnoMenu.html">Cancelar</a>
                </div>
            </div>
        </div>
    `;

    document.getElementById("btnGuardar").addEventListener("click", async function () {
        const alumnoActualizado = {
            nombre: document.getElementById("inputNombre").value.trim(),
            apellido: document.getElementById("inputApellido").value.trim(),
            email: document.getElementById("inputEmail").value.trim(),
            aula: { id: alumno.aula.id }
        };

        const resultado = await putAlumno(id, alumnoActualizado);

        if (resultado) {
            window.location.href = "alumnoMenu.html";
        } else {
            root.innerHTML += `<p class="error">Error al actualizar el alumno</p>`;
        }
    });
});
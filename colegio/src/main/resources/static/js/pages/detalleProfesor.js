
import { AlumnoAPI } from "../api/profesorApi.js";
import {getProfesorAsignaturaPorProfesorId } from "../api/profesorAsignaturaApi.js";
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

    const asignaturas = await getProfesorAsignaturaPorProfesorId(id);

    let asignaturasHtml = "";
    if (asignaturas && asignaturas.length > 0) {
        asignaturas.forEach(function (pa) {
            asignaturasHtml += `
                <tr>
                    <td>${pa.asignatura.nombre}</td>
                    <td>${pa.asignatura.curso}</td>
                    <td>${pa.horasSemanales}</td>
                </tr>
            `;
        });
    } else {
        asignaturasHtml = `<tr><td colspan="3">Sin asignaturas</td></tr>`;
    }

    root.innerHTML = `
        <div class="containerDetalle">
            <h1>Detalle del Profesor</h1>
            <div class="card">
                <div class="card-body">
                    <h2>${profesor.codigo}</h2>
                    <hr>
                    <p><strong>Nombre:</strong> ${profesor.nombre} ${profesor.apellido}</p>
                    <p><strong>Email:</strong> ${profesor.email}</p>
                    <p><strong>Especialidad:</strong> ${profesor.especialidad}</p>
                </div>
            </div>
            <h2>Asignaturas que imparte</h2>
            <table>
                <thead>
                    <tr>
                        <th>Asignatura</th>
                        <th>Curso</th>
                        <th>Horas semanales</th>
                    </tr>
                </thead>
                <tbody>
                    ${asignaturasHtml}
                </tbody>
            </table>
            <a href="profesorMenu.html">Volver</a>
        </div>
    `;
});
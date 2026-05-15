import { AsignaturaAPI } from "../api/asignaturaApi.js";
import { ProfesorAsignaturaAPI } from "../api/profesorAsignaturaApi.js";
import { AlumnoAsignaturaAPI } from "../api/alumnoAsignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    const respuesta = await AsignaturaAPI.obtenerPorId(id);
    const asignatura = respuesta.datos;

    if (!asignatura) {
        root.innerHTML = `<p class="error">Asignatura no encontrada</p>`;
    } else {
        const respuestaProfesores = await ProfesorAsignaturaAPI.obtenerPorAsignatura(id);
        const profesores = respuestaProfesores.datos;

        let profesoresHtml = "";
        if (profesores && profesores.length > 0) {
            for (const pa of profesores) {
                profesoresHtml += `
                    <tr>
                        <td><a href="detalleProfesor.html?id=${pa.profesor.id}">${pa.profesor.nombre} ${pa.profesor.apellido}</a></td>
                        <td>${pa.profesor.especialidad}</td>
                        <td>${pa.horasSemanales}</td>
                    </tr>
                `;
            }
        } else {
            profesoresHtml = `<tr><td colspan="3">Sin profesores</td></tr>`;
        }

        const respuestaAlumnos = await AlumnoAsignaturaAPI.obtenerPorAsignatura(id);
        const matriculas = respuestaAlumnos.datos;

        let alumnosHtml = "";
        if (matriculas && matriculas.length > 0) {
            for (const matricula of matriculas) {
                alumnosHtml += `
                    <tr>
                        <td>${matricula.alumno.codigo}</td>
                        <td><a href="detalleAlumno.html?id=${matricula.alumno.id}">${matricula.alumno.nombre} ${matricula.alumno.apellido}</a></td>
                        <td>${matricula.nota}</td>
                    </tr>
                `;
            }
        } else {
            alumnosHtml = `<tr><td colspan="3">Sin alumnos matriculados</td></tr>`;
        }

        root.innerHTML = `
            <div class="containerDetalle">
                <h1>Detalle de la Asignatura</h1>
                <div class="card">
                    <div class="card-body">
                        <h2>${asignatura.codigo}</h2>
                        <hr>
                        <p><strong>Nombre:</strong> ${asignatura.nombre}</p>
                        <p><strong>Curso:</strong> ${asignatura.curso}</p>
                        <p><strong>Horas semanales:</strong> ${asignatura.horasSemana}</p>
                        <p><strong>Descripción:</strong> ${asignatura.descripcion}</p>
                    </div>
                </div>
                <h2>Profesores</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Especialidad</th>
                            <th>Horas semanales</th>
                        </tr>
                    </thead>
                    <tbody>${profesoresHtml}</tbody>
                </table>
                <h2>Alumnos matriculados</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Nota</th>
                        </tr>
                    </thead>
                    <tbody>${alumnosHtml}</tbody>
                </table>
                <a href="asignaturaMenu.html">Volver</a>
            </div>
        `;
    }
});
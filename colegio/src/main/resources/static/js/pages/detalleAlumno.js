import { getAlumnoPorId } from "../api/alumnoApi.js";
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

    const aula = alumno.aula;
    const tutor = aula.tutor;

    let tutorTexto;
    if (tutor) {
        tutorTexto = tutor.nombre + " " + tutor.apellido;
    } else {
        tutorTexto = "Sin tutor";
    }

    root.innerHTML = `
        <div class="containerDetalle">
            <h1>Detalle del Alumno</h1>
            <div class="card">
                <div class="card-body">
                    <h2>${alumno.codigo}</h2>
                    <hr>
                    <p><strong>Nombre:</strong> ${alumno.nombre} ${alumno.apellido}</p>
                    <p><strong>Email:</strong> ${alumno.email}</p>
                    <p><strong>Curso:</strong> ${aula.curso}</p>
                    <p><strong>Grupo:</strong> ${aula.grupo}</p>
                    <p><strong>Aula:</strong> ${aula.codigo}</p>
                    <p><strong>Tutor:</strong> ${tutorTexto}</p>
                </div>
            </div>
            <a href="alumnoMenu.html">Volver</a>
        </div>
    `;
});
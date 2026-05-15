import { AsignaturaAPI } from "../api/asignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAsignatura } from "../components/asignaturaComponente.js";
import { CURSOS } from "../utils/constantes.js";

let todasAsignaturas = [];

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
        <div class="containerAsignaturas">
            <h1>Gestionar Asignaturas</h1>
            <div id="listaAsignaturas"></div>
        </div>
    `;

    await cargarTodos();

    document.getElementById("listaAsignaturas").addEventListener("click", function (evento) {
        if (evento.target.classList.contains("btnNombreAsignatura")) {
            const nombreAsignatura = evento.target.getAttribute("data-nombre");
            const divCursos = document.getElementById("cursos-" + nombreAsignatura);
            const divTarjeta = document.getElementById("tarjeta-" + nombreAsignatura);

            if (divCursos.style.display === "none") {
                divCursos.style.display = "block";
            } else {
                divCursos.style.display = "none";
                divTarjeta.innerHTML = "";
            }
        }

        if (evento.target.classList.contains("btnCurso")) {
            const nombreAsignatura = evento.target.getAttribute("data-nombre");
            const cursoSeleccionado = evento.target.getAttribute("data-curso");
            const divTarjeta = document.getElementById("tarjeta-" + nombreAsignatura);

            let asignaturaEncontrada = null;

            for (const asignatura of todasAsignaturas) {
                if (asignatura.nombre === nombreAsignatura && asignatura.curso === cursoSeleccionado) {
                    asignaturaEncontrada = asignatura;
                }
            }

            if (asignaturaEncontrada) {
                divTarjeta.innerHTML = crearTarjetaAsignatura(asignaturaEncontrada);
            } else {
                divTarjeta.innerHTML = `<p class="error">Asignatura no encontrada</p>`;
            }
        }
    });
});

async function cargarTodos() {
    const listaAsignaturas = document.getElementById("listaAsignaturas");
    const respuesta = await AsignaturaAPI.obtenerTodos();

    if (respuesta.datos && respuesta.datos.length > 0) {
        todasAsignaturas = respuesta.datos;

        const nombresUnicos = [];
        for (const asignatura of todasAsignaturas) {
            if (!nombresUnicos.includes(asignatura.nombre)) {
                nombresUnicos.push(asignatura.nombre);
            }
        }

        let html = "";

        for (const nombreAsignatura of nombresUnicos) {
            let cursosHtml = "";
            for (const curso of CURSOS) {
                cursosHtml += `<button class="btnCurso" data-nombre="${nombreAsignatura}" data-curso="${curso}">${curso}</button>`;
            }
            html += `
                <div class="asignaturaItem">
                    <button class="btnNombreAsignatura" data-nombre="${nombreAsignatura}">${nombreAsignatura}</button>
                    <div id="cursos-${nombreAsignatura}" style="display:none;">
                        ${cursosHtml}
                    </div>
                    <div id="tarjeta-${nombreAsignatura}"></div>
                </div>
            `;
        }

        listaAsignaturas.innerHTML = html;
    } else {
        listaAsignaturas.innerHTML = `<p class="error">No hay asignaturas</p>`;
    }
}
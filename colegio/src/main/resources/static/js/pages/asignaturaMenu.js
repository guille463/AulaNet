import { AsignaturaAPI } from "../api/asignaturaApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAsignatura } from "../components/asignaturaComponente.js";

let todasAsignaturas = [];

const nombresAsignaturas = [
  "Matematicas",
  "Lengua Castellana y Literatura",
  "Ciencias de la Naturaleza",
  "Ciencias Sociales",
  "Educacion Fisica",
  "Ingles",
  "Musica",
  "Plastica",
  "Religion",
];

const cursos = ["1º", "2º", "3º", "4º", "5º", "6º"];

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

  document
    .getElementById("listaAsignaturas")
    .addEventListener("click", function (evento) {
      if (evento.target.classList.contains("btnNombreAsignatura")) {
        const nombreAsignatura = evento.target.getAttribute("data-nombre");
        const divCursos = document.getElementById("cursos-" + nombreAsignatura);
        const divTarjeta = document.getElementById(
          "tarjeta-" + nombreAsignatura,
        );

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
        const divTarjeta = document.getElementById(
          "tarjeta-" + nombreAsignatura,
        );

        const asignaturaEncontrada = todasAsignaturas.find(
          function (asignatura) {
            return (
              asignatura.nombre === nombreAsignatura &&
              asignatura.curso === cursoSeleccionado
            );
          },
        );

        if (asignaturaEncontrada) {
          divTarjeta.innerHTML = crearTarjetaAsignatura(asignaturaEncontrada);
        }
      }
    });
});

async function cargarTodos() {
  const listaAsignaturas = document.getElementById("listaAsignaturas");
  const respuesta = await AsignaturaAPI.obtenerTodos();

  if (respuesta.datos && respuesta.datos.length > 0) {
    todasAsignaturas = respuesta.datos;
    let html = "";

    nombresAsignaturas.forEach(function (nombreAsignatura) {
      let cursosHtml = "";
      cursos.forEach(function (curso) {
        cursosHtml += `<button class="btnCurso" data-nombre="${nombreAsignatura}" data-curso="${curso}">${curso}</button>`;
      });

      html += `
    <div class="asignaturaItem">
        <button class="btnNombreAsignatura" data-nombre="${nombreAsignatura}">${nombreAsignatura}</button>
        <div id="cursos-${nombreAsignatura}" style="display:none;">
            ${cursosHtml}
        </div>
        <div id="tarjeta-${nombreAsignatura}"></div>
    </div>
`;
    });

    listaAsignaturas.innerHTML = html;
  } else {
    listaAsignaturas.innerHTML = `<p class="error">No hay asignaturas</p>`;
  }
}

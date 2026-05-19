import { AlumnoAPI } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";
import { CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

//VARIABLES
let idAEliminar = null;
let opcionesCurso = "";
let opcionesGrupo = "";

for (const curso of CURSOS) {
  opcionesCurso += `<option value="${curso}">${curso}</option>`;
}



for (const grupo of GRUPOS) {
  opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
}

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
  <div class="containerGestion">
        <h1>Gestionar Alumnos</h1>
        <a href="crearAlumno.html">➕ Añadir alumno</a>
      <div class="card card--buscador">
          <div class="card-body">
            <h2 class="card-title">Buscar Alumno</h2>
            <select id="tipoBusqueda" class="form-select">
              <option value="id">Por ID</option>
              <option value="nombre">Por Nombre</option>
              <option value="fecha">Por Fecha de Nacimiento</option>
              <option value="curso">Por Curso y Grupo</option>
            </select>
            <div id="inputContainer">
              <input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el ID">
            </div>
            <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
          </div>
        </div>
        <div id="resultado"></div>
        <hr>
        <h2>Lista de Alumnos</h2>
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Fecha Nacimiento</th>
                    <th>Curso</th>
                    <th>Grupo</th>
                    <th>Tutor</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="listaAlumnos"></tbody>
        </table>
      </div>
`;

  cargarTodos();

  document.getElementById("root").addEventListener("click", function (evento) {
    if (evento.target.classList.contains("btnEliminar")) {
      idAEliminar = evento.target.getAttribute("data-id");
      document.getElementById("modalTexto").textContent =
        "¿Estás seguro de que quieres eliminar al alumno " + idAEliminar + "?";
      document.getElementById("modalConfirmar").classList.add("activo");
    }
  });

  document
    .getElementById("btnConfirmarSi")
    .addEventListener("click", async function () {
      document.getElementById("modalConfirmar").classList.remove("activo");
      await eliminarAlumno(idAEliminar);
      const eliminado = idAEliminar; 
      idAEliminar = null;
      document.getElementById("resultado").innerHTML =
        `<p class="mensaje--exito">Alumno con id ${eliminado} eliminado con éxito</p>`;
    });

  document
    .getElementById("btnConfirmarNo")
    .addEventListener("click", function () {
      document.getElementById("modalConfirmar").classList.remove("activo");
      idAEliminar = null;
      document.getElementById("resultado").innerHTML =
        `<p class="mensaje--error">Operación cancelada</p>`;
    });

  document
    .getElementById("tipoBusqueda")
    .addEventListener("change", function () {
      const container = document.getElementById("inputContainer");
      const tipo = this.value;
      if (tipo === "id") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el ID">`;
      } else if (tipo === "nombre") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el nombre">`;
      } else if (tipo === "fecha") {
        container.innerHTML = `<input type="date" id="inputBusqueda" class="form-control">`;
      } else if (tipo === "curso") {
        container.innerHTML = `
          <select id="inputCurso" class="form-select">${opcionesCurso}</select>
          <select id="inputGrupo" class="form-select">${opcionesGrupo}</select>`;
      }
    });

  document.getElementById("btnBuscar").addEventListener("click", function () {
    const tipo = document.getElementById("tipoBusqueda").value;
    if (tipo === "id") {
      const valor = document.getElementById("inputBusqueda").value.trim();
      buscarPorId(valor);
    } else if (tipo === "nombre") {
      const valor = document.getElementById("inputBusqueda").value.trim();
      buscarPorNombre(valor);
    } else if (tipo === "fecha") {
      const valor = document.getElementById("inputBusqueda").value;
      buscarPorFecha(valor);
    } else if (tipo === "curso") {
      const curso = document.getElementById("inputCurso").value;
      const grupo = document.getElementById("inputGrupo").value;
      buscarPorCodigoAula(curso + grupo);
    }
  });
});

async function cargarTodos() {
  const listaAlumnos = document.getElementById("listaAlumnos");
  listaAlumnos.innerHTML = "";
  const respuesta = await AlumnoAPI.obtenerTodos();
  if (respuesta.datos && respuesta.datos.length > 0) {
    let html = "";
    for (const alumno of respuesta.datos) {
      html += crearTarjetaAlumno(alumno);
    }
    listaAlumnos.innerHTML = html;
  } else {
    listaAlumnos.innerHTML = `<tr><td colspan="9" class="mensaje--error">No hay alumnos</td></tr>`;
  }
}

async function buscarPorId(id) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (!id) {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un ID</p>`;
  } else if (!REGEX.SOLO_NUMEROS.test(id)) {
    resultado.innerHTML = `<p class="mensaje--error">El ID solo puede contener números</p>`;
  } else {
    const respuesta = await AlumnoAPI.obtenerPorId(id);
    if (respuesta.datos) {
      resultado.innerHTML = crearTablaAlumnos(crearTarjetaAlumno(respuesta.datos));
    } else {
      resultado.innerHTML = `<p class="mensaje--error">Alumno con id: ${id} no encontrado</p>`;
    }
  }
}

async function buscarPorNombre(nombre) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (!nombre) {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un nombre</p>`;
  } else if (!REGEX.SOLO_LETRAS.test(nombre)) {
    resultado.innerHTML = `<p class="mensaje--error">El nombre solo puede contener letras</p>`;
  } else {
    const respuesta = await AlumnoAPI.obtenerPorNombre(nombre);
    if (respuesta.datos && respuesta.datos.length > 0) {
      let html = "";
      for (const alumno of respuesta.datos) {
        html += crearTarjetaAlumno(alumno);
      }
      resultado.innerHTML = crearTablaAlumnos(html);
    } else {
      resultado.innerHTML = `<p class="mensaje--error">No se encontraron alumnos con nombre: ${nombre}</p>`;
    }
  }
}


async function buscarPorFecha(fecha) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (fecha) {
    const respuesta = await AlumnoAPI.obtenerPorFecha(fecha);
    if (respuesta.datos && respuesta.datos.length > 0) {
      let html = "";
      for (const alumno of respuesta.datos) {
        html += crearTarjetaAlumno(alumno);
      }
      resultado.innerHTML = crearTablaAlumnos(html);
    } else {
      resultado.innerHTML = `<p class="mensaje--error">No se encontraron alumnos nacidos a fecha: ${fecha}</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="mensaje--error">Introduce una fecha</p>`;
  }
}

async function buscarPorCodigoAula(codigoAula) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  const respuesta = await AlumnoAPI.obtenerPorCodigoAula(codigoAula);
  if (respuesta.datos && respuesta.datos.length > 0) {
    let html = "";
    for (const alumno of respuesta.datos) {
      html += crearTarjetaAlumno(alumno);
    }
    resultado.innerHTML = crearTablaAlumnos(html);
  } else {
    resultado.innerHTML = `<p class="mensaje--error">No se encontraron alumnos en esa aula: ${codigoAula}</p>`;
  }
}

async function eliminarAlumno(id) {
  const respuesta = await AlumnoAPI.eliminar(id);
  if (respuesta.datos) {
    cargarTodos();
  } else {
    document.getElementById("listaAlumnos").innerHTML +=
      `<p class="mensaje--error">Error al eliminar</p>`;
  }
}

function crearTablaAlumnos(filas) {
  return `
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Fecha Nacimiento</th>
                    <th>Curso</th>
                    <th>Grupo</th>
                    <th>Tutor</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>${filas}</tbody>
        </table>
    `;
}
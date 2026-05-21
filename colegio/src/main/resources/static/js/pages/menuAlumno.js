/**
 * Pagina de gestion del listado de alumnos.
 *
 * <p>Permite buscar alumnos por ID, nombre, fecha de nacimiento o aula,
 * asi como eliminar registros mediante una confirmacion.</p>
 *
 * @module menuAlumno
 * @see {@link AlumnoAPI}
 * @see {@link crearTarjetaAlumno}
 */

import { AlumnoAPI } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";
import { CURSOS, GRUPOS, REGEX } from "../utils/constantes.js";

// ============================================================
// VARIABLES
// ============================================================

/** @type {string|null} ID del alumno pendiente de eliminar. */
let idAEliminar = null;

// ============================================================
// INICIALIZACION DE OPCIONES
// ============================================================

/** @type {string} HTML de opciones para el selector de curso. */
let opcionesCurso = "";
for (const curso of CURSOS) {
  opcionesCurso += `<option value="${curso}">${curso}</option>`;
}

/** @type {string} HTML de opciones para el selector de grupo. */
let opcionesGrupo = "";
for (const grupo of GRUPOS) {
  opcionesGrupo += `<option value="${grupo}">Grupo ${grupo}</option>`;
}

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

 root.innerHTML = `
  <div class="containerGestion">
    <h1>Gestionar Alumnos</h1>
    <a href="crearAlumno.html">Añadir alumno</a>
    <div class="card card--buscador">
      <div class="card-body">
        <h2 class="card-title">Buscar Alumno</h2>
        ${añadirSelectsBusquedaAlumno()}
        <div id="inputContainer">
          <input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el ID">
        </div>
        <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
      </div>
    </div>
    <div id="resultado"></div>
    <hr>
    <h2>Lista de Alumnos</h2>
    ${añadirTablaAlumno()}
  </div>
`;

  cargarTodos();

  /** Delegacion de eventos sobre el root: detecta el boton eliminar de cada fila. */
  document.getElementById("root").addEventListener("click", function (evento) {
    if (evento.target.classList.contains("btnEliminar")) {
      idAEliminar = evento.target.getAttribute("data-id");
      document.getElementById("modalTexto").textContent =
        "¿Estas seguro de que quieres eliminar al alumno " + idAEliminar + "?";
      document.getElementById("modalConfirmar").classList.add("activo");
    }
  });

  /** Confirmacion del modal: ejecuta la eliminacion y muestra el mensaje de exito. */
  document
    .getElementById("btnConfirmarSi")
    .addEventListener("click", async function () {
      document.getElementById("modalConfirmar").classList.remove("activo");
      await eliminarAlumno(idAEliminar);
      const eliminado = idAEliminar;
      idAEliminar = null;
      document.getElementById("resultado").innerHTML =
        `<p class="mensaje--exito">Alumno con id ${eliminado} eliminado con exito</p>`;
    });

  /** Confirmacion negativa del modal: cancela la operacion y muestra el mensaje de cancelacion. */
  document
    .getElementById("btnConfirmarNo")
    .addEventListener("click", function () {
      document.getElementById("modalConfirmar").classList.remove("activo");
      idAEliminar = null;
      document.getElementById("resultado").innerHTML =
        `<p class="mensaje--error">Operacion cancelada</p>`;
    });

  /** Cambio de tipo de busqueda: reemplaza el input segun la seleccion. */
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
        // Curso+grupo permite construir el codigo de aula, ej: "1ºA"
        container.innerHTML = `
          <select id="inputCurso" class="form-select">${opcionesCurso}</select>
          <select id="inputGrupo" class="form-select">${opcionesGrupo}</select>`;
      }
    });

  /** Input de busqueda por nombre: lanza la busqueda en tiempo real al escribir. */
  document.getElementById("inputContainer").addEventListener("input", function (evento) {
    if (document.getElementById("tipoBusqueda").value === "nombre") {
      const valor = evento.target.value.trim();
      if (valor.length === 0) {
        // Si el campo se vacia limpia el resultado
        document.getElementById("resultado").innerHTML = "";
      } else if (valor.length >= 2) {
        buscarPorNombre(valor);
      }
    }
  });

  /** Boton buscar: redirige al metodo de busqueda segun el tipo. */
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
      // El codigo del aula se forma concatenando curso y grupo, ej: "1º" + "A" = "1ºA"
      buscarPorCodigoAula(curso + grupo);
    }
  });
});

// ============================================================
// FUNCIONES
// ============================================================

/**
 * Carga todos los alumnos y los muestra en la tabla principal.
 *
 * @returns {Promise<void>}
 */
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

/**
 * Busca un alumno por su ID y muestra el resultado.
 *
 * @param {string} id - ID numerico del alumno.
 * @returns {Promise<void>}
 */
async function buscarPorId(id) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (!id) {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un ID</p>`;
  } else if (!REGEX.SOLO_NUMEROS.test(id)) {
    resultado.innerHTML = `<p class="mensaje--error">El ID solo puede contener numeros</p>`;
  } else {
    const respuesta = await AlumnoAPI.obtenerPorId(id);
    if (respuesta.datos) {
      resultado.innerHTML = crearTablaAlumnos(
        crearTarjetaAlumno(respuesta.datos),
      );
    } else {
      resultado.innerHTML = `<p class="mensaje--error">Alumno con id: ${id} no encontrado</p>`;
    }
  }
}

/**
 * Busca alumnos por nombre, o por nombre y apellido si se escriben dos palabras.
 *
 * @param {string} nombre - Nombre o nombre y apellido separados por espacio.
 * @returns {Promise<void>}
 */
async function buscarPorNombre(nombre) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (!nombre) {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un nombre</p>`;
  } else if (!REGEX.SOLO_LETRAS.test(nombre)) {
    resultado.innerHTML = `<p class="mensaje--error">El nombre solo puede contener letras</p>`;
  } else {
    const partes = nombre.trim().split(/\s+/);
    let respuesta;

    if (partes.length >= 2) {
      // Si hay dos palabras busca por nombre y apellido
      respuesta = await AlumnoAPI.obtenerPorNombreYApellido(
        partes[0],
        partes.slice(1).join(" "),
      );
    } else {
      // Con una sola palabra busca solo por nombre
      respuesta = await AlumnoAPI.obtenerPorNombre(partes[0]);
    }

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

/**
 * Busca alumnos por fecha de nacimiento y muestra los resultados.
 *
 * @param {string} fecha - Fecha en formato YYYY-MM-DD.
 * @returns {Promise<void>}
 */
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

/**
 * Busca alumnos por el codigo de aula y muestra los resultados.
 *
 * @param {string} codigoAula - Codigo del aula, ej {@code "1ºA"}.
 * @returns {Promise<void>}
 */
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

/**
 * Elimina un alumno por ID y recarga la lista principal.
 *
 * @param {string} id - ID del alumno a eliminar.
 * @returns {Promise<void>}
 */
async function eliminarAlumno(id) {
  const respuesta = await AlumnoAPI.eliminar(id);
  if (respuesta.datos) {
    cargarTodos();
  } else {
    document.getElementById("listaAlumnos").innerHTML +=
      `<p class="mensaje--error">Error al eliminar</p>`;
  }
}

/**
 * Genera el HTML de una tabla de alumnos con las filas proporcionadas.
 *
 * @param {string} filas - HTML de las filas {@code <tr>} a insertar.
 * @returns {string} HTML completo de la tabla.
 */
function crearTablaAlumnos(filas) {
  return `
        <table>
            <thead>
                <tr>
                    <th>Codigo</th>
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

function añadirTablaAlumno(){
  return `
   <table>
            <thead>
                <tr>
                    <th>Codigo</th>
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
  `
}

function añadirSelectsBusquedaAlumno (){

  return `
   <select id="tipoBusqueda" class="form-select">
              <option value="id">Por ID</option>
              <option value="nombre">Por Nombre</option>
              <option value="fecha">Por Fecha de Nacimiento</option>
              <option value="curso">Por Curso y Grupo</option>
            </select>
  
  `
}

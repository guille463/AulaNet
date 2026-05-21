/**
 * Pagina de gestion del listado de profesores.
 *
 * <p>Permite buscar profesores por ID, nombre, email o especialidad,
 * asi como eliminar registros mediante una confirmacion.</p>
 *
 * @module menuProfesor
 * @see {@link ProfesorAPI}
 * @see {@link crearTarjetaProfesor}
 */

import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaProfesor } from "../components/profesorComponente.js";
import { ESPECIALIDADES, REGEX } from "../utils/constantes.js";
import { AulaAPI } from "../api/aulaApi.js";

// ============================================================
// VARIABLES
// ============================================================

/** @type {string|null} ID del profesor pendiente de eliminar. */
let idAEliminar = null;

/** @type {Map<string, string} Mapa de profesorId a codigo de aula para detectar tutores */
const tutoresPorAula = new Map();

// ============================================================
// INICIALIZACION DE OPCIONES
// ============================================================

/** @type {string} HTML de opciones para el selector de especialidad. */
let opcionesEspecialidad = "";
for (const especialidad of ESPECIALIDADES) {
  opcionesEspecialidad += `<option value="${especialidad.valor}">${especialidad.etiqueta}</option>`;
}

// ============================================================
// EVENTO PRINCIPAL
// ============================================================

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
    <div class="containerGestion">
        <h1>Gestionar Profesores</h1>
        <a href="crearProfesor.html"> Añadir Docente</a>
        <div class="card card--buscador">
          <div class="card-body">
            <h2 class="card-title">Buscar Docente</h2>
          ${AñadirselectsBusquedaProfesor()}
            <div id="inputContainer">
              <input type="text" id="inputBusqueda" class="form-control" placeholder="">
            </div>
            <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
          </div>
        </div>
        <div id="resultado"></div>
        <hr>
        <h2>Lista de Profesores</h2>
        ${AñadirTablasDeProfesores()}   
    </div>
`;

  cargarTodos();

  await cargarMapaTutores();

  // ============================================================
  // LITENER
  // ============================================================

  /** Detecta el boton eliminar de cada fila. */
  document.getElementById("root").addEventListener("click", function (evento) {
    if (evento.target.classList.contains("btnEliminar")) {
      idAEliminar = evento.target.getAttribute("data-id");
      if (tutoresPorAula.has(idAEliminar)) {
        document.getElementById("resultado").innerHTML =
          `<p class="mensaje--error"> No se puede eliminar al profesor porque es tutor del aula ${tutoresPorAula.get(idAEliminar)}</p>`;
        idAEliminar = null;
      } else {
        document.getElementById("modalTexto").textContent =
          "¿Estas seguro de que quieres eliminar al profesor con id" +
          idAEliminar +
          " ?";
        document.getElementById("modalConfirmar").classList.add("activo");
      }
    }
  });

  // ============================================================
  // LISTENER
  // ============================================================

  /** Confirmacion del modal, ejecuta la eliminacion y muestra el mensaje de exito. */
  document
    .getElementById("btnConfirmarSi")
    .addEventListener("click", async function () {
      document.getElementById("modalConfirmar").classList.remove("activo");
      const idEliminado = idAEliminar;
      await eliminarProfesor(idEliminado);
      idAEliminar = null;
      document.getElementById("resultado").innerHTML =
        `<p class="mensaje--exito">Profesor con id: ${idEliminado} eliminado con exito</p>`;
    });

  /** Confirmacion negativa del modal, cancela la operacion y muestra el mensaje de cancelacion. */
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
      } else if (tipo === "email") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el email">`;
      } else if (tipo === "especialidad") {
        container.innerHTML = `<select id="inputBusqueda" class="form-select">${opcionesEspecialidad}</select>`;
      }
    });

  /** Input de busqueda por nombre: lanza la busqueda en tiempo real al escribir. */
  document
    .getElementById("inputContainer")
    .addEventListener("input", function (evento) {
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
    } else if (tipo === "email") {
      const valor = document.getElementById("inputBusqueda").value.trim();
      buscarPorEmail(valor);
    } else if (tipo === "especialidad") {
      const valor = document.getElementById("inputBusqueda").value;
      buscarPorEspecialidad(valor);
    }
  });
});

// ============================================================
// FUNCIONES ASYNC
// ============================================================

/**
 * Carga todos los profesores y los muestra en la tabla principal.
 *
 * @returns {Promise<void>}
 */
async function cargarTodos() {
  const listaProfesores = document.getElementById("listaProfesores");
  listaProfesores.innerHTML = "";
  const resultado = await ProfesorAPI.obtenerTodos();
  if (resultado.datos && resultado.datos.length > 0) {
    let html = "";
    for (const profesor of resultado.datos) {
      html += crearTarjetaProfesor(profesor);
    }
    listaProfesores.innerHTML = html;
  } else {
    listaProfesores.innerHTML = `<tr><td colspan="7" class="mensaje--error">No hay profesores</td></tr>`;
  }
}

/**
 * Busca un profesor por su ID y muestra el resultado.
 *
 * @param {string} id - ID numerico del profesor.
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
    const respuesta = await ProfesorAPI.obtenerPorId(id);
    if (respuesta.datos) {
      resultado.innerHTML = crearTablaProfesores(
        crearTarjetaProfesor(respuesta.datos),
      );
    } else {
      resultado.innerHTML = `<p class="mensaje--error">Profesor con id: ${id} no encontrado</p>`;
    }
  }
}

/**
 * Busca profesores por nombre, o por nombre y apellido si se escriben dos palabras.
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
      respuesta = await ProfesorAPI.obtenerPorNombreYApellido(
        partes[0],
        partes.slice(1).join(" "),
      );
    } else {
      // Con una sola palabra busca solo por nombre
      respuesta = await ProfesorAPI.obtenerPorNombre(partes[0]);
    }

    if (respuesta.datos && respuesta.datos.length > 0) {
      let html = "";
      for (const profesor of respuesta.datos) {
        html += crearTarjetaProfesor(profesor);
      }
      resultado.innerHTML = crearTablaProfesores(html);
    } else {
      resultado.innerHTML = `<p class="mensaje--error">No se encontraron profesores con nombre: ${nombre}</p>`;
    }
  }
}

/**
 * Busca un profesor por email y muestra el resultado.
 *
 * @param {string} email - Email a buscar.
 * @returns {Promise<void>}
 */
async function buscarPorEmail(email) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (!email) {
    resultado.innerHTML = `<p class="mensaje--error">Introduce un email</p>`;
  } else if (!REGEX.EMAIL.test(email)) {
    resultado.innerHTML = `<p class="mensaje--error">El formato del email no es valido</p>`;
  } else {
    const respuesta = await ProfesorAPI.obtenerPorEmail(email);
    if (respuesta.datos) {
      resultado.innerHTML = crearTablaProfesores(
        crearTarjetaProfesor(respuesta.datos),
      );
    } else {
      resultado.innerHTML = `<p class="mensaje--error">Profesor con email: ${email} no encontrado</p>`;
    }
  }
}

/**
 * Busca profesores por especialidad y muestra los resultados.
 *
 * @param {string} especialidad - Valor de la especialidad seleccionada.
 * @returns {Promise<void>}
 */
async function buscarPorEspecialidad(especialidad) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (especialidad) {
    const respuesta = await ProfesorAPI.obtenerPorEspecialidad(especialidad);
    if (respuesta.datos && respuesta.datos.length > 0) {
      let html = "";
      for (const profesor of respuesta.datos) {
        html += crearTarjetaProfesor(profesor);
      }
      resultado.innerHTML = crearTablaProfesores(html);
    } else {
      resultado.innerHTML = `<p class="mensaje--error">No se encontraron profesores de especialidad: ${especialidad}</p>`;
    }
  }
}

/**
 * Elimina un profesor por ID y recarga la lista principal.
 *
 * @param {string} id - ID del profesor a eliminar.
 * @returns {Promise<void>}
 */
async function eliminarProfesor(id) {
  const respuesta = await ProfesorAPI.eliminar(id);
  if (respuesta.datos) {
    cargarTodos();
  } else {
    document.getElementById("listaProfesores").innerHTML +=
      `<p class="mensaje--error">Error al eliminar</p>`;
  }
}

async function cargarMapaTutores() {
  try {
    const respuesta = await AulaAPI.obtenerTodos();
    if (respuesta.datos) {
      for (const aula of respuesta.datos) {
        if (aula.tutor) {
          tutoresPorAula.set(String(aula.tutor.id), aula.codigo);
        }
      }
    }
  } catch (error) {
    console.error("Error al cargar el mapa de tutores");
  }
}

// ============================================================
// FINCIONES
// ============================================================
/**
 * Genera el HTML de una tabla de profesores con las filas proporcionadas.
 *
 * @param {string} filas - HTML de las filas {@code <tr>} a insertar.
 * @returns {string} HTML completo de la tabla.
 */
function crearTablaProfesores(filas) {
  return `
        <table>
            <thead>
                <tr>
                    <th>Codigo</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Especialidad</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>${filas}</tbody>
        </table>
    `;
}

function AñadirselectsBusquedaProfesor() {
  return `
  <select id="tipoBusqueda" class="form-select">
              <option value="id">Por ID</option>
              <option value="nombre">Por Nombre</option>
              <option value="email">Por Email</option>
              <option value="especialidad">Por Especialidad</option>
            </select>  
  `;
}

function AñadirTablasDeProfesores() {
  return `
   <table>
            <thead>
                <tr>
                    <th>Codigo</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Especialidad</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="listaProfesores"></tbody>
        </table>
  `;
}

function mostrarResultados(datos) {
  let html = "";
  for (const profesor of datos) {
    html += crearTarjetaProfesor(profesor);
  }
  document.getElementById("resultado").innerHTML = crearTablaProfesores(html);
}

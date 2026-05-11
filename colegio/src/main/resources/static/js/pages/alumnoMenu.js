/**
 * 
 */
import {
  getAlumnos,
  getAlumnoPorId,
  getAlumnoPorNombre,
  getAlumnoPorEmail,
  getAlumnosPorCodigoAula,
} from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";

/**
 * 
 */
document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
      <div class="containerBuscarAlumno">
        <h1>Gestionar Alumnos </h1>

        <button class="btn btn-CargarTodos" id="btnCargarTodos">Cargar todos los alumnos</button>

       <div class="card">
       <div class="card-body">
                <h2 class="card-title">Buscar Alumno</h2>
                <select id="tipoBusqueda" class="form-select mb-2">
                    <option value="id">Por ID</option>
                    <option value="nombre">Por Nombre</option>
                    <option value="email">Por Email</option>
                    <option value="curso">Por Curso y Grupo</option>
                </select>
                <div id="inputContainer">
                    <input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">
                </div>
                <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
            </div>
       </div>
       
        <div id="resultado"></div>
    </div>
    `;

/**
 * 
 */
  document
    .getElementById("btnCargarTodos")
    .addEventListener("click", function () {
      const resultado = document.getElementById("resultado");
      if (resultado.innerHTML !== "") {
        resultado.innerHTML = "";
      } else {
        cargarTodos();
      }
    });

/**
 * 
 */
  document
    .getElementById("tipoBusqueda")
    .addEventListener("change", function () {
      const container = document.getElementById("inputContainer");
      const tipo = this.value;
      if (tipo === "id") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">`;
      } else if (tipo === "nombre") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el nombre">`;
      } else if (tipo === "email") {
        container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el email">`;
      } else if (tipo === "curso") {
        container.innerHTML = `
            <select id="inputCurso" class="form-select mb-2">
                <option value="1º">1º</option>
                <option value="2º">2º</option>
                <option value="3º">3º</option>
                <option value="4º">4º</option>
                <option value="5º">5º</option>
                <option value="6º">6º</option>
            </select>
            <select id="inputGrupo" class="form-select mb-2">
                <option value="A">Grupo A</option>
                <option value="B">Grupo B</option>
            </select>`;
      }
    });

/**
 * 
 */
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
    } else if (tipo === "curso") {
      const curso = document.getElementById("inputCurso").value;
      const grupo = document.getElementById("inputGrupo").value;
      buscarPorCodigoAula(curso + grupo);
    }
  });
});

/**
 * 
 */
async function cargarTodos() {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  const alumnos = await getAlumnos();
  if (alumnos && alumnos.length > 0) {
    let html = "";
    alumnos.forEach(function (alumno) {
      html += crearTarjetaAlumno(alumno);
    });
    resultado.innerHTML = html;
  } else {
    resultado.innerHTML = `<p class="error">No hay alumnos</p>`;
  }
}

/**
 * 
 */
async function buscarPorId(id) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (id) {
    const alumno = await getAlumnoPorId(id);
    if (alumno) {
      resultado.innerHTML = crearTarjetaAlumno(alumno);
    } else {
      resultado.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un ID</p>`;
  }
}

/**
 * 
 */
async function buscarPorNombre(nombre) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (nombre) {
    const alumnos = await getAlumnoPorNombre(nombre);
    if (alumnos && alumnos.length > 0) {
      let html = "";
      alumnos.forEach(function (alumno) {
        html += crearTarjetaAlumno(alumno);
      });
      resultado.innerHTML = html;
    } else {
      resultado.innerHTML = `<p class="error">No se encontraron alumnos</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un nombre</p>`;
  }
}

/**
 * 
 */
async function buscarPorEmail(email) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (email) {
    const alumno = await getAlumnoPorEmail(email);
    if (alumno) {
      resultado.innerHTML = crearTarjetaAlumno(alumno);
    } else {
      resultado.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un email</p>`;
  }
}

/**
 * 
 */
async function buscarPorCodigoAula(codigo) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  const alumnos = await getAlumnosPorCodigoAula(codigo);
  if (alumnos && alumnos.length > 0) {
    let html = "";
    alumnos.forEach(function (alumno) {
      html += crearTarjetaAlumno(alumno);
    });
    resultado.innerHTML = html;
  } else {
    resultado.innerHTML = `<p class="error">No se encontraron alumnos en esa aula</p>`;
  }
}
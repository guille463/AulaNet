/**
 * 
 */
import {
  getAlumnos,
  getAlumnoPorId,
  getAlumnoPorNombre,
  getAlumnoPorEmail,
  getAlumnosPorCurso,
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
                    <option value="curso">Por Curso</option>
                </select>
                <input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">
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
      const input = document.getElementById("inputBusqueda");
      const tipo = this.value;
      if (tipo === "id") input.placeholder = "Introduce el ID";
      if (tipo === "nombre") input.placeholder = "Introduce el nombre";
      if (tipo === "email") input.placeholder = "Introduce el email";
      if (tipo === "curso") input.placeholder = "Ej: 1ºA";
    });

/**
 * 
 */
  document.getElementById("btnBuscar").addEventListener("click", function () {
    const tipo = document.getElementById("tipoBusqueda").value;
    const valor = document.getElementById("inputBusqueda").value.trim();
    if (tipo === "id") buscarPorId(valor);
    if (tipo === "nombre") buscarPorNombre(valor);
    if (tipo === "email") buscarPorEmail(valor);
    if (tipo === "curso") buscarPorCurso(valor);
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
async function buscarPorCurso(curso) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (curso) {
    const alumnos = await getAlumnosPorCurso(curso);
    if (alumnos && alumnos.length > 0) {
      let html = "";
      alumnos.forEach(function (alumno) {
        html += crearTarjetaAlumno(alumno);
      });
      resultado.innerHTML = html;
    } else {
      resultado.innerHTML = `<p class="error">No se encontraron alumnos en ese curso</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un curso</p>`;
  }
}

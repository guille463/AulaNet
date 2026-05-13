import {ProfesorAPI} from "../api/profesorApi.js"; 
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaProfesor } from "../components/profesorComponente.js";

let idAEliminar = null;

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
      <div class="containerBuscarProfesor">
        <h1>Gestionar Profesores</h1>
        <a href="crearProfesor.html">➕ Añadir Docente</a>
        <div class="card">
          <div class="card-body">
            <h2 class="card-title">Buscar Docente</h2>
            <select id="tipoBusqueda" class="form-select mb-2">
              <option value="id">Por ID</option>
              <option value="nombre">Por Nombre</option>
              <option value="email">Por Email</option>
              <option value="especialidad">Por Especialidad</option>
            </select>
            <div id="inputContainer">
              <input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">
            </div>
            <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
          </div>
        </div>
        <div id="resultado"></div>
        <hr>
        <h2>Lista de Profesores</h2>
        <div id="listaProfesores"></div>
      </div>
    `;

    cargarTodos();

    document.getElementById("root").addEventListener("click", function (e) {
        if (e.target.classList.contains("btnEliminar")) {
            idAEliminar = e.target.getAttribute("data-id");
            document.getElementById("modalTexto").textContent = "¿Estás seguro de que quieres eliminar al profesor " + idAEliminar + "?";
            document.getElementById("modalConfirmar").style.display = "block";
        }
    });

    document.getElementById("btnConfirmarSi").addEventListener("click", async function () {
        document.getElementById("modalConfirmar").style.display = "none";
        await eliminarProfesor(idAEliminar);
        idAEliminar = null;
        document.getElementById("resultado").innerHTML = `<p>Profesor eliminado con éxito</p>`;
    });

    document.getElementById("btnConfirmarNo").addEventListener("click", function () {
        document.getElementById("modalConfirmar").style.display = "none";
        idAEliminar = null;
        document.getElementById("resultado").innerHTML = `<p>Operación cancelada</p>`;
    });

    document.getElementById("tipoBusqueda").addEventListener("change", function () {
        const container = document.getElementById("inputContainer");
        const tipo = this.value;
        if (tipo === "id") {
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el ID">`;
        } else if (tipo === "email") {
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el email">`;
        } else if (tipo === "especialidad") {
            container.innerHTML = `
                <select id="inputBusqueda" class="form-select mb-2">
                    <option value="GENERAL">General</option>
                    <option value="EDUCACION_FISICA">Educación Física</option>
                    <option value="INGLES">Inglés</option>
                    <option value="MUSICA">Música</option>
                    <option value="LOGOPEDA">Logopeda</option>
                    <option value="RELIGION">Religión</option>
                </select>`;
        } else if (tipo === "nombre") {

            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control mb-2" placeholder="Introduce el nombre">`;


            
        }
    });

    document.getElementById("btnBuscar").addEventListener("click", function () {
        const tipo = document.getElementById("tipoBusqueda").value;
        if (tipo === "id") {
            const valor = document.getElementById("inputBusqueda").value.trim();
            buscarPorId(valor);
        } else if (tipo === "email") {
            const valor = document.getElementById("inputBusqueda").value.trim();
            buscarPorEmail(valor);
        } else if (tipo === "especialidad") {
            const valor = document.getElementById("inputBusqueda").value;
            buscarPorEspecialidad(valor);
        } else if (tipo === "nombre"){
            const valor = document.getElementById("inputBusqueda").value.trim(); 
            buscarPorNombre(valor)

        }
    });
});

async function cargarTodos() {
    const listaProfesores = document.getElementById("listaProfesores");
    listaProfesores.innerHTML = "";
    const resultado = await ProfesorAPI.obtenerTodos();
    if (resultado.datos && resultado.datos.length > 0) {
        let html = "";
       for (const profesor of resultado.datos) {
            html += await crearTarjetaProfesor(profesor);
          }
        listaProfesores.innerHTML = html;
    } else {
        listaProfesores.innerHTML = `<p class="error">No hay profesores</p>`;
    }
}

async function buscarPorId(id) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    if (id) {
        const respuesta = await ProfesorAPI.obtenerPorId(id); 
        if (respuesta.datos) {
            resultado.innerHTML = crearTarjetaProfesor(respuesta.datos);
        } else {
            resultado.innerHTML = `<p class="error">Profesor no encontrado</p>`;
        }
    } else {
        resultado.innerHTML = `<p class="error">Introduce un ID</p>`;
    }
}

async function buscarPorNombre(nombre) {
  const resultado = document.getElementById("resultado");
  resultado.innerHTML = "";
  if (nombre) {
    const respuesta = await ProfesorAPI.obtenerPorNombre(nombre);
    if (respuesta.datos && respuesta.datos.length > 0) {
      let html = "";
      respuesta.datos.forEach(function (profesor) {
        html += crearTarjetaProfesor(profesor);
      });
      resultado.innerHTML = html;
    } else {
      resultado.innerHTML = `<p class="error">No se encontraron Profesores</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce un nombre</p>`;
  }
}

async function buscarPorEmail(email) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    if (email) {
        const respuesta = await ProfesorAPI.obtenerPorEmail(email);
        if (respuesta.datos) {
            resultado.innerHTML = crearTarjetaProfesor(respuesta.datos);
        } else {
            resultado.innerHTML = `<p class="error">Profesor no encontrado</p>`;
        }
    } else {
        resultado.innerHTML = `<p class="error">Introduce un email</p>`;
    }
}

async function buscarPorEspecialidad(especialidad) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    if (especialidad) {
        const respuesta = await ProfesorAPI.obtenerPorEspecialidad(especialidad);
        if (respuesta.datos && respuesta.datos.length > 0) {
            let html = "";
            respuesta.datos.forEach(function (profesor) {
                html += crearTarjetaProfesor(profesor);
            });
            resultado.innerHTML = html;
        } else {
            resultado.innerHTML = `<p class="error">No se encontraron profesores</p>`;
        }
    }
}

async function eliminarProfesor(id) {
    const respuesta = await ProfesorAPI.eliminar(id)
    if (respuesta.datos) {
        cargarTodos();
    } else {
        document.getElementById("listaProfesores").innerHTML += `<p class="error">Error al eliminar</p>`;
    }
}
import { ProfesorAPI } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaProfesor } from "../components/profesorComponente.js";
import { ESPECIALIDADES } from "../utils/constantes.js";

let idAEliminar = null;

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    let opcionesEspecialidad = "";
    for (const especialidad of ESPECIALIDADES) {
        opcionesEspecialidad += `<option value="${especialidad.valor}">${especialidad.etiqueta}</option>`;
    }

  root.innerHTML = `
    <div class="containerGestion">
        <h1>Gestionar Profesores</h1>
        <a href="crearProfesor.html">➕ Añadir Docente</a>
        <div class="card card--buscador">
          <div class="card-body">
            <h2 class="card-title">Buscar Docente</h2>
            <select id="tipoBusqueda" class="form-select">
              <option value="id">Por ID</option>
              <option value="nombre">Por Nombre</option>
              <option value="email">Por Email</option>
              <option value="especialidad">Por Especialidad</option>
            </select>
            <div id="inputContainer">
              <input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el ID">
            </div>
            <button class="btn btn-Buscar" id="btnBuscar">Buscar</button>
          </div>
        </div>
        <div id="resultado"></div>
        <hr>
        <h2>Lista de Profesores</h2>
        <table>
            <thead>
                <tr>
                    <th>Código</th>
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
    </div>
`;

    cargarTodos();

    document.getElementById("root").addEventListener("click", function (evento) {
        if (evento.target.classList.contains("btnEliminar")) {
            idAEliminar = evento.target.getAttribute("data-id");
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
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el ID">`;
        } else if (tipo === "nombre") {
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el nombre">`;
        } else if (tipo === "email") {
            container.innerHTML = `<input type="text" id="inputBusqueda" class="form-control" placeholder="Introduce el email">`;
        } else if (tipo === "especialidad") {
            container.innerHTML = `<select id="inputBusqueda" class="form-select">${opcionesEspecialidad}</select>`;
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
        } else if (tipo === "email") {
            const valor = document.getElementById("inputBusqueda").value.trim();
            buscarPorEmail(valor);
        } else if (tipo === "especialidad") {
            const valor = document.getElementById("inputBusqueda").value;
            buscarPorEspecialidad(valor);
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
            html += crearTarjetaProfesor(profesor);
        }
        listaProfesores.innerHTML = html;
    } else {
       listaProfesores.innerHTML = `<tr><td colspan="7" class="error">No hay profesores</td></tr>`;
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
            for (const profesor of respuesta.datos) {
                html += crearTarjetaProfesor(profesor);
            }
            resultado.innerHTML = html;
        } else {
            resultado.innerHTML = `<p class="error">No se encontraron profesores</p>`;
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
            for (const profesor of respuesta.datos) {
                html += crearTarjetaProfesor(profesor);
            }
            resultado.innerHTML = html;
        } else {
            resultado.innerHTML = `<p class="error">No se encontraron profesores</p>`;
        }
    }
}

async function eliminarProfesor(id) {
    const respuesta = await ProfesorAPI.eliminar(id);
    if (respuesta.datos) {
        cargarTodos();
    } else {
        document.getElementById("listaProfesores").innerHTML += `<p class="error">Error al eliminar</p>`;
    }
}
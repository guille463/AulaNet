import { getAlumnos, getAlumnoPorId, getAlumnoPorNombre, getAlumnoPorEmail, getAlumnosPorCurso } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
      <div class="containerBuscarAlumno">
        <h1>Alumnos</h1>

        <button class="btn btn-CargarTodos" id="btnCargarTodos">Cargar todos los alumnos</button>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Buscar por ID</h5>
                <input type="number" id="inputId" class="form-control" placeholder="Introduce el ID">
                <button class="btn btn-BuscarPorId" id="btnBuscarId">Buscar</button>
            </div>
            <div class="card">
                <h5 class="card-title">Buscar por Nombre</h5>
                <input type="text" id="inputNombre" class="form-control" placeholder="Introduce el nombre del alumno">
                <button class="btn btn-BuscarPorNombre" id="btnBuscarNombre">Buscar</button>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Buscar por Email</h5>
                <input type="text" id="inputEmail" class="form-control" placeholder="Introduce el email">
                <button class="btn btn-BuscarPorEmail" id="btnBuscarEmail">Buscar</button>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Buscar por Curso</h5>
                <input type="text" id="inputCurso" class="form-control" placeholder="Introduce el curso">
                <button class="btn btn-BuscarPorCurso" id="btnBuscarCurso">Buscar</button>
            </div>
        </div>

        <div id="resultado"></div>
    </div>
    `;

   document.getElementById("btnCargarTodos").addEventListener("click", function() {
    const resultado = document.getElementById("resultado");
    if (resultado.innerHTML !== "") {
        resultado.innerHTML = "";
    } else {
        cargarTodos();
    }
});

    document.getElementById("btnBuscarId").addEventListener("click", function() {
        buscarPorId(document.getElementById("inputId").value.trim());
    });

    document.getElementById("btnBuscarNombre").addEventListener("click", function() {
        buscarPorNombre(document.getElementById("inputNombre").value.trim());
    });

    document.getElementById("btnBuscarEmail").addEventListener("click", function() {
        buscarPorEmail(document.getElementById("inputEmail").value.trim());
    });

    document.getElementById("btnBuscarCurso").addEventListener("click", function() {
        buscarPorCurso(document.getElementById("inputCurso").value.trim());
    });
});

async function cargarTodos() {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    const alumnos = await getAlumnos();
    if (alumnos && alumnos.length > 0) {
        let html = "";
        alumnos.forEach(function(alumno) {
            html += crearTarjetaAlumno(alumno);
        });
        resultado.innerHTML = html;
    } else {
        resultado.innerHTML = `<p class="error">No hay alumnos</p>`;
    }
}

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

async function buscarPorNombre(nombre) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    if (nombre) {
        const alumnos = await getAlumnoPorNombre(nombre);
        if (alumnos && alumnos.length > 0) {
            let html = "";
            alumnos.forEach(function(alumno) {
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

async function buscarPorCurso(curso) {
    const resultado = document.getElementById("resultado");
    resultado.innerHTML = "";
    if (curso) {
        const alumnos = await getAlumnosPorCurso(curso);
        if (alumnos && alumnos.length > 0) {
            let html = "";
            alumnos.forEach(function(alumno) {
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
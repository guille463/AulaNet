import { getAlumnoPorId, getAlumnoPorNombre, getAlumnoPorEmail, getAlumnoPorCurso } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearTarjetaAlumno } from "../components/alumnoComponente.js";


document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
        <div class="containerBuscarAlumno">
            <h1>Buscar Alumno</h1>

            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Buscar por ID</h2>
                    <input type="number" id="inputId" class="form-control" placeholder="Introduce el ID">
                    <button class="btn btn-BuscarPorId" id="btnBuscarId">Buscar</button>
                </div>
                <div class="card">
                <h2 class="card-title">Buscar por Nombre</h2>
                <input type="text" id="inputNombre" class="form-control" placeholder="Introduce el nombre del alumno">
                <button class="btn btn-BuscarPorNombre" id="btnBuscarPorNombre">Buscar Por Nombre</button>
                </div>
            </div>
            <div class="card">
    <div class="card-body">
        <h2 class="card-title">Buscar por Email</h2>
        <input type="text" id="inputEmail" class="form-control" placeholder="Introduce el email">
        <button class="btn btn-BuscarPorEmail" id="btnBuscarPorEmail">Buscar</button>
    </div>
</div>
 <div class="card-body">
        <h2 class="card-title">Buscar por Curso</h2>
        <input type="text" id="inputCurso" class="form-control" placeholder="Introduce el curso">
        <button class="btn btn-BuscarPorCurso" id="btnBuscarPorCurso">Buscar</button>
    </div>
</div>

            <div id="resultado"></div>
        </div>
    `;

  document.getElementById("btnBuscarId").addEventListener("click", function () {
    buscarPorId(document.getElementById("inputId").value.trim());
  });

  document
    .getElementById("btnBuscarPorNombre")
    .addEventListener("click", function () {
      buscarAlumnoPorNombre(
        document.getElementById("inputNombre").value.trim(),
      );
    });

  document
    .getElementById("btnBuscarPorEmail")
    .addEventListener("click", function () {
      buscarPorEmail(document.getElementById("inputEmail").value.trim());
    });

    document.getElementById("btnBuscarPorCurso").addEventListener("click", function(){
        
    })
});



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

async function buscarAlumnoPorNombre(nombre) {
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
      resultado.innerHTML = `<p class="error">Alumno no encontrado</p>`;
    }
  } else {
    resultado.innerHTML = `<p class="error">Introduce el nombre del alumno</p>`;
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

async function  buscarAlumnosPorCurso(curso) {
    const resultado = document.getElementById("resultado")
    resultado.innerHTML=""; 
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
    


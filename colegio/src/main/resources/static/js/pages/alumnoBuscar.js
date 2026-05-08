import { getAlumnoPorId } from "../api/alumnoApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  root.innerHTML = `
        <div class="containerBuscarAlumno">
            <h1 class=>Buscar Alumno</h1>

            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Buscar por ID</h2>
                    <input type="number" id="inputId" class="form-control" placeholder="Introduce el ID">
                    <button class="btn btn-BuscarPorId" id="btnBuscarId">Buscar</button>
                </div>
            </div>

            <div id="resultado"></div>
        </div>
    `;

  function mostrarAlumno(alumno) {
    document.getElementById("resultado").innerHTML = `
        <div class="card">
            <div class="card-body">
                <h2>${alumno.codigo} - ${alumno.nombre} ${alumno.apellido}</h2>
                <p>Email: ${alumno.email}</p>
                <p>Curso: ${alumno.curso}</p>
                <p>Fecha de nacimiento: ${alumno.fechaNac}</p>
            </div>
        </div>
    `;
  }

  document.getElementById("btnBuscarId").addEventListener("click", async () => {
    const id = document.getElementById("inputId").value;
    if (!id) return;
    const alumno = await getAlumnoPorId(id);
    mostrarAlumno(alumno);
  });
});

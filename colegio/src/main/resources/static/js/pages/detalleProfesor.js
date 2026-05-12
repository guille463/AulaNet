import { getProfesorPorId } from "../api/profesorApi.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { getProfesorAsignaturaPorProfesorId } from "../api/profesorAsignaturaApi.js";

document.addEventListener("DOMContentLoaded", async () => {
  document.getElementById("navbar").innerHTML = crearBarraNavegacion();
  const root = document.getElementById("root");

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) {
    root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
    return;
  }

  const profesor = await getProfesorPorId(id);


  if (!profesor) {
    root.innerHTML = `<p class="error">Profesor no encontrado</p>`;
    return;
  }
const asignaturas = getProfesorAsignaturaPorProfesorId(id); 

let asignaturasHtml = ""; 
if (asignaturas && asignaturas.length){
    asignaturas.forEach(function(pa){
        asignaturasHtml += `
                <tr>
                    <td>${pa.asignaturas.nombre}</td>
                </tr>
            `;
    })
}

root.innerHTML = `
         <div class="containerDetalle">
            <h1>Detalle del profesor</h1>
            <div class="card">
                <div class="card-body">
                    <h2>${profesor.codigo}</h2>
                    <hr>
                    <p><strong>Nombre:</strong> ${profesor.nombre} ${profesor.apellido}</p>
                    <p><strong>Email:</strong> ${profesor.email}</p>
                </div>
            </div>
            <h2>Asignaturas</h2>
                    ${asignaturasHtml}
            <p id="mensaje"></p>
            <a href="alumnoMenu.html">Volver</a>
        </div>
    `;
});

 

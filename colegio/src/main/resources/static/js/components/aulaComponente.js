import { AulaAPI } from "../api/aulaApi.js";

export async function crearTarjetaAula(aula) {
  const numeroDeAlumnos = (await AulaAPI.calcularAlumnos(aula.id)).datos;

  return `
        <div class="card">
            <div class="card-body">
                <strong>${aula.codigo}</strong>
                <span>Capacidad: ${numeroDeAlumnos} / ${aula.capacidad}</span>
                <span>Tutor: ${aula.tutor ? aula.tutor.nombre + " " + aula.tutor.apellido : "Sin tutor"}</span>
                <a href="detalleAula.html?id=${aula.id}">Ver más</a>
            </div>
        </div>
    `;
}

import { AulaAPI } from "../api/aulaApi.js"


export async function crearTarjetaAula(aula) {
    const numeroDeAlumnos = (await AulaAPI.calcularAlumnos(aula.id)).datos; 


    const tutor = aula.tutor
    let tutorTexto;

    if (tutor) {
        tutorTexto = tutor.nombre + " " + tutor.apellido
    } else {
        tutorTexto = "Sin tutor";
    }

   

    return `
    <div class="card">
    <div class = "card-body">
    <strong> ${aula.codigo}</strong>
    <span>Capacidad: ${numeroDeAlumnos} / ${aula.capacidad}</span>
    <span> Tutor: ${aula.tutor}</span>
    <a href="detalleAula.html?id= ${aula.id}">Ver mas</a>
    </div>
    </div>
    `
}





export function crearTarjetaAula(aula) {
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
    <span>${aula.curso}</span>
    <span>${aula.grupo}</span>
    <span>Capacidad: ${aula.capacidd}</span>
    <span> Tutor: ${aula.tutor}</span>
    <a href="detalleAula.html?id= ${aula.id}">Ver mas</a>
    </div>
    </div>
    `


}
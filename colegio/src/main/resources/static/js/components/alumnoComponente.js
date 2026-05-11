export function crearTarjetaAlumno(alumno) {
    const aula = alumno.aula;
    const tutor = aula.tutor;
    let tutorTexto;
    if (tutor) {
        tutorTexto = tutor.nombre + " " + tutor.apellido;
    } else {
        tutorTexto = "Sin tutor";
    }
    return `
        <div class="card">
            <div class="card-body" style="display:flex; gap:20px;">
                <strong>${alumno.codigo}</strong>
                <span>${alumno.nombre} ${alumno.apellido}</span>
                <span>${alumno.email}</span>
                <span>${aula.curso}</span>
                <span>${aula.grupo}</span>
                <span>${tutorTexto}</span>
                <a href="detalleAlumno.html?id=${alumno.id}">👁</a>
                <a href="editarAlumno.html?id=${alumno.id}">✏️</a>
                <button class="btnEliminar" data-id="${alumno.id}">🗑️</button>
            </div>
        </div>
    `;
}
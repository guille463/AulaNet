export function crearTarjetaAlumno(alumno) {
    const aula = alumno.aula;

    return `
        <tr>
            <td><strong>${alumno.codigo}</strong></td>
            <td>${alumno.nombre} ${alumno.apellido}</td>
            <td>${alumno.fechaNacimiento}</td>
            <td>${aula.curso}</td>
            <td>${aula.grupo}</td>
            <td>${aula.tutor ? aula.tutor.nombre + " " + aula.tutor.apellido : "Sin tutor"}</td>
            <td><a href="detalleAlumno.html?id=${alumno.id}">👁</a></td>
            <td><a href="editarAlumno.html?id=${alumno.id}">✏️</a></td>
            <td><button class="btnEliminar" data-id="${alumno.id}">🗑️</button></td>
        </tr>
    `;
}
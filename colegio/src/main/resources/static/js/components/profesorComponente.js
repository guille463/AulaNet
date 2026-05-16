export function crearTarjetaProfesor(profesor) {
    return `
        <tr>
            <td><strong>${profesor.codigo}</strong></td>
            <td>${profesor.nombre} ${profesor.apellido}</td>
            <td>${profesor.email}</td>
            <td>${profesor.especialidad}</td>
            <td><a href="detalleProfesor.html?id=${profesor.id}">👁</a></td>
            <td><a href="editarProfesor.html?id=${profesor.id}">✏️</a></td>
            <td><button class="btnEliminar" data-id="${profesor.id}">🗑️</button></td>
        </tr>
    `;
}
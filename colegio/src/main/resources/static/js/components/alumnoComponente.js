export function crearTarjetaAlumno(alumno) {
    return `
        <div class="card">
            <div class="card-body">
                <h5>${alumno.codigo} - ${alumno.nombre} ${alumno.apellido}</h5>
                <p>Email: ${alumno.email}</p>
                <p>Curso: ${alumno.curso}</p>
                <p>Fecha de nacimiento: ${alumno.fechaNac}</p>
            </div>
        </div>
    `;
}
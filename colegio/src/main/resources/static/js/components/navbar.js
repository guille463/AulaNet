export function crearBarraNavegacion() {
    return `
        <nav class="navbar navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="index.html">Colegio</a>
                <a class="btn btn-AlumnosMenu" href="alumnoMenu.html">Alumnos</a>
                <a class="btn btn-ProfesoresMenu" href="profesorMenu.html">Profesores</a>
                <a class="btn btn-AulaMenu" href="aulaMenu.html">Aula</a>
                <a class="btn btn-AsignaturaMenu" href="asignaturaMenu.html">Asignaturas</a>

            </div>
        </nav>
    `;
}
export function crearBarraNavegacion() {
  return `
        <nav class="navbar">
            <div class="navbar__container">
              <a class="navbar__brand" href="index.html">
    <img src="js/media/imgs/Logotipo.png" alt="Colegio" class="navbar__logo">
</a>
                <ul class="navbar__menu">
                    <li class="navbar__item">
                        <a class="navbar__link" href="menuAlumno.html">Alumnos</a>
                        <ul class="navbar__dropdown">
                            <li><a href="menuAlumno.html">Ver todos</a></li>
                            <li><a href="crearAlumno.html">Crear alumno</a></li>
                        </ul>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link" href="menuProfesor.html">Profesores</a>
                        <ul class="navbar__dropdown">
                            <li><a href="menuProfesor.html">Ver todos</a></li>
                            <li><a href="crearProfesor.html">Crear profesor</a></li>
                        </ul>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link" href="menuAula.html">Aulas</a>
                        <ul class="navbar__dropdown">
                            <li><a href="menuAula.html">Ver todas</a></li>
                        </ul>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link" href="menuAsignatura.html">Asignaturas</a>
                        <ul class="navbar__dropdown">
                            <li><a href="menuAsignatura.html">Ver todas</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    `;
}

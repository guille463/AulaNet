export function crearTarjetaAsignatura(asignatura) {
  return `
        <div class="card">
            <div class="card-body" style="display:flex; gap:20px;">
                <strong>${asignatura.codigo}</strong>
                <span>${asignatura.nombre}</span>
                <span>${asignatura.curso}</span>
                <span>${asignatura.horasSemana}h/semana</span>
                <a href="detalleAsignatura.html?id=${asignatura.id}">👁</a>
            </div>
        </div>
    `;
}

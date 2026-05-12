export function crearTarjetaProfesor(profesor){
    const aula = profesor.aula; 
    return `
        <div class="card">
            <div class="card-body" style="display:flex; gap:20px;">
                <strong>${profesor.codigo}</strong>
                <span>${profesor.nombre} ${profesor.apellido}</span>
                <span>${profesor.email}</span>
                <a href="detalleProfesor.html?id=${profesor.id}">👁</a>
                <a href="editarProfesor.html?id=${profesor.id}">✏️</a>
                <button class="btnEliminar" data-id="${profesor.id}">🗑️</button>
            </div>
        </div>
    `;
    
}
import { fetchApi } from "../utils/apiUtils.js";

export const AulaAPI = {
    obtenerTodos: () => fetchApi('GET', '/aulas'), 
   
    obtenerPorCodigo: (codigo) => fetchApi('GET', `/aulas/codigo/${encodeURIComponent(codigo)}`),
    
    obtenerPorId: (id) => fetchApi('GET', `/aulas/${id}`),
    
    calcularAlumnos: (id) => fetchApi('GET', `/aulas/${id}/alumnos/count`),
    
    asignarTutor: (aulaId, profesorId) => fetchApi('PUT', `/aulas/${aulaId}/tutor/${profesorId}`),

    eliminarTutor: (aulaId) => fetchApi('DELETE', `/aulas/${aulaId}/tutor`)
};
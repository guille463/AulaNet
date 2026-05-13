import { BASE_URL } from "../config/config.js";
import { fetchApi } from "../utils/apiUtils.js";


export const ProfesorAPI = {
    obtenerTodos: () => fetchApi('GET', '/profesores'),
    
    obtenerPorId: (id) => fetchApi('GET', `/profesores/${id}`),

    obtenerPorNombre: (nombre) => fetchApi('GET', `/profesores/buscar/${nombre}`), 

    obtenerPorEmail: (email) => fetchApi ('GET', `/profesores/email/${email}`), 

    obtenerPorCodigoAula: (codigo) => fetchApi ('GET', `/profesores/aula/codigo/${encodeURIComponent(codigo)}`),

    obtenerPorEspecialidad: (especialidad)=> fetchApi('GET', `/profesores/especialidad/${especialidad}`),  

    crear: (profesor) => fetchApi ('POST', `/profesores`, profesor), 

    actualizar: (id, profesor)=> fetchApi('PUT', `/profesores/${id}`, profesor), 

    eliminar: (id) => fetchApi('DELETE', `/profesores/${id}`) 
}; 

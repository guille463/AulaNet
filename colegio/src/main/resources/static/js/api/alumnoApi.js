import { fetchApi } from "../utils/apiUtils.js";

export const AlumnoAPI = {
    obtenerTodos: () => fetchApi('GET', '/alumnos'),

    obtenerPorId: (id) => fetchApi('GET', `/alumnos/${id}`),

    obtenerPorNombre: (nombre) => fetchApi('GET', `/alumnos/buscar/${nombre}`),

    obtenerPorEmail: (email) => fetchApi('GET', `/alumnos/email/${email}`),

    obtenerPorCodigoAula: (codigo) => fetchApi('GET', `/alumnos/aula/codigo/${encodeURIComponent(codigo)}`),

    crear: (alumno) => fetchApi('POST', '/alumnos', alumno),

    actualizar: (id, alumno) => fetchApi('PUT', `/alumnos/${id}`, alumno),
    
    eliminar: (id) => fetchApi('DELETE', `/alumnos/${id}`)
};
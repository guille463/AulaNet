import { fetchApi } from "../utils/apiUtils.js";

export const AlumnoAPI = {
    obtenerTodos: () => fetchApi('GET', '/alumnos'),

    obtenerPorId: (id) => fetchApi('GET', `/alumnos/${id}`),

    obtenerPorNombre: (nombre) => fetchApi('GET', `/alumnos/buscar/${nombre}`),

    obtenerPorNombreYApellido: (nombre, apellido) => fetchApi('GET', `/alumnos/buscar/${nombre}/${apellido}`),

    obtenerPorFecha: (fecha) => fetchApi('GET', `/alumnos/fecha/${fecha}`),

    obtenerPorCodigoAula: (codigo) => fetchApi('GET', `/alumnos/aula/codigo/${encodeURIComponent(codigo)}`),

    obtenerPorAula: (aulaId) => fetchApi('GET', `/alumnos/aula/${aulaId}`),

    crear: (alumno) => fetchApi('POST', '/alumnos', alumno),

    actualizar: (id, alumno) => fetchApi('PUT', `/alumnos/${id}`, alumno),

    eliminar: (id) => fetchApi('DELETE', `/alumnos/${id}`)
};
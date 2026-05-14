import { fetchApi } from "../utils/apiUtils.js";

export const AlumnoAsignaturaAPI = {
    obtenerPorAlumno: (id) => fetchApi('GET', `/alumno-asignatura/alumno/${id}`),
    
    actualizarNota: (id, nota) => fetchApi('PUT', `/alumno-asignatura/${id}`, { nota: nota }),

    obtenerPorAsignatura: (id) => fetchApi('GET', `/alumno-asignatura/asignatura/${id}`)


};
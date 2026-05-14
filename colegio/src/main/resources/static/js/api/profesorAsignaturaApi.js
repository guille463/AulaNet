import { fetchApi } from "../utils/apiUtils.js";

export const ProfesorAsignaturaAPI = {
    obtenerPorProfesor: (id) => fetchApi('GET', `/profesor-asignatura/profesor/${id}`),

    obtenerPorAula: (aulaId) => fetchApi('GET', `/profesor-asignatura/aula/${aulaId}`),
    
    obtenerPorAsignatura: (id) => fetchApi('GET', `/profesor-asignatura/asignatura/${id}`)
}
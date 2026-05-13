import { fetchApi } from "../utils/apiUtils.js";

export const ProfesorAsignaturaAPI = {
    obtenerPorProfesor: (id) => fetchApi('GET', `/profesor-asignatura/profesor/${id}`)
}
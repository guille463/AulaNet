import { fetchApi } from "../utils/apiUtils.js";

export const AulaAPI = {
    obtenerTodos: () => fetchApi('GET', '/aulas'), 
    obtenerPorCodigo: (codigo) => fetchApi('GET', `/aulas/codigo/${encodeURIComponent(codigo)}`),
    obtenerPorCodigo: (id) => fetchApi('GET', `/aulas/${id}`)
};
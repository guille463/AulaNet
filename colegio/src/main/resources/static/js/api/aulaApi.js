import { fetchApi } from "../utils/apiUtils.js";

export const AulaAPI = {
    obtenerPorCodigo: (codigo) => fetchApi('GET', `/aulas/codigo/${encodeURIComponent(codigo)}`)
};
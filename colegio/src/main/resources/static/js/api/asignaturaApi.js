import { fetchApi } from "../utils/apiUtils.js";

export const AsignaturaAPI = {
  obtenerTodos: () => fetchApi("GET", "/asignaturas"),
  obtenerPorId: (id) => fetchApi("GET", `/asignaturas/${id}`),
  obtenerPorCurso: (curso) => fetchApi("GET", `/asignaturas/curso/${curso}`),
};

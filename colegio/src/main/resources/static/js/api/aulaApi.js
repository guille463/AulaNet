import { BASE_URL } from "../config/config.js";

const URL_AULAS = BASE_URL + "/aulas";

export async function getAulaPorCodigo(codigo) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_AULAS}/codigo/${encodeURIComponent(codigo)}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Aula no encontrada");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}
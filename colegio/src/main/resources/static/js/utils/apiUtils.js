import { BASE_URL } from "../config/config.js";

export async function fetchApi(metodo, ruta, cuerpo = null) {
    let datos = null;
    let error = null;
    try {
        const opciones = {};
        opciones.method = metodo;
        opciones.headers = { "Content-Type": "application/json" };

        if (cuerpo !== null) {
            opciones.body = JSON.stringify(cuerpo);
        }

        const respuesta = await fetch(BASE_URL + ruta, opciones);
        if (respuesta.ok) {
            if (metodo === "DELETE") {
                datos = true;
            } else {
                datos = await respuesta.json();
            }
        } else {
             error = await respuesta.json();
        }
    } catch (e) {
        console.error(e);
    }
    return { datos, error };
}
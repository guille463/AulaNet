import {BASE_URL} from "../config/config.js"; 

const URL_ALUMNOS = BASE_URL + "/alumnos"; 

export async function getAlumnos() {
    const respuesta = await fetch(URL_ALUMNOS); 
    return await respuesta.json(); 
    
}


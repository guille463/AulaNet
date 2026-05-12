import { BASE_URL } from "../config/config.js";

const URL_PROFESOR_ASIGNATURA = BASE_URL + "/profesor-asignatura"; 

export async function getProfesorAsignaturaPorProfesorId(id) {
    let datos = null; 
    try {
        const respuesta = await fetch (`${URL_PROFESOR_ASIGNATURA}/profesor/${id}`); 
        if(respuesta.ok){
            datos = await respuesta.json(); 
        } else{
            throw new Error ("Error al ver las asignaturas del profesor")
        }
    } catch (error) {
        
    }
    
}


    

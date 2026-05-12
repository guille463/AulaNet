import { BASE_URL } from "../config/config.js";

const URL_ALUMNO_ASIGNATURA = BASE_URL + "/alumno-asignatura"

export async function getAlumnoAsignaturaPorAlumno(id) {

    let datos = null
    try {
        const respuesta = await fetch(URL_ALUMNO_ASIGNATURA)
        if(respuesta.ok){
            datos = await respuesta.json()
        } else{
            throw new Error ("Error al obtener laa asignaturas del alumno")
        }
    } catch (error) {
        console.log(error)
        
    }
    return datos
    
}

export async function putNotaALumno(id, nota) {
    let datos = null
    try {
        const respuesta = await fetch (`${URL_ALUMNO_ASIGNATURA}/${id}`,{
            method: "PUT", 
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({nota: nota})
        }); 
        if(respuesta.ok){
            datos = await respuesta.json()
        } else{
            throw new Error("Error al actualizar la nota del alumno")
        }
    } catch (error) {
        console.log(error);
        
        
    }
    return datos
    
}
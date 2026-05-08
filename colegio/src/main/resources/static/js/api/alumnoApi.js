import {BASE_URL} from "../config/config.js"; 

const URL_ALUMNOS = BASE_URL + "/alumnos"; 

export async function getAlumnos() {
    let datos = null;
    try {
        const respuesta = await fetch(URL_ALUMNOS);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Error al obtener alumnos");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getAlumnoPorId(id) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/${id}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Alumno no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getAlumnoPorNombre(nombre) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/buscar/${nombre}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Alumno no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getAlumnoPorEmail(email) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/email/${email}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Alumno no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getAlumnosPorCurso(curso){
     console.log("Buscando por curso:", curso);
    let datos = null
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/curso/${encodeURIComponent(curso)}`)
        if(respuesta.ok){
            datos = await respuesta.json(); 
        } else{
            throw new Error("Alumno no encontrado")
        }
    } catch (error) {
        console.log(error)
        
    }
    return datos; 
}


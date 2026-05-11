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

export async function getAlumnosPorCodigoAula(codigo) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/aula/codigo/${encodeURIComponent(codigo)}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("No se encontraron alumnos");
        }
    } catch (error) {
        console.log(error);
    }
    return datos;
}

export async function putAlumno(id, alumno) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(alumno)
        });
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Error al actualizar alumno");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function deleteAlumno(id) {
    let datos = null; 

    try {
        const respuesta = await fetch(`${URL_ALUMNOS}/${id}`, {
            method: "DELETE",
        });
        if(respuesta.ok){
            let ok = true; 
        } else{
            throw new Error("Error al Eliminar alumno")
        }
    } catch (error) {
        console.log(error);
                
    }
            return datos; 

    
}
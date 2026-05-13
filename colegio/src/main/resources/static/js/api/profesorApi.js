import { BASE_URL } from "../config/config.js";

const URL_PROFESORES = BASE_URL + "/profesores"; 

export async function getProfesores() {
    let datos = null; 
    try {
        const respuesta = await fetch (URL_PROFESORES); 
        if(respuesta.ok){
            datos = await respuesta.json(); 
        } else{
            throw new Error ("Error al crgar la lista de profesores")
        }
    } catch (error) {
        console.error(error)
        
    }

    return datos; 
    
}

export async function getProfesorPorId(id) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/${id}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Profesor no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getProfesorPorNombre(nombre) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/buscar/${nombre}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Profesor no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getProfesorPorEmail(email) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/email/${email}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Profesor no encontrado");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function getProfesorPorCodigoAula(codigo) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/aula/codigo/${encodeURIComponent(codigo)}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("No se encontraron Porfesores");
        }
    } catch (error) {
        console.log(error);
    }
    return datos;
}

export async function putProfesor(id, profesor) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(profesor)
        });
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Error al actualizar profesor");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}

export async function deleteProfesor(id) {
    let ok = false;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/${id}`, {
            method: "DELETE"
        });
        if (respuesta.ok) {
            ok = true;
        } else {
            throw new Error("Error al eliminar alumno");
        }
    } catch (error) {
        console.error(error);
    }
    return ok;
}

export async function postProfesor(profesor) {
    let datos = null;
    let error = null;
    try {
        const respuesta = await fetch(URL_PROFESORES, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(profesor)
        });
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            error = await respuesta.text();
            console.log(error);
            
        }
    } catch (e) {
        console.error(e);
    }
    return { datos, error };
}

export async function getProfesorPorEspecialidad(especialidad) {
    let datos = null;
    try {
        const respuesta = await fetch(`${URL_PROFESORES}/especialidad/${especialidad}`);
        if (respuesta.ok) {
            datos = await respuesta.json();
        } else {
            throw new Error("Profesores no encontrados");
        }
    } catch (error) {
        console.error(error);
    }
    return datos;
}
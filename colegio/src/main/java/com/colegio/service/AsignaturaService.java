package com.colegio.service; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.entity.Asignatura;
import com.colegio.repository.AsignaturaRepository;
import com.colegio.util.Constantes;


@Service 
public class AsignaturaService{
    @Autowired
    AsignaturaRepository asignaturaRepository; 

    public List<Asignatura>listarAsignaturas(){
        return asignaturaRepository.findAll(); 
    }

    public Asignatura buscarAsignaturaPorId(Long id){
        return asignaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("La Asignatura con id: " + id + " no existe")); 

    }

    public Asignatura guardarAsignatura(Asignatura asignatura){
         Asignatura guardado = asignaturaRepository.save(asignatura);
    guardado.setCodigo(Constantes.PREFIJO_ASIG + guardado.getId());
    return asignaturaRepository.save(guardado);
    }

    public Asignatura actualizarAsignatura(Long id, Asignatura asignatura){
    Asignatura existente = buscarAsignaturaPorId(id); 
    existente.setNombre(asignatura.getNombre());
    existente.setDescripcion(asignatura.getDescripcion());
    existente.setCurso(asignatura.getCurso());
    existente.setCreditos(asignatura.getCreditos()); 
    existente.setHorasSemana(asignatura.getHorasSemana());
    return asignaturaRepository.save(existente);
    }

    public void borrarAsignatura(Long id) {
    asignaturaRepository.deleteById(id);
}

}
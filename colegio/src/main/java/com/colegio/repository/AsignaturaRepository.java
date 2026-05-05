package com.colegio.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.Asignatura;


@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>{
    
}
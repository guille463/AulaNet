package com.colegio.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.entity.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long>{
    
}
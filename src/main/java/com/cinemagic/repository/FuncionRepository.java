package com.cinemagic.repository;

import com.cinemagic.model.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

//Da los metodos CRUD para la base de datos
public interface FuncionRepository extends JpaRepository<Funcion, Long> {
    //sin consultas personalizadas
}
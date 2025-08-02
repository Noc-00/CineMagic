package com.cinemagic.repository;

import com.cinemagic.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;

//Da los metodos CRUD para la base de datos
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
    //JpaRepository incluye los metodos basicos
    //save, findById, findAll, deleteById...
}
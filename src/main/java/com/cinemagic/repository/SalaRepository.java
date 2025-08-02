package com.cinemagic.repository;

import com.cinemagic.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositoryo y consulta personalizada
public interface SalaRepository extends JpaRepository<Sala, Long> {

    //Evita duplicados de salas con el mismo nombre
    boolean existsByNombre(String nombre);
}
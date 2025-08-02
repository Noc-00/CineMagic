package com.cinemagic.repository;

import com.cinemagic.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Da los metodos CRUD para la base de datos
@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    //sin consultas personalizadas
}
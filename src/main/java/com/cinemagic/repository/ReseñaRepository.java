package com.cinemagic.repository;

import com.cinemagic.model.Reseña;
import com.cinemagic.model.Usuario;
import com.cinemagic.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Repositoryo y consulta personalizada
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {

    //Busca la reseña de alguien especifico
    //Devuelve un Optional porque puede o no existir
    Optional<Reseña> findByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);

    //evita duplicados
    boolean existsByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);

    //Busca por pelicula
    Object findByPelicula(Pelicula pelicula);
}
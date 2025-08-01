package com.cinemagic.repository;

import com.cinemagic.model.Reseña;
import com.cinemagic.model.Usuario;
import com.cinemagic.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    Optional<Reseña> findByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);
    boolean existsByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);
    Object findByPelicula(Pelicula pelicula);
}

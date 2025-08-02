package com.cinemagic.service;

import com.cinemagic.model.*;
import com.cinemagic.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

//Manejo de logica
@Service
@Transactional
public class ReseñaService {

    @Autowired
    ReseñaRepository reseñaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PeliculaRepository peliculaRepository;

    //Crea nueva reseña
    public Reseña crearReseña(Long usuarioId, Long peliculaId, Integer calificacion, String comentario) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        //Verifica que no haya reseñado la ¿pelicula antes
        if (reseñaRepository.existsByUsuarioAndPelicula(usuario, pelicula)) {
            throw new RuntimeException("Ya has reseñado esta película");
        }

        //Guarda la reseña
        Reseña reseña = new Reseña();
        reseña.setUsuario(usuario);
        reseña.setPelicula(pelicula);
        reseña.setCalificacion(calificacion);
        reseña.setComentario(comentario);
        reseña.setFecha(LocalDateTime.now());

        return reseñaRepository.save(reseña);
    }

    //Obtiene todas las reseñas
    public List<Reseña> obtenerTodas() {
        return reseñaRepository.findAll();
    }

    //Buscar por pelicula
    public List<Reseña> obtenerPorPelicula(Long peliculaId) {
        return reseñaRepository.findAll().stream()
                .filter(r -> r.getPelicula().getId().equals(peliculaId))
                .toList();
    }


    public ReseñaService(ReseñaRepository reseñaRepository,
                         UsuarioRepository usuarioRepository,
                         PeliculaRepository peliculaRepository) {
        this.reseñaRepository = reseñaRepository;
        this.usuarioRepository = usuarioRepository;
        this.peliculaRepository = peliculaRepository;
    }
}
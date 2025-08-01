package com.cinemagic.service;

import com.cinemagic.model.*;
import com.cinemagic.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReseñaService {

    private final ReseñaRepository reseñaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PeliculaRepository peliculaRepository;

    public ReseñaService(ReseñaRepository reseñaRepository,
                         UsuarioRepository usuarioRepository,
                         PeliculaRepository peliculaRepository) {
        this.reseñaRepository = reseñaRepository;
        this.usuarioRepository = usuarioRepository;
        this.peliculaRepository = peliculaRepository;
    }

    public Reseña crearReseña(Long usuarioId, Long peliculaId, Integer calificacion, String comentario) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        if (reseñaRepository.existsByUsuarioAndPelicula(usuario, pelicula)) {
            throw new RuntimeException("Ya has reseñado esta película");
        }

        Reseña reseña = new Reseña();
        reseña.setUsuario(usuario);
        reseña.setPelicula(pelicula);
        reseña.setCalificacion(calificacion);
        reseña.setComentario(comentario);
        reseña.setFecha(LocalDateTime.now());

        return reseñaRepository.save(reseña);
    }

    public List<Reseña> obtenerPorPelicula(Long peliculaId) {
        return reseñaRepository.findAll().stream()
                .filter(r -> r.getPelicula().getId().equals(peliculaId))
                .toList();
    }
}
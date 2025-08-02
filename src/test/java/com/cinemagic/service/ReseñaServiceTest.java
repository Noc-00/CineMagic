package com.cinemagic.service;

import com.cinemagic.model.*;
import com.cinemagic.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReseñaServiceTest {

    private ReseñaRepository reseñaRepository;
    private UsuarioRepository usuarioRepository;
    private PeliculaRepository peliculaRepository;
    private ReseñaService reseñaService;

    @BeforeEach
    void setUp() {
        reseñaRepository = mock(ReseñaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        peliculaRepository = mock(PeliculaRepository.class);
        reseñaService = new ReseñaService(reseñaRepository, usuarioRepository, peliculaRepository);
    }

    @Test
    void crearReseña() {
        Usuario usuario = new Usuario();
        Pelicula pelicula = new Pelicula();
        Reseña reseñaGuardada = new Reseña();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(peliculaRepository.findById(2L)).thenReturn(Optional.of(pelicula));
        when(reseñaRepository.existsByUsuarioAndPelicula(usuario, pelicula)).thenReturn(false);
        when(reseñaRepository.save(any(Reseña.class))).thenReturn(reseñaGuardada);

        Reseña resultado = reseñaService.crearReseña(1L, 2L, 5, "Excelente");

        assertNotNull(resultado);
        verify(reseñaRepository).save(any(Reseña.class));
    }

    @Test
    void Duplicado() {
        Usuario usuario = new Usuario();
        Pelicula pelicula = new Pelicula();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(peliculaRepository.findById(2L)).thenReturn(Optional.of(pelicula));
        when(reseñaRepository.existsByUsuarioAndPelicula(usuario, pelicula)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reseñaService.crearReseña(1L, 2L, 4, "Ya reseñada");
        });

        assertEquals("Ya has reseñado esta película", ex.getMessage());
    }

    @Test
    void UsuarioNoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reseñaService.crearReseña(1L, 2L, 3, "Comentario");
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void PeliculaNoEncontrada() {
        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(peliculaRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reseñaService.crearReseña(1L, 2L, 3, "Comentario");
        });

        assertEquals("Película no encontrada", ex.getMessage());
    }

    @Test
    void obtenerPorPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(1L);

        Reseña r1 = new Reseña();
        r1.setPelicula(pelicula);

        Reseña r2 = new Reseña();
        r2.setPelicula(pelicula);

        List<Reseña> lista = Arrays.asList(r1, r2);

        when(reseñaRepository.findAll()).thenReturn(lista);

        List<Reseña> resultado = reseñaService.obtenerPorPelicula(1L);

        assertEquals(2, resultado.size());
        verify(reseñaRepository).findAll();
    }
}
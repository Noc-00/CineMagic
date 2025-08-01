package com.cinemagic.service;

import com.cinemagic.model.Pelicula;
import com.cinemagic.repository.PeliculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PeliculaServiceTest {

        private PeliculaRepository peliculaRepository;
        private PeliculaService peliculaService;

    @BeforeEach
    void setUp() {
        peliculaRepository = mock(PeliculaRepository.class);
        peliculaService = new PeliculaService(peliculaRepository); // ← ahora sí
    }

    @Test
        void CrearPelicula() {
            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo("Inception");

            when(peliculaRepository.save(pelicula)).thenReturn(pelicula);

            Pelicula resultado = peliculaService.crearPelicula(pelicula);

            assertEquals("Inception", resultado.getTitulo());
            verify(peliculaRepository).save(pelicula);
        }

        @Test
        void ObtenerTodas() {
            List<Pelicula> lista = Arrays.asList(new Pelicula(), new Pelicula());
            when(peliculaRepository.findAll()).thenReturn(lista);

            List<Pelicula> resultado = peliculaService.obtenerTodas();

            assertEquals(2, resultado.size());
            verify(peliculaRepository).findAll();
        }

        @Test
        void ObtenerPorId() {
            Pelicula pelicula = new Pelicula();
            pelicula.setId(1L);

            when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

            Optional<Pelicula> resultado = peliculaService.obtenerPorId(1L);

            assertTrue(resultado.isPresent());
            assertEquals(1L, resultado.get().getId());
            verify(peliculaRepository).findById(1L);
        }

        @Test
        void EliminarPorId() {
            Long id = 1L;

            peliculaService.eliminarPelicula(id);

            verify(peliculaRepository).deleteById(id);
        }
    }

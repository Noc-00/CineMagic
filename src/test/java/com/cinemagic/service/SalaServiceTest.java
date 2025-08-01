package com.cinemagic.service;

import com.cinemagic.model.Sala;
import com.cinemagic.repository.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalaServiceTest {

    private SalaRepository salaRepository;
    private SalaService salaService;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        salaService = new SalaService();
        salaService.salaRepository = salaRepository;
    }

    @Test
    void crearSala() {
        Sala sala = new Sala();
        sala.setNombre("Sala 1");

        when(salaRepository.existsByNombre("Sala 1")).thenReturn(false);
        when(salaRepository.save(sala)).thenReturn(sala);

        Sala resultado = salaService.crearSala(sala);

        assertNotNull(resultado);
        verify(salaRepository).save(sala);
    }

    @Test
    void SalaDuplicada() {
        Sala sala = new Sala();
        sala.setNombre("Sala duplicada");

        when(salaRepository.existsByNombre("Sala duplicada")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            salaService.crearSala(sala);
        });

        assertEquals("Sala existente", ex.getMessage());
        verify(salaRepository, never()).save(any());
    }

    @Test
    void obtenerTodas() {
        List<Sala> lista = Arrays.asList(new Sala(), new Sala());
        when(salaRepository.findAll()).thenReturn(lista);

        List<Sala> resultado = salaService.obtenerTodas();

        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerPorId() {
        Sala sala = new Sala();
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));

        Optional<Sala> resultado = salaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(sala, resultado.get());
    }

    @Test
    void EliminarPorId() {
        salaService.eliminarSala(1L);
        verify(salaRepository).deleteById(1L);
    }
}
package com.cinemagic.service;

import com.cinemagic.model.Asiento;
import com.cinemagic.repository.AsientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsientoServiceTest {

    private AsientoRepository asientoRepository;
    private AsientoService asientoService;

    @BeforeEach
    void setUp() {
        asientoRepository = mock(AsientoRepository.class);
        asientoService = new AsientoService(asientoRepository);
    }

    @Test
    void CrearAsiento() {
        Asiento asiento = new Asiento();
        when(asientoRepository.save(asiento)).thenReturn(asiento);

        Asiento resultado = asientoService.crearAsiento(asiento);

        assertEquals(asiento, resultado);
        verify(asientoRepository, times(1)).save(asiento);
    }

    @Test
    void ObtenerTodos() {
        List<Asiento> lista = Arrays.asList(new Asiento(), new Asiento());
        when(asientoRepository.findAll()).thenReturn(lista);

        List<Asiento> resultado = asientoService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(asientoRepository, times(1)).findAll();
    }

    @Test
    void ObtenerPorId() {
        Asiento asiento = new Asiento();
        when(asientoRepository.findById(1L)).thenReturn(Optional.of(asiento));

        Optional<Asiento> resultado = asientoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(asiento, resultado.get());
    }

    @Test
    void EliminarAsiento() {
        asientoService.eliminarAsiento(1L);

        verify(asientoRepository, times(1)).deleteById(1L);
    }

    @Test
    void MarcarComoOcupado() {
        Asiento asiento = new Asiento();
        asiento.setDisponible(true);
        when(asientoRepository.findById(1L)).thenReturn(Optional.of(asiento));
        when(asientoRepository.save(any())).thenReturn(asiento);

        Asiento actualizado = asientoService.marcarComoOcupado(1L);

        assertFalse(actualizado.isDisponible());
        verify(asientoRepository).save(asiento);
    }

    @Test
    void NoEncontradoO() {
        when(asientoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                asientoService.marcarComoOcupado(1L)
        );

        assertEquals("Asiento no encontrado con ID: 1", ex.getMessage());
    }

    @Test
    void MarcarComoDisponible() {
        Asiento asiento = new Asiento();
        asiento.setDisponible(false);
        when(asientoRepository.findById(1L)).thenReturn(Optional.of(asiento));
        when(asientoRepository.save(any())).thenReturn(asiento);

        Asiento actualizado = asientoService.marcarComoDisponible(1L);

        assertTrue(actualizado.isDisponible());
        verify(asientoRepository).save(asiento);
    }

    @Test
    void AsientoNoEncontrado() {
        when(asientoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                asientoService.marcarComoDisponible(1L)
        );

        assertEquals("Asiento no encontrado con ID: 1", ex.getMessage());
    }
}
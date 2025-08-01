package com.cinemagic.service;

import com.cinemagic.model.Funcion;
import com.cinemagic.repository.FuncionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionServiceTest {

    private FuncionRepository funcionRepository;
    private FuncionService funcionService;

    @BeforeEach
    void setUp() {
        funcionRepository = mock(FuncionRepository.class);
        funcionService = new FuncionService();
        funcionService.funcionRepository = funcionRepository; // inyección directa
    }

    @Test
    void crearFuncion() {
        Funcion funcion = new Funcion();
        when(funcionRepository.save(funcion)).thenReturn(funcion);

        Funcion resultado = funcionService.crearFuncion(funcion);

        assertNotNull(resultado);
        verify(funcionRepository).save(funcion);
    }

    @Test
    void obtenerTodas() {
        List<Funcion> lista = Arrays.asList(new Funcion(), new Funcion());
        when(funcionRepository.findAll()).thenReturn(lista);

        List<Funcion> resultado = funcionService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(funcionRepository).findAll();
    }

    @Test
    void obtenerPorId() {
        Funcion funcion = new Funcion();
        when(funcionRepository.findById(1L)).thenReturn(Optional.of(funcion));

        Optional<Funcion> resultado = funcionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(funcion, resultado.get());
    }

    @Test
    void EliminarPorId() {
        funcionService.eliminarFuncion(1L);
        verify(funcionRepository).deleteById(1L);
    }
}
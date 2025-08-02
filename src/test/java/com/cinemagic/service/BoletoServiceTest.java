package com.cinemagic.service;

import com.cinemagic.model.*;
import com.cinemagic.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoletoServiceTest {

    private BoletoRepository boletoRepository;
    private UsuarioRepository usuarioRepository;
    private FuncionRepository funcionRepository;
    private AsientoRepository asientoRepository;

    private BoletoService boletoService;

    @BeforeEach
    void setUp() {
        boletoRepository = mock(BoletoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        funcionRepository = mock(FuncionRepository.class);
        asientoRepository = mock(AsientoRepository.class);

        boletoService = new BoletoService();
        boletoService.boletoRepository = boletoRepository;
        boletoService.usuarioRepository = usuarioRepository;
        boletoService.funcionRepository = funcionRepository;
        boletoService.asientoRepository = asientoRepository;
    }

    @Test
    void comprarBoleto() {
        Long usuarioId = 1L, funcionId = 2L, asientoId = 3L;
        double precio = 100.0;

        Usuario usuario = new Usuario();
        Funcion funcion = new Funcion();
        Asiento asiento = new Asiento();
        asiento.setDisponible(true);

        Boleto boletoEsperado = Boleto.builder()
                .usuario(usuario)
                .funcion(funcion)
                .asiento(asiento)
                .precio(precio)
                .fechaCompra(LocalDateTime.now())
                .build();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(funcionRepository.findById(funcionId)).thenReturn(Optional.of(funcion));
        when(asientoRepository.findById(asientoId)).thenReturn(Optional.of(asiento));
        when(asientoRepository.save(any(Asiento.class))).thenReturn(asiento);
        when(boletoRepository.save(any(Boleto.class))).thenReturn(boletoEsperado);

        Boleto resultado = boletoService.comprarBoleto(usuarioId, funcionId, asientoId, precio);

        assertNotNull(resultado);
        assertEquals(precio, resultado.getPrecio());
        verify(asientoRepository).save(asiento);
        verify(boletoRepository).save(any(Boleto.class));
    }

    @Test
    void AsientoNoDisponible() {
        Long usuarioId = 1L, funcionId = 2L, asientoId = 3L;
        double precio = 100.0;

        Usuario usuario = new Usuario();
        Funcion funcion = new Funcion();
        Asiento asiento = new Asiento();
        asiento.setDisponible(false);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(funcionRepository.findById(funcionId)).thenReturn(Optional.of(funcion));
        when(asientoRepository.findById(asientoId)).thenReturn(Optional.of(asiento));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            boletoService.comprarBoleto(usuarioId, funcionId, asientoId, precio);
        });

        assertEquals("El asiento ya está ocupado", ex.getMessage());
        verify(boletoRepository, never()).save(any(Boleto.class));
    }

    @Test
    void UsuarioNoExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            boletoService.comprarBoleto(1L, 2L, 3L, 100.0);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void FuncionNoExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(funcionRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            boletoService.comprarBoleto(1L, 2L, 3L, 100.0);
        });

        assertEquals("Función no encontrada", ex.getMessage());
    }

    @Test
    void AsientoNoExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(funcionRepository.findById(2L)).thenReturn(Optional.of(new Funcion()));
        when(asientoRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            boletoService.comprarBoleto(1L, 2L, 3L, 100.0);
        });

        assertEquals("Asiento no encontrado", ex.getMessage());
    }

    @Test
    void obtenerTodos() {
        when(boletoRepository.findAll()).thenReturn(List.of(new Boleto(), new Boleto()));
        List<Boleto> boletos = boletoService.obtenerTodos();
        assertEquals(2, boletos.size());
    }

    @Test
    void obtenerPorId() {
        Boleto boleto = new Boleto();
        when(boletoRepository.findById(1L)).thenReturn(Optional.of(boleto));
        Optional<Boleto> resultado = boletoService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void eliminarPorId() {
        boletoService.eliminar(1L);
        verify(boletoRepository).deleteById(1L);
    }

    @Test
    void obtenerResumenVentas() {
        var resumenMock = List.of(
                new com.cinemagic.dto.ResumenVentaDTO("Película A", 10L, 1500.0),
                new com.cinemagic.dto.ResumenVentaDTO("Película B", 5L, 750.0)
        );

        when(boletoRepository.obtenerResumenVentasPorPelicula()).thenReturn(resumenMock);

        var resultado = boletoService.obtenerResumenVentas();

        assertEquals(2, resultado.size());
        assertEquals("Película A", resultado.get(0).getNombrePelicula());
        assertEquals(10L, resultado.get(0).getTotalBoletosVendidos());
        assertEquals(1500.0, resultado.get(0).getTotalIngresos());

        verify(boletoRepository).obtenerResumenVentasPorPelicula();
    }
}
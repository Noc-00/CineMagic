package com.cinemagic.service;

import com.cinemagic.model.*;
import com.cinemagic.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.repository.BoletoRepository;

@Service
@Transactional
public class BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    AsientoRepository asientoRepository;

    public BoletoService() {
        this.boletoRepository = boletoRepository;
        this.usuarioRepository = usuarioRepository;
        this.funcionRepository = funcionRepository;
        this.asientoRepository = asientoRepository;
    }

    public Boleto comprarBoleto(Long usuarioId, Long funcionId, Long asientoId, double precio) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Funcion funcion = funcionRepository.findById(funcionId)
                .orElseThrow(() -> new RuntimeException("Función no encontrada"));

        Asiento asiento = asientoRepository.findById(asientoId)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if (!asiento.isDisponible()) {
            throw new RuntimeException("El asiento ya está ocupado");
        }

        // Marcar asiento como ocupado
        asiento.setDisponible(false);
        asientoRepository.save(asiento);

        Boleto boleto = Boleto.builder()
                .usuario(usuario)
                .funcion(funcion)
                .asiento(asiento)
                .precio(precio)
                .fechaCompra(LocalDateTime.now())
                .build();

        return boletoRepository.save(boleto);
    }

    public List<Boleto> obtenerTodos() {
        return (List<Boleto>) boletoRepository.findAll();
    }

    public Optional<Boleto> obtenerPorId(Long id) {
        return boletoRepository.findById(id);
    }

    public void eliminar(Long id) {
        boletoRepository.deleteById(id);
    }

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    // Método para obtener resumen de ventas
    public List<ResumenVentaDTO> obtenerResumenVentas() {
        return boletoRepository.obtenerResumenVentasPorPelicula();
    }

}

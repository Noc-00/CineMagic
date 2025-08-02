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

//Manejo de logica
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

    //Metodo para comprar un boleto
    //Verifica que usuario, funcion y asiento existan
    //Comprueba disponibilidad del asiento y lo marca como ocupado
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
        asiento.setDisponible(false);
        asientoRepository.save(asiento);

        //Guarda el boleto
        Boleto boleto = Boleto.builder()
                .usuario(usuario)
                .funcion(funcion)
                .asiento(asiento)
                .precio(precio)
                .fechaCompra(LocalDateTime.now())
                .build();
        return boletoRepository.save(boleto);
    }

    //Obtener lista de todos los boletos
    public List<Boleto> obtenerTodos() {
        return (List<Boleto>) boletoRepository.findAll();
    }

    //Busca por Id
    public Optional<Boleto> obtenerPorId(Long id) {
        return boletoRepository.findById(id);
    }

    //Elimina por Id
    public void eliminar(Long id) {
        boletoRepository.deleteById(id);
    }

    //Metodo para obtener resumen de ventas
    public List<ResumenVentaDTO> obtenerResumenVentas() {
        return boletoRepository.obtenerResumenVentasPorPelicula();
    }

    //Constructor de inyeccion de dependencias
    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }
    public BoletoService() {
        this.boletoRepository = boletoRepository;
        this.usuarioRepository = usuarioRepository;
        this.funcionRepository = funcionRepository;
        this.asientoRepository = asientoRepository;
    }
}
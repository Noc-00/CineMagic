package com.cinemagic.service;

import com.cinemagic.model.Asiento;
import com.cinemagic.repository.AsientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Manejo de logica
@Service
@Transactional //Asegura q las operaciones de BD se ejecuten en una transacción
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    //Guarda el asiento creaado
    public Asiento crearAsiento(Asiento asiento) {
        return asientoRepository.save(asiento);
    }

    //Obtener lista de todos los asientos
    public List<Asiento> obtenerTodos() {
        return asientoRepository.findAll();
    }

    //Obtener por el id
    public Optional<Asiento> obtenerPorId(Long id) {
        return asientoRepository.findById(id);
    }

    //eliminar asiento por id
    public void eliminarAsiento(Long id) {
        asientoRepository.deleteById(id);
    }

    //Marca un asiento como ocupado
    public Asiento marcarComoOcupado(Long id) {
        Optional<Asiento> asientoOpt = asientoRepository.findById(id);
        if (asientoOpt.isPresent()) {
            Asiento asiento = asientoOpt.get();
            asiento.setDisponible(false);
            return asientoRepository.save(asiento);
        }
        throw new RuntimeException("Asiento no encontrado con ID: " + id);
    }

    //Marca un asiento como disponible
    public Asiento marcarComoDisponible(Long id) {
        Optional<Asiento> asientoOpt = asientoRepository.findById(id);
        if (asientoOpt.isPresent()) {
            Asiento asiento = asientoOpt.get();
            asiento.setDisponible(true);
            return asientoRepository.save(asiento);
        }
        throw new RuntimeException("Asiento no encontrado con ID: " + id);
    }
    // Constructor para inyectar el repositorio
    public AsientoService(AsientoRepository asientoRepository) {
        this.asientoRepository = asientoRepository;
    }
}
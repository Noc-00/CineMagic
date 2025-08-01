package com.cinemagic.service;

import com.cinemagic.model.Asiento;
import com.cinemagic.repository.AsientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    public Asiento crearAsiento(Asiento asiento) {
        return asientoRepository.save(asiento);
    }

    public List<Asiento> obtenerTodos() {
        return asientoRepository.findAll();
    }

    public Optional<Asiento> obtenerPorId(Long id) {
        return asientoRepository.findById(id);
    }

    public void eliminarAsiento(Long id) {
        asientoRepository.deleteById(id);
    }

    public Asiento marcarComoOcupado(Long id) {
        Optional<Asiento> asientoOpt = asientoRepository.findById(id);
        if (asientoOpt.isPresent()) {
            Asiento asiento = asientoOpt.get();
            asiento.setDisponible(false);
            return asientoRepository.save(asiento);
        }
        throw new RuntimeException("Asiento no encontrado con ID: " + id);
    }

    public Asiento marcarComoDisponible(Long id) {
        Optional<Asiento> asientoOpt = asientoRepository.findById(id);
        if (asientoOpt.isPresent()) {
            Asiento asiento = asientoOpt.get();
            asiento.setDisponible(true);
            return asientoRepository.save(asiento);
        }
        throw new RuntimeException("Asiento no encontrado con ID: " + id);
    }

    public AsientoService(AsientoRepository asientoRepository) {
        this.asientoRepository = asientoRepository;
    }
}
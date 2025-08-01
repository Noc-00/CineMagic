package com.cinemagic.service;

import com.cinemagic.model.Sala;
import com.cinemagic.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    SalaRepository salaRepository;

    public Sala crearSala(Sala sala) {
        if (salaRepository.existsByNombre(sala.getNombre())) {
            throw new RuntimeException("Sala existente");
        }
        return salaRepository.save(sala);
    }

    public List<Sala> obtenerTodas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> obtenerPorId(Long id) {
        return salaRepository.findById(id);
    }

    public void eliminarSala(Long id) {
        salaRepository.deleteById(id);
    }
}

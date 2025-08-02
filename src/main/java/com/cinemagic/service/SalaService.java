package com.cinemagic.service;

import com.cinemagic.model.Sala;
import com.cinemagic.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Manejo de logica
@Service
public class SalaService {

    @Autowired
    SalaRepository salaRepository;

    //Crea una nueva sala si no esta duplicada
    public Sala crearSala(Sala sala) {
        if (salaRepository.existsByNombre(sala.getNombre())) {
            throw new RuntimeException("Sala existente");
        }
        return salaRepository.save(sala);
    }

    //Obtener todas las salas
    public List<Sala> obtenerTodas() {
        return salaRepository.findAll();
    }

    //Buscar por Id
    public Optional<Sala> obtenerPorId(Long id) {
        return salaRepository.findById(id);
    }

    //Eliminar por Id
    public void eliminarSala(Long id) {
        salaRepository.deleteById(id);
    }
}

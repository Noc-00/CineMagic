package com.cinemagic.service;

import com.cinemagic.model.Funcion;
import com.cinemagic.repository.FuncionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuncionService {

    @Autowired
    FuncionRepository funcionRepository;

    public Funcion crearFuncion(Funcion funcion) {
        return funcionRepository.save(funcion);
    }

    public List<Funcion> obtenerTodas() {
        return funcionRepository.findAll();
    }

    public Optional<Funcion> obtenerPorId(Long id) {
        return funcionRepository.findById(id);
    }

    public void eliminarFuncion(Long id) {
        funcionRepository.deleteById(id);
    }
}
package com.cinemagic.service;

import com.cinemagic.model.Funcion;
import com.cinemagic.repository.FuncionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Manejo de logica
@Service
@Transactional
public class FuncionService {

    @Autowired
    FuncionRepository funcionRepository;

    //Crea/Guarda la funcion en la BD
    public Funcion crearFuncion(Funcion funcion) {
        return funcionRepository.save(funcion);
    }

    //Lidta de todas las funciones
    public List<Funcion> obtenerTodas() {
        return funcionRepository.findAll();
    }

    //Buscar por Id
    public Optional<Funcion> obtenerPorId(Long id) {
        return funcionRepository.findById(id);
    }

    //Eliminar por Id
    public void eliminarFuncion(Long id) {
        funcionRepository.deleteById(id);
    }
}
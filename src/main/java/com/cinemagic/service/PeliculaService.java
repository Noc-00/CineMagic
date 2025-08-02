package com.cinemagic.service;

import com.cinemagic.model.Pelicula;
import com.cinemagic.repository.PeliculaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Manejo de logica
@Service
@Transactional
public class PeliculaService {

    @Autowired
    PeliculaRepository peliculaRepository;

    //Crea/Guarda una pelicula
    public Pelicula crearPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    //Obtener todas las peliculas
    public List<Pelicula> obtenerTodas() {
        return peliculaRepository.findAll();
    }

    //Buscar por Id
    public Optional<Pelicula> obtenerPorId(Long id) {
        return peliculaRepository.findById(id);
    }

    //Eliminar por Id
    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

    //Constructor de inyeccion de dependencias
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }
}
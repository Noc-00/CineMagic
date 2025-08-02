package com.cinemagic.controller;

import com.cinemagic.model.Pelicula;
import com.cinemagic.service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    //Peticion para crear la pelicula
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@Valid @RequestBody Pelicula pelicula) {
        Pelicula nuevaPelicula = peliculaService.crearPelicula(pelicula);
        return ResponseEntity.ok(nuevaPelicula);
    }

    //Peticion para obtener tosas las peliculas
    @GetMapping
    public ResponseEntity<List<Pelicula>> obtenerPeliculas() {
        List<Pelicula> peliculas = peliculaService.obtenerTodas();
        return ResponseEntity.ok(peliculas);
    }

    //Peticion para o¿buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPeliculaPorId(@PathVariable Long id) {
        Optional<Pelicula> pelicula = peliculaService.obtenerPorId(id);
        return pelicula.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Peticion para eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build();
    }
}
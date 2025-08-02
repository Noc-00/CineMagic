package com.cinemagic.controller;

import com.cinemagic.model.Sala;
import com.cinemagic.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    //Peticion para crear la sala
    @PostMapping
    public ResponseEntity<Sala> crearSala(@Valid @RequestBody Sala sala) {
        Sala nuevaSala = salaService.crearSala(sala);
        return ResponseEntity.ok(nuevaSala);
    }

    //Peticion para obtener todas las salas
    @GetMapping
    public ResponseEntity<List<Sala>> obtenerSalas() {
        List<Sala> salas = salaService.obtenerTodas();
        return ResponseEntity.ok(salas);
    }

    //Peticion para buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerSalaPorId(@PathVariable Long id) {
        Optional<Sala> sala = salaService.obtenerPorId(id);
        return sala.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Peticion para eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSala(@PathVariable Long id) {
        salaService.eliminarSala(id);
        return ResponseEntity.noContent().build();
    }
}
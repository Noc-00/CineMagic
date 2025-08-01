package com.cinemagic.controller;

import com.cinemagic.model.Sala;
import com.cinemagic.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<Sala> crearSala(@Valid @RequestBody Sala sala) {
        Sala nuevaSala = salaService.crearSala(sala);
        return ResponseEntity.ok(nuevaSala);
    }

    @GetMapping
    public ResponseEntity<List<Sala>> obtenerSalas() {
        List<Sala> salas = salaService.obtenerTodas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerSalaPorId(@PathVariable Long id) {
        Optional<Sala> sala = salaService.obtenerPorId(id);
        return sala.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSala(@PathVariable Long id) {
        salaService.eliminarSala(id);
        return ResponseEntity.noContent().build();
    }
}

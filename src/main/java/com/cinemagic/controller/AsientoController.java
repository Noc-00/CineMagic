package com.cinemagic.controller;

import com.cinemagic.model.Asiento;
import com.cinemagic.service.AsientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asientos")
public class AsientoController {

    @Autowired
    private AsientoService asientoService;

    @PostMapping
    public ResponseEntity<Asiento> crearAsiento(@Valid @RequestBody Asiento asiento) {
        Asiento nuevoAsiento = asientoService.crearAsiento(asiento);
        return ResponseEntity.ok(nuevoAsiento);
    }

    @GetMapping
    public ResponseEntity<List<Asiento>> obtenerAsientos() {
        List<Asiento> asientos = asientoService.obtenerTodos();
        return ResponseEntity.ok(asientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asiento> obtenerAsientoPorId(@PathVariable Long id) {
        Optional<Asiento> asiento = asientoService.obtenerPorId(id);
        return asiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsiento(@PathVariable Long id) {
        asientoService.eliminarAsiento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ocupar")
    public ResponseEntity<Asiento> marcarComoOcupado(@PathVariable Long id) {
        Asiento actualizado = asientoService.marcarComoOcupado(id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<Asiento> marcarComoDisponible(@PathVariable Long id) {
        Asiento actualizado = asientoService.marcarComoDisponible(id);
        return ResponseEntity.ok(actualizado);
    }
}
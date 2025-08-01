package com.cinemagic.controller;

import com.cinemagic.model.Boleto;
import com.cinemagic.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    @PostMapping
    public ResponseEntity<Boleto> comprarBoleto(
            @RequestParam Long usuarioId,
            @RequestParam Long funcionId,
            @RequestParam Long asientoId,
            @RequestParam double precio) {

        Boleto boleto = boletoService.comprarBoleto(usuarioId, funcionId, asientoId, precio);
        return ResponseEntity.ok(boleto);
    }

    @GetMapping
    public ResponseEntity<List<Boleto>> obtenerTodos() {
        return ResponseEntity.ok(boletoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleto> obtenerPorId(@PathVariable Long id) {
        return boletoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boletoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
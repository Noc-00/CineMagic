package com.cinemagic.controller;

import com.cinemagic.model.Boleto;
import com.cinemagic.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    //Peticion para comprar un boleto
    //Requiere usuario, funcion, asiento, precio
    @PostMapping
    public ResponseEntity<Boleto> comprarBoleto(
            @RequestParam Long usuarioId,
            @RequestParam Long funcionId,
            @RequestParam Long asientoId,
            @RequestParam double precio) {

        Boleto boleto = boletoService.comprarBoleto(usuarioId, funcionId, asientoId, precio);
        return ResponseEntity.ok(boleto);
    }

    //Peticion para obtener los boletos existentes
    @GetMapping
    public ResponseEntity<List<Boleto>> obtenerTodos() {
        return ResponseEntity.ok(boletoService.obtenerTodos());
    }

    //Peticion para buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Boleto> obtenerPorId(@PathVariable Long id) {
        return boletoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Peticion para eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boletoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
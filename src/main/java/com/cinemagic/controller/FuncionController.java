package com.cinemagic.controller;

import com.cinemagic.model.Funcion;
import com.cinemagic.service.FuncionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funciones")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    @PostMapping
    public ResponseEntity<Funcion> crearFuncion(@Valid @RequestBody Funcion funcion) {
        Funcion nuevaFuncion = funcionService.crearFuncion(funcion);
        return ResponseEntity.ok(nuevaFuncion);
    }

    @GetMapping
    public ResponseEntity<List<Funcion>> obtenerFunciones() {
        List<Funcion> funciones = funcionService.obtenerTodas();
        return ResponseEntity.ok(funciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcion> obtenerFuncionPorId(@PathVariable Long id) {
        Optional<Funcion> funcion = funcionService.obtenerPorId(id);
        return funcion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFuncion(@PathVariable Long id) {
        funcionService.eliminarFuncion(id);
        return ResponseEntity.noContent().build();
    }
}
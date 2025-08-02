package com.cinemagic.controller;

import com.cinemagic.dto.UsuarioDTO;
import com.cinemagic.model.Usuario;
import com.cinemagic.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Peticion para validar y crear un usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.ok(nuevoUsuario);
    }

    //Peticion para buscar usuarios por correo
    @GetMapping("/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = usuarioService.obtenerPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Peticion para obtener una lista de todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    //Peticion para eliminar por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
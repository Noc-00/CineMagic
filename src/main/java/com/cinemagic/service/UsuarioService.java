// com.cinemagic.service.UsuarioService.java
package com.cinemagic.service;

import com.cinemagic.dto.UsuarioDTO;
import com.cinemagic.model.Usuario;
import com.cinemagic.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());

        // Hashear la contraseña antes de guardarla
        String contraseñaPlano = usuarioDTO.getContraseña();
        String contraseñaHasheada = passwordEncoder.encode(contraseñaPlano);
        usuario.setContraseña(contraseñaHasheada);

        usuario.setRol(usuarioDTO.getRol());

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
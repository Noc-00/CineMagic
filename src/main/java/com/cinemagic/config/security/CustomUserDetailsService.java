package com.cinemagic.config.security;

import com.cinemagic.model.Usuario;
import com.cinemagic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

//Busca en la BD al usuario y crea un objeto que contenga toda la informacion necesaria
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodo que carga el usuario desde la BD
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Crea el objeto CustomUserDetails
        return new CustomUserDetails(usuario);
    }
}
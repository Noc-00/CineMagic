package com.cinemagic.service;

import com.cinemagic.dto.UsuarioDTO;
import com.cinemagic.model.Usuario;
import com.cinemagic.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        usuarioService = new UsuarioService();
        usuarioService.usuarioRepository = usuarioRepository;
        usuarioService.passwordEncoder = passwordEncoder;
    }

    @Test
    void crearUsuario() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setCorreo("juan@mail.com");
        dto.setContraseña("1234");
        dto.setRol("USER");

        when(passwordEncoder.encode("1234")).thenReturn("hashed1234");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario usuarioGuardado = usuarioService.crearUsuario(dto);

        assertNotNull(usuarioGuardado);
        assertEquals("Juan", usuarioGuardado.getNombre());
        assertEquals("juan@mail.com", usuarioGuardado.getCorreo());
        assertEquals("hashed1234", usuarioGuardado.getContraseña());
        assertEquals("USER", usuarioGuardado.getRol());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void obtenerTodos() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(new Usuario(), new Usuario()));

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertEquals(2, usuarios.size());
    }

    @Test
    void obtenerPorCorreo() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("a@a.com");

        when(usuarioRepository.findByCorreo("a@a.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerPorCorreo("a@a.com");

        assertTrue(resultado.isPresent());
        assertEquals("a@a.com", resultado.get().getCorreo());
    }

    @Test
    void eliminarPorId() {
        usuarioService.eliminarUsuario(7L);
        verify(usuarioRepository).deleteById(7L);
    }
}
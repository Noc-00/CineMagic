package com.cinemagic.controller;

import com.cinemagic.config.security.CustomUserDetails;
import com.cinemagic.config.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    //Los datos requeridos para el login
    public static class LoginRequest {
        public String correo;
        public String contraseña;
    }

    //Se guarda el login del usuario
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        //Se identifica con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.correo, loginRequest.contraseña)
        );

        //Se extrae la informacion del usuario
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        //Se genera token a partir del correo y rol 🥸
        String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getUsuario().getRol());

        //Token generado 🥳
        return token;
    }
}
package com.cinemagic.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//Esta clase sirve de filtro(intercepta las peticiones http y valida el token JWT)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Obttiene el header "Authorization" de la peticion HTTP
        final String authHeader = request.getHeader("Authorization");

        String username = null; //Guarda el usuario
        String jwt = null; //Guarda el token JWT

        //Valida que el header no sea null y que comience con "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            //Extrae el token
            jwt = authHeader.substring(7);

            //Valida que el token sea valido
            if (jwtUtil.validateToken(jwt)) {

                //Obtiene el nombre de usuario del token
                username = jwtUtil.getUsernameFromToken(jwt);
            }
        }

        //Caso:extrajo un usuario y no hay una autenticacion en el contexto actual de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga los detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valida de nuevo el token
            if (jwtUtil.validateToken(jwt)) {

                //Crea un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Asocia detalles extras de la petición al token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Y establece la autenticación en el contexto de seguridad de Spring 🫠
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Continua con el proceso de filtros
        filterChain.doFilter(request, response);
    }
}
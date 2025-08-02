package com.cinemagic.config;

import com.cinemagic.config.security.CustomUserDetailsService;
import com.cinemagic.config.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Clase que configura la seguridad de nuestra aplicacion
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                //Configuracion de los URL y sus permisos
                .authorizeHttpRequests(auth -> auth
                        // Registro y login permitido para cualquier rol
                        .requestMatchers("/auth/**").permitAll()

                        // Lectura pública de películas, funciones, etc.
                        .requestMatchers(HttpMethod.GET, "/peliculas/**", "/funciones/**", "/salas/**", "/asientos/**", "/reseñas/**").permitAll()

                        // POST reseñas para usuarios autenticados (roles ADMINISTRADOR o ESPECTADOR)
                        .requestMatchers(HttpMethod.POST, "/reseñas/**").hasAnyRole("ADMINISTRADOR", "ESPECTADOR")

                        // DELETE reseñas solo ADMINISTRADOR
                        .requestMatchers(HttpMethod.DELETE, "/reseñas/**").hasRole("ADMINISTRADOR")

                        // Resumen ventas solo ADMINISTRADOR
                        .requestMatchers("/api/resumen/**").hasRole("ADMINISTRADOR")

                        // Boletos solo ESPECTADOR(Lectura para ADMINISTRADOR)
                        .requestMatchers(HttpMethod.GET, "/boletos/**").hasAnyRole("ESPECTADOR", "ADMINISTRADOR")
                        .requestMatchers("/boletos/**").hasRole("ESPECTADOR")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // Aca configuramos que la sesion sea sin estado, porque usamos JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configuramos donde se verifica el usuario y contraseña
                .authenticationProvider(authenticationProvider())
                //Agregamos el filtro para validar los JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //Configuramos lo que usara nuestro servicio para cargar usuarios
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); //Obtener detalles del usuario
        authProvider.setPasswordEncoder(passwordEncoder()); //validar la contraseña
        return authProvider;
    }

    //Encriptamos aca las contraseñas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Obtenemos el gestor de autenticacion
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
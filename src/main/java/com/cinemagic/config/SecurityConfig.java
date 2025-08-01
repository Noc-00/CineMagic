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
                .authorizeHttpRequests(auth -> auth
                        // Permitimos /auth para login, registro
                        .requestMatchers("/auth/**").permitAll()

                        // Lectura pública de películas, funciones, etc.
                        .requestMatchers(HttpMethod.GET, "/peliculas/**", "/funciones/**", "/salas/**", "/asientos/**", "/reseñas/**").permitAll()

                        // POST reseñas para usuarios autenticados (roles ADMINISTRADOR o ESPECTADOR)
                        .requestMatchers(HttpMethod.POST, "/reseñas/**").hasAnyRole("ADMINISTRADOR", "ESPECTADOR")

                        // DELETE reseñas solo ADMINISTRADOR
                        .requestMatchers(HttpMethod.DELETE, "/reseñas/**").hasRole("ADMINISTRADOR")

                        // Resumen ventas solo ADMINISTRADOR
                        .requestMatchers("/api/resumen/**").hasRole("ADMINISTRADOR")

                        // Boletos solo ESPECTADOR
                        .requestMatchers("/boletos/**").hasRole("ESPECTADOR")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
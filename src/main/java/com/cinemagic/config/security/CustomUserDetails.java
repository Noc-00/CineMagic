package com.cinemagic.config.security;

import com.cinemagic.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

//Aqui se adapta la informacion de la entidad Usuario a UserDetails que usa SS
public class CustomUserDetails implements UserDetails {

    // Entidad que contiene los datos que necesitamos
    private Usuario usuario;

    //Constructor que recibe a Usuario para inicializar la clase
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    //Metodo para obtener el objeto original
    public Usuario getUsuario() {
        return usuario;
    }

    //Retornar los roles del usuario para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + usuario.getRol().toUpperCase());
    }

    //Retorna la contraseña del usuario para la autenticacion
    @Override
    public String getPassword() {
        return usuario.getContraseña();
    }

    // Retorna el correo para la autenticacion
    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    //Indica si la cuenta no ha expirado
    @Override
    public boolean isAccountNonExpired() { return true; }

    //Indica si la cuenta no ha esta bloqueada
    @Override
    public boolean isAccountNonLocked() { return true; }

    //Indica si las credenciales no han expirado
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    //Indica si esta habilitada la cuenta
    @Override
    public boolean isEnabled() { return true; }
}
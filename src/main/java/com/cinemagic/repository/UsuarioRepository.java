package com.cinemagic.repository;

import com.cinemagic.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Repositoryo y consulta
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Busca un usuario por su correo(opcional porque puede o no existir)
    Optional<Usuario> findByCorreo(String correo);
}
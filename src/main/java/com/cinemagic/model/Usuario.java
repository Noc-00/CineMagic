package com.cinemagic.model;

import jakarta.persistence.*;
import lombok.*;

//Incluye nombre, correo, contraseña, rol
@Entity
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    //Identificador unico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre de Usuario
    @Column(nullable = false, length = 100)
    private String nombre;

    //Correo del usuario(sirve para el login)
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    //Contraseña(para el login)
    @Column(nullable = false)
    private String contraseña;

    //Rol del usuario(ADMINISTRADOR o ESPECTADOR)
    //Al realizar el registro en el caso de los administradores de debe asignar manualmente en la base de datos
    @Column(nullable = false)
    private String rol;
}
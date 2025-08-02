package com.cinemagic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//Este transporta los datos de el usuario segun se necesiten
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    //Un requisito obligatorio, no puede estar vacio o manda un mensaje
    @NotBlank(message = "Este dato es obligatorio")
    private String nombre;

    //Un requisito obligatorio y valido (no duplicado o mal escrito)
    @Email(message = "correo inválido")
    @NotBlank(message = "Este dato es obligatorio")
    private String correo;

    //Un requisito obligatorio y de al menos 6 caracteres
    @NotBlank(message = "Este dato es obligatorio")
    @Size(min = 6, message = "Introduzca al menos 6 caracteres")
    private String contraseña;

    //Un requisito obligatorio, ADMINISTRADOR O ESPECTADOR
    @NotBlank(message = "Este dato es obligatorio")
    private String rol;
}
package com.cinemagic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    @NotBlank(message = "Este dato es obligatorio")
    private String nombre;

    @Email(message = "correo inválido")
    @NotBlank(message = "Este dato es obligatorio")
    private String correo;

    @NotBlank(message = "Este dato es obligatorio")
    @Size(min = 6, message = "Introduzca al menos 6 caracteres")
    private String contraseña;

    @NotBlank(message = "Este dato es obligatorio")
    private String rol;


}

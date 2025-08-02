package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//Incluye nombre y capacidad
@Entity
@Table(name = "salas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    // Identificador único
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la sala unico
    @NotBlank(message = "Este dato es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    // Capacidad máxima de asientos
    @NotNull(message = "Este dato es obligatorio")
    @Column(nullable = false)
    private Integer capacidad;
}
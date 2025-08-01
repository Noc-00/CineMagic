package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "salas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Este dato es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @NotNull(message = "Este dato es obligatorio")
    @Column(nullable = false)
    private Integer capacidad;
}

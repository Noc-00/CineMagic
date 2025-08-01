package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "peliculas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este dato es obligatorio")
    private String titulo;

    @NotNull(message = "Este dato es obligatorio")
    private String descripcion;

    @NotNull(message = "Este dato es obligatorio")
    private Integer duracion; // en minutos

    @NotNull(message = "Este dato es obligatorio")
    private String categoria;
}

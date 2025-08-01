package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "funciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este dato es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @NotNull(message = "Este dato es obligatorio")
    private LocalDateTime fechaHora;

    @NotNull(message = "Este dato es obligatorio")
    private double precio;

    @NotNull(message = "Este dato es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
}

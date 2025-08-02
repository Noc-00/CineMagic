package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

//Incluye película, sala, fecha y hora, precio
@Entity
@Table(name = "funciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcion {

    // Identificador unico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Pelicula a proyectar
    @NotNull(message = "Este dato es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    //Fecha y hora de funcion
    @NotNull(message = "Este dato es obligatorio")
    private LocalDateTime fechaHora;


    //Precio del boleto para
    @NotNull(message = "Este dato es obligatorio")
    private double precio;

    //Sala
    @NotNull(message = "Este dato es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
}
package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//Incluye título, descripción, duración y categoría.
@Entity
@Table(name = "peliculas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    //Identificador unico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Titulo de la pelicula
    @NotNull(message = "Este dato es obligatorio") //para que no permita si falta el dato
    private String titulo;

    //Sinopsis o de que trata
    @NotNull(message = "Este dato es obligatorio")
    private String descripcion;

    //Duracion(en minutos)
    @NotNull(message = "Este dato es obligatorio")
    private Integer duracion;

    //Categoria o genero de la pelicula
    @NotNull(message = "Este dato es obligatorio")
    private String categoria;
}
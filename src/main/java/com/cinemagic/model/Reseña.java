package com.cinemagic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.*;

//Incluye calificacion, comentario y fecha
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reseña {

    //Identificador unico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Usuario que escribe la contraseña
    @ManyToOne(optional = false)
    private Usuario usuario;

    //Pelicula a reseñar
    @ManyToOne(optional = false)
    private Pelicula pelicula;

    //calificacion para la pelicula
    @NotNull
    @Min(1)//minimo puntaje
    @Max(5)//maximo puntaje
    private Integer calificacion;

    //comentario de la reseña
    @Size(max = 500)//no mas de 500 caracteres
    private String comentario;

    //fecha de publicacion
    private LocalDateTime fecha;
}
package com.cinemagic.model;

import jakarta.persistence.*;
import lombok.*;

//Incluye fila, número, estado y sala
@Entity
@Table(name = "asientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asiento {

    // Identificador único
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de la fila(Letra🗣)
    @Column(nullable = false)
    private String fila;

    // Número de asiento dentro de la fila
    @Column(nullable = false)
    private int numero;

    // Sala donde está
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    // Indica si esta libre o ya lo ocuparon👍🏻
    @Column(nullable = false)
    private boolean disponible;
}
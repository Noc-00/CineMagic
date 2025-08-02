package com.cinemagic.model;

import jakarta.persistence.*;
import lombok.*;

//Incluye el asiento, precio y la fecha
@Entity
@Table(name = "boletos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleto {

    // Identificador único
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que compró el boleto
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Función a la que va
    @ManyToOne
    @JoinColumn(name = "funcion_id", nullable = false)
    private Funcion funcion;

    // Asiento elegido
    @ManyToOne
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

    // Precio del boleto
    @Column(nullable = false)
    private double precio;

    // Fecha y hora de la compra 🛒
    @Column(nullable = false)
    private java.time.LocalDateTime fechaCompra;
}
package com.cinemagic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boletos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)

    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "funcion_id", nullable = false)

    private Funcion funcion;

    @ManyToOne
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private java.time.LocalDateTime fechaCompra;
}

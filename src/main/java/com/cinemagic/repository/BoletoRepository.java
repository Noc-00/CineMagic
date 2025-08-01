package com.cinemagic.repository;

import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    @Query("SELECT new com.cinemagic.dto.ResumenVentaDTO(" +
            "b.funcion.pelicula.titulo, COUNT(b), SUM(b.precio)) " +
            "FROM Boleto b " +
            "GROUP BY b.funcion.pelicula.titulo")
    List<ResumenVentaDTO> obtenerResumenVentasPorPelicula();

}
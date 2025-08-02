package com.cinemagic.repository;

import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

//Repositorio y consulta personalizada para obtener el resumen de ventas segun la pelicula
public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    //Agrupa por título de película, cuenta boletos y suma precios
    @Query("SELECT new com.cinemagic.dto.ResumenVentaDTO(" +
            "b.funcion.pelicula.titulo, COUNT(b), SUM(b.precio)) " +
            "FROM Boleto b " + "GROUP BY b.funcion.pelicula.titulo")
    List<ResumenVentaDTO> obtenerResumenVentasPorPelicula();

}
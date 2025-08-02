package com.cinemagic.dto;

//Muestra el resumen de ventas, envia informacion resumida del backend a frontend
public class ResumenVentaDTO {

    private String nombrePelicula;
    private Long totalBoletosVendidos;
    private Double totalIngresos;

    //Constructor para tener valores calculados
    public ResumenVentaDTO(String nombrePelicula, Long totalBoletosVendidos, Double totalIngresos) {
        this.nombrePelicula = nombrePelicula;
        this.totalBoletosVendidos = totalBoletosVendidos;
        this.totalIngresos = totalIngresos;
    }

    //Getters y setters
    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public Long getTotalBoletosVendidos() {
        return totalBoletosVendidos;
    }

    public void setTotalBoletosVendidos(Long totalBoletosVendidos) {
        this.totalBoletosVendidos = totalBoletosVendidos;
    }

    public Double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }
}
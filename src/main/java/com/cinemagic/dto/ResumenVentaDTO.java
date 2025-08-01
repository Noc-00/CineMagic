package com.cinemagic.dto;

public class ResumenVentaDTO {

    private String nombrePelicula;
    private Long totalBoletosVendidos;
    private Double totalIngresos;

    public ResumenVentaDTO(String nombrePelicula, Long totalBoletosVendidos, Double totalIngresos) {
        this.nombrePelicula = nombrePelicula;
        this.totalBoletosVendidos = totalBoletosVendidos;
        this.totalIngresos = totalIngresos;
    }

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
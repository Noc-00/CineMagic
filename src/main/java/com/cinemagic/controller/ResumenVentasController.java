package com.cinemagic.controller;

import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.service.BoletoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//Expone los endpoints y  permite operaciones a traves de peticiones HTTP
@RestController
@RequestMapping("/api/resumen")
public class ResumenVentasController {

    private final BoletoService boletoService;

    //Inyectar las dependencias
    public ResumenVentasController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    //Peticion para obtener el resumen de ventas
    @GetMapping
    public List<ResumenVentaDTO> obtenerResumenVentas() {
        return boletoService.obtenerResumenVentas();
    }
}
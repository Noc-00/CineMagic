package com.cinemagic.controller;

import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.service.BoletoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/resumen")
public class ResumenVentasController {

    private final BoletoService boletoService;

    public ResumenVentasController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @GetMapping
    public List<ResumenVentaDTO> obtenerResumenVentas() {
        return boletoService.obtenerResumenVentas();
    }
}

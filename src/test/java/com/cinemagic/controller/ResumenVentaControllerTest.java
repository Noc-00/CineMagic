package com.cinemagic.controller;

import com.cinemagic.config.security.CustomUserDetailsService;
import com.cinemagic.config.security.JwtUtil;
import com.cinemagic.dto.ResumenVentaDTO;
import com.cinemagic.service.BoletoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResumenVentasController.class)
class ResumenVentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoletoService boletoService;

    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMINISTRADOR"})
    void obtenerResumenVentas_retornaListaDeResumen() throws Exception {
        var resumen = List.of(
                new ResumenVentaDTO("Peli A", 10L, 1000.0),
                new ResumenVentaDTO("Peli B", 5L, 500.0)
        );

        when(boletoService.obtenerResumenVentas()).thenReturn(resumen);

        mockMvc.perform(get("/api/resumen")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombrePelicula").value("Peli A"))
                .andExpect(jsonPath("$[0].totalBoletosVendidos").value(10))
                .andExpect(jsonPath("$[0].totalIngresos").value(1000.0));
    }
}
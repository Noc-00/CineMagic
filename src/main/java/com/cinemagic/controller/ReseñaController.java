package com.cinemagic.controller;

import com.cinemagic.model.Pelicula;
import com.cinemagic.model.Reseña;
import com.cinemagic.service.ReseñaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reseñas")
public class ReseñaController {

    @Autowired
    private ReseñaService reseñaService;

    @PostMapping
    public Reseña crearReseña(@RequestBody @Valid ReseñaRequest request) {
        return reseñaService.crearReseña(
                request.getUsuarioId(),
                request.getPeliculaId(),
                request.getCalificacion(),
                request.getComentario()
        );
    }

    @GetMapping("/pelicula/{peliculaId}")
    public List<Reseña> obtenerPorPelicula(@PathVariable Long peliculaId) {
        return reseñaService.obtenerPorPelicula(peliculaId);
    }

    @GetMapping
    public ResponseEntity<List<Reseña>> obtenerReseñas() {
        List<Reseña> reseñas = reseñaService.obtenerTodas();
        return ResponseEntity.ok(reseñas);
    }

    public static class ReseñaRequest {
        @NotNull
        private Long usuarioId;

        @NotNull
        private Long peliculaId;

        @NotNull
        @Min(1)
        @Max(5)
        private Integer calificacion;

        @Size(max = 500)
        private String comentario;

        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

        public Long getPeliculaId() { return peliculaId; }
        public void setPeliculaId(Long peliculaId) { this.peliculaId = peliculaId; }

        public Integer getCalificacion() { return calificacion; }
        public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

        public String getComentario() { return comentario; }
        public void setComentario(String comentario) { this.comentario = comentario; }
    }
}
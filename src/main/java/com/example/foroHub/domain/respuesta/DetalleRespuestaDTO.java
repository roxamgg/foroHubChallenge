package com.example.foroHub.domain.respuesta;

import java.time.LocalDateTime;

public record DetalleRespuestaDTO(
            Long id,
            String respuesta,
            Long usuario,
            Long topico,
            LocalDateTime fecha) {

    public DetalleRespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getRespuesta(), respuesta.getUsuario().getId(), respuesta.getTopico().getId(), respuesta.getFechaCreacion());
    }
}

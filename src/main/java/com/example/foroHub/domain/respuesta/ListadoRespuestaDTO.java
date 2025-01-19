package com.example.foroHub.domain.respuesta;

import java.time.LocalDateTime;

public record ListadoRespuestaDTO(
        Long id,
        LocalDateTime fecha,
        String respuesta,
        Long usuario,
        Long topico) {

    public ListadoRespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getFechaCreacion(), respuesta.getRespuesta(), respuesta.getUsuario().getId(), respuesta.getTopico().getId());
    }
}

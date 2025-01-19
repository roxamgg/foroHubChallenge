package com.example.foroHub.domain.topico;

import java.time.LocalDateTime;

public record RespuestaTopicoDTO(Long id,
                                 String titulo,
                                 String mensaje,
                                 Status status,
                                 Long usuario,
                                 String curso,
                                 LocalDateTime fecha) {

    public RespuestaTopicoDTO (Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getStatus(), topico.getUsuario().getId(), topico.getCurso(), topico.getFechaCreacion());
    }

}

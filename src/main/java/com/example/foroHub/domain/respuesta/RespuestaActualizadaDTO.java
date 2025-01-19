package com.example.foroHub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record RespuestaActualizadaDTO(
        @NotNull
        String respuesta,
        @NotNull
        Long idUsuario,
        @NotNull
        Long idTopico) {
}

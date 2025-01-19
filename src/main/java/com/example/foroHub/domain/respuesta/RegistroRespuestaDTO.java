package com.example.foroHub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record RegistroRespuestaDTO(
        @NotNull(message = "La respuesta es obligatoria.")
        String respuesta,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "El topico es obligatorio")
        Long idTopico) {
}

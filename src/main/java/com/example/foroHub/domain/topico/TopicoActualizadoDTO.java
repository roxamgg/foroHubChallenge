package com.example.foroHub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicoActualizadoDTO(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Status status,
        @NotNull
        String curso) {
}

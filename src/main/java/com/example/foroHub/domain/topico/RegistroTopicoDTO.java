package com.example.foroHub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record RegistroTopicoDTO(
        @NotNull(message = "El título es obligatorio y no debe repetirse.")
        String titulo,
        @NotNull(message = "El mensaje es obligatorio y no debe repetirse.")
        String mensaje,
        @NotNull(message = "Al registrar el topico el estado debe ser ´ACTIVO´")
        Status status,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "Indica el nombre completo del curso.")
        String curso) {
}

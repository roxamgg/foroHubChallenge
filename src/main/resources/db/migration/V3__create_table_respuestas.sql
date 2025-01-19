CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    respuesta TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    fechaCreacion DATETIME NOT NULL,
    autor_id BIGINT NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_respuestas_usuarios FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_respuestas_topicos FOREIGN KEY (topico_id) REFERENCES topicos(id)
);
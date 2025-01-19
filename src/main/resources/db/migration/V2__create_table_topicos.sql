CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(300) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion DATETIME NOT NULL,
    status ENUM('ACTIVO', 'INACTIVO', 'RESUELTO') NOT NULL,
    autor_id BIGINT NOT NULL,
    curso VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_topicos_usuarios FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
package com.example.foroHub.domain.respuesta;

import com.example.foroHub.domain.topico.RegistroTopicoDTO;
import com.example.foroHub.domain.topico.Topico;
import com.example.foroHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="Respuesta")
@Table(name = "respuestas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"autor_id", "topico_id"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaCreacion;
    private String respuesta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="autor_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topico_id")
    private Topico topico;
    private boolean estado;

    public Respuesta() {
    }

    public Respuesta(RegistroRespuestaDTO registroRespuestaDTO, Usuario usuario, Topico topico) {
        this.respuesta = registroRespuestaDTO.respuesta();
        this.usuario = usuario;
        this.topico=topico;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

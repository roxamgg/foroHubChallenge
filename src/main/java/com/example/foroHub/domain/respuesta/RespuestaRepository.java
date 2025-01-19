package com.example.foroHub.domain.respuesta;

import com.example.foroHub.domain.topico.Topico;
import com.example.foroHub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByTopicoAndEstadoTrue(Topico topico, Pageable paginacion);
    Optional<Respuesta> findByIdAndEstadoTrue(Long id);
    Optional<Respuesta> findByUsuarioAndTopicoAndEstadoTrue(Usuario usuario, Topico topico);
    Optional<Respuesta> findByIdAndUsuarioAndTopicoAndEstadoTrue(Long id, Usuario usuario, Topico topico);
    Optional<Respuesta> findByUsuarioAndTopicoAndEstadoFalse(Usuario usuario, Topico topico);
}

package com.example.foroHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByMensajeIgnoreCase(String mensaje);
    Page<Topico> findByEstadoTrue(Pageable paginacion);
    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND YEAR(t.fechaCreacion) = :anio AND t.estado = true")
    List<ListadoTopicoDTO> findByCursoAndFechaCreacionYear(String curso, Integer anio);
    Optional<Topico> findByTituloAndIdNotAndEstadoTrue(String titulo, Long id);
    Optional<Topico> findByMensajeAndIdNotAndEstadoTrue(String mensaje, Long id);
}

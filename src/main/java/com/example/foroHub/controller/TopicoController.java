package com.example.foroHub.controller;

import com.example.foroHub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private TopicoBL topicoBL;

    public TopicoController(TopicoBL topicoBL) {
        this.topicoBL = topicoBL;
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid RegistroTopicoDTO registroTopicoDTO){
        var registro = topicoBL.registrar(registroTopicoDTO);
        return ResponseEntity.ok(registro);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTopicoDTO>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        Pageable paginacionConOrden = PageRequest.of(
                paginacion.getPageNumber(),
                paginacion.getPageSize(),
                Sort.by(Sort.Order.asc("fechaCreacion"))  //Orden ascendente por 'fechaCreacion'.
        );
        var listado = topicoBL.listarTopicos(paginacion);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/{curso}/{anio}")
    public ResponseEntity<List<ListadoTopicoDTO>> listadoTopicosPorCursoYAnio(
            @PathVariable String curso,
            @PathVariable Integer anio) {

        var listado = topicoBL.listarTopicosPorCursoYAnio(curso, anio);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> obtenerTopicoPorId(@PathVariable Long id) {
        var topico = topicoBL.obtenerTopicoPorId(id);
        return ResponseEntity.ok(new RespuestaTopicoDTO(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getUsuario().getId(),
                topico.getCurso(),
                topico.getFechaCreacion()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO) {
        var topicoActualizado = topicoBL.actualizarTopico(id, topicoActualizadoDTO);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoBL.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}

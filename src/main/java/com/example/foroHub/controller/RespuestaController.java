package com.example.foroHub.controller;

import com.example.foroHub.domain.respuesta.*;
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

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private RespuestaBL respuestaBL;

    public RespuestaController(RespuestaBL respuestaBL) {
        this.respuestaBL = respuestaBL;
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrarRespuesta(@RequestBody @Valid RegistroRespuestaDTO registroRespuestaDTO){
        var registro = respuestaBL.registrar(registroRespuestaDTO);
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Page<ListadoRespuestaDTO>> listadoRespuestasPorTopico(@PathVariable Long id, @PageableDefault(size = 10) Pageable paginacion) {
        Pageable paginacionConOrden = PageRequest.of(
                paginacion.getPageNumber(),
                paginacion.getPageSize(),
                Sort.by(Sort.Order.asc("fechaCreacion"))  //Orden ascendente por 'fechaCreacion'.
        );
        var listado = respuestaBL.listarRespuestas(id, paginacion);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRespuestaDTO> obtenerDetalleRespuestaPorId(@PathVariable Long id) {
        var respuesta = respuestaBL.obtenerDetalleRespuestaPorId(id);
        return ResponseEntity.ok(new DetalleRespuestaDTO(respuesta.getId(),
                respuesta.getRespuesta(),
                respuesta.getUsuario().getId(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        var respuestaActualizada = respuestaBL.actualizarRespuesta(id, respuestaActualizadaDTO);
        return ResponseEntity.ok(respuestaActualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaBL.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}

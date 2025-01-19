package com.example.foroHub.domain.respuesta;

import com.example.foroHub.domain.ValidacionException;
import com.example.foroHub.domain.topico.Topico;
import com.example.foroHub.domain.topico.TopicoRepository;
import com.example.foroHub.domain.usuario.Usuario;
import com.example.foroHub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RespuestaBL {

    private RespuestaRepository respuestaRepository;
    private UsuarioRepository usuarioRepository;
    private TopicoRepository topicoRepository;

    public RespuestaBL(RespuestaRepository respuestaRepository, UsuarioRepository usuarioRepository, TopicoRepository topicoRepository) {
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.topicoRepository = topicoRepository;
    }

    public DetalleRespuestaDTO registrar(RegistroRespuestaDTO registroRespuestaDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(registroRespuestaDTO.idUsuario());
        Optional<Topico> topico = topicoRepository.findById(registroRespuestaDTO.idTopico());
        //Validaciones
        if (!usuario.isPresent()){
            throw new ValidacionException("El usuario indicado no está registrado en la base de datos.");
        }
        if (!topico.isPresent() || !topico.get().isEstado()) {
            throw new ValidacionException("El tópico indicado no se encuentra registrado en la base de datos.");
        }
        //Se verifica si el usuario ya ha respondido al topico y está en estado true.
        var respuestaExistenteActiva = respuestaRepository.findByUsuarioAndTopicoAndEstadoTrue(usuario.get(), topico.get());
        if (respuestaExistenteActiva.isPresent()) {
            throw new ValidacionException("El usuario ya respondió a este tópico.");
        }
        //Se verifica si el usuario ya ha respondido al topico y está en estado false.
        var respuestaExistenteInactiva = respuestaRepository.findByUsuarioAndTopicoAndEstadoFalse(usuario.get(), topico.get());
        if (respuestaExistenteInactiva.isPresent()) {
            Respuesta respuesta = respuestaExistenteInactiva.get();
            respuesta.setRespuesta(registroRespuestaDTO.respuesta());
            respuesta.setEstado(true);
            respuestaRepository.save(respuesta);
            return new DetalleRespuestaDTO(respuesta);
        }

        //Se crea respuesta para registrar.
        var respuesta = new Respuesta(registroRespuestaDTO, usuario.get(), topico.get());
        respuestaRepository.save(respuesta);
        return new DetalleRespuestaDTO(respuesta);
    }

    public Page<ListadoRespuestaDTO> listarRespuestas(Long id, Pageable paginacion) {
        //Se busca el topico.
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent() || !topico.get().isEstado()){
            throw new ValidacionException("El topico indicado no está registrado en la base de datos.");
        }
        //Se obtiene listado.
        var listado = respuestaRepository.findByTopicoAndEstadoTrue(topico.get(), paginacion);
        if (listado.isEmpty()){
            throw new ValidacionException("No hay registros en la base de datos con ese ID topico: " + id);
        }

        return listado.map(ListadoRespuestaDTO::new);
    }

    public Respuesta obtenerDetalleRespuestaPorId(Long id) {
        var respuesta = respuestaRepository.findByIdAndEstadoTrue(id);
        if (!respuesta.isPresent()){
            throw new ValidacionException("No existe la respuesta con ese id indicado.");
        }

        return respuesta.get();
    }


    public DetalleRespuestaDTO actualizarRespuesta(Long id, @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(respuestaActualizadaDTO.idUsuario());
        Optional<Topico> topico = topicoRepository.findById(respuestaActualizadaDTO.idTopico());
        //Validaciones
        if (!usuario.isPresent()){
            throw new ValidacionException("El usuario indicado no está registrado en la base de datos.");
        }
        if (!topico.isPresent() || !topico.get().isEstado()) {
            throw new ValidacionException("El tópico indicado no se encuentra registrado en la base de datos.");
        }

        var respuestaXUsuarioYTopico = respuestaRepository.findByIdAndUsuarioAndTopicoAndEstadoTrue(id, usuario.get(), topico.get());
        if (!respuestaXUsuarioYTopico.isPresent()){
            throw new ValidacionException("No existe la respuesta con los datos suministrados. Por favor validar la información.");
        }

        Respuesta respuesta = respuestaXUsuarioYTopico.get();
        respuesta.setRespuesta(respuestaActualizadaDTO.respuesta());

        respuestaRepository.save(respuesta);

        return new DetalleRespuestaDTO(respuesta);
    }

    public void eliminarRespuesta(Long id) {
        var respuesta = obtenerDetalleRespuestaPorId(id);
        respuesta.setEstado(false);
        respuestaRepository.save(respuesta);
    }
}

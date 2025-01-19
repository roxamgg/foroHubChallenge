package com.example.foroHub.domain.topico;

import com.example.foroHub.domain.ValidacionException;
import com.example.foroHub.domain.usuario.Usuario;
import com.example.foroHub.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoBL {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;

    public TopicoBL(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RespuestaTopicoDTO registrar(RegistroTopicoDTO registroTopicoDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(registroTopicoDTO.idUsuario());
        //Validaciones
        if (!usuario.isPresent()){
            throw new ValidacionException("El usuario indicado no está registrado en la base de datos.");
        }
        if (topicoRepository.existsByTituloIgnoreCase(registroTopicoDTO.titulo())){
            throw new ValidacionException("El título ya se encuentra registrado en la base de datos. Por favor ingresa otro titulo.");
        }
        if (topicoRepository.existsByMensajeIgnoreCase(registroTopicoDTO.mensaje())){
            throw new ValidacionException("El mensaje ya se encuentra registrado en la base de datos. Por favor ingresa otro mensaje.");
        }
        //Se crea topico para registrar.
        var topico = new Topico(registroTopicoDTO, usuario.get());
        topicoRepository.save(topico);
        return new RespuestaTopicoDTO(topico);
    }

    public Page<ListadoTopicoDTO> listarTopicos(Pageable paginacion) {
        return topicoRepository.findByEstadoTrue(paginacion).map(ListadoTopicoDTO::new);
    }

    public List<ListadoTopicoDTO> listarTopicosPorCursoYAnio(String curso, Integer anio) {
        if (curso == null && anio == null) {
            throw new ValidacionException("Debe indicar el curso y el año.");
        }
        //Se obtiene listado.
        var listado = topicoRepository.findByCursoAndFechaCreacionYear(curso, anio);
        if (listado.isEmpty()){
            throw new ValidacionException("No hay registros en la base de datos con esos criterios de búsqueda.");
        }

        return listado;
    }

    public Topico obtenerTopicoPorId(Long id) {
        var topico = topicoRepository.findById(id);
        if (!topico.isPresent() || !topico.get().isEstado()){
            throw new ValidacionException("No existe el topico con ese id indicado.");
        }

        return topico.get();

    }

    private boolean existeTopicoConTitulo(Long id, TopicoActualizadoDTO topicoActualizadoDTO) {
        var topicoPorTitulo = topicoRepository.findByTituloAndIdNotAndEstadoTrue(topicoActualizadoDTO.titulo(), id);
        //Si existe un tópico con el mismo título (pero con un ID diferente), se retorna true.
        return topicoPorTitulo.isPresent();
    }

    private boolean existeTopicoConMensaje(Long id, TopicoActualizadoDTO topicoActualizadoDTO) {
        var topicoPorMensaje = topicoRepository.findByMensajeAndIdNotAndEstadoTrue(topicoActualizadoDTO.mensaje(), id);
        // Si existe un tópico con el mismo mensaje (pero con un ID diferente), se retorna true.
        return topicoPorMensaje.isPresent();
    }

    public RespuestaTopicoDTO actualizarTopico(Long id, TopicoActualizadoDTO topicoActualizadoDTO) {
        //Se consulta si el tópico existe.
        var topico = obtenerTopicoPorId(id);

        if (existeTopicoConTitulo(id, topicoActualizadoDTO)){
            throw new ValidacionException("El título ya se encuentra registrado en otro tópico. Por favor ingresa otro título.");
        }
        if (existeTopicoConMensaje(id, topicoActualizadoDTO)){
            throw new ValidacionException("El mensaje ya se encuentra registrado en otro tópico. Por favor ingresa otro mensaje.");
        }

        topico.setTitulo(topicoActualizadoDTO.titulo());
        topico.setMensaje(topicoActualizadoDTO.mensaje());
        topico.setStatus(topicoActualizadoDTO.status());
        topico.setCurso(topicoActualizadoDTO.curso());

        topicoRepository.save(topico);
        return new RespuestaTopicoDTO(topico);
    }

    public void eliminarTopico(Long id) {
        var topico = obtenerTopicoPorId(id);
        topico.setEstado(false);
        topicoRepository.save(topico);
    }
}

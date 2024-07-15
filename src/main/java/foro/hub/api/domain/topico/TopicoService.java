package foro.hub.api.domain.topico;

import foro.hub.api.domain.cursos.CursoRepository;
import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import foro.hub.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    List<ValidadorDeTopicos> validadores;

    public DatosRespuestaTopico registrar(DatosRegistrarTopico datosRegistrarTopico) {

        validadores.forEach(v-> v.validar(datosRegistrarTopico));

        var usuario = usuarioRepository.findById(datosRegistrarTopico.idUsuario()).get();
        var curso = cursoRepository.findById(datosRegistrarTopico.idCurso()).get();
        var topico = new Topico(datosRegistrarTopico, usuario, curso);
        topicoRepository.save(topico);

        return new DatosRespuestaTopico(topico);
    }
}

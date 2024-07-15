package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.usuarios.validaciones.ValidadorDeUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorDeUsuarios> validadores;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatosRespuestaUsuario registrar(DatosRegistrarUsuario datos) {

        validadores.forEach(v-> v.validar(datos));
        String hash = codificarPassword(datos.password());

        var usuario = new Usuario(datos, hash);
        usuarioRepository.save(usuario);

        return new DatosRespuestaUsuario(usuario);
    }

    public String codificarPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

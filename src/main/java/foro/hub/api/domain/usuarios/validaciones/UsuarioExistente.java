package foro.hub.api.domain.usuarios.validaciones;

import foro.hub.api.domain.topico.DatosRegistrarTopico;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.usuarios.DatosRegistrarUsuario;
import foro.hub.api.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioExistente implements ValidadorDeUsuarios {
    @Autowired
    private UsuarioRepository repository;

    public void validar(DatosRegistrarUsuario datos) {

        var usuario = repository.findByEmail(datos.email());
        if(usuario != null){
            throw new ValidationException("Ya existe un usuario con el email: "
                    + datos.email() + " en el sistema");
        }
    }
}

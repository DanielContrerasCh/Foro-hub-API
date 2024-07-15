package foro.hub.api.domain.usuarios.validaciones;

import foro.hub.api.domain.topico.DatosRegistrarTopico;
import foro.hub.api.domain.usuarios.DatosRegistrarUsuario;

public interface ValidadorDeUsuarios {
    public void validar(DatosRegistrarUsuario datos);
}

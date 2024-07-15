package foro.hub.api.domain.respuestas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(Long id,
                                      String mensaje,
                                      @JsonProperty("FechaActualización")LocalDateTime fecha,
                                      String idUsuario,
                                      String idTopico) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFecha(),
                respuesta.getUsuario().getNombre(),
                respuesta.getTopico().getTitulo());
    }
}

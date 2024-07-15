package foro.hub.api.domain.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        @JsonProperty("fechaCreacion") LocalDateTime fecha,
        String estado,
        String autor,
        String curso
) {
    public DatosRespuestaTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus().toString(),
                topico.getUsuario().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}

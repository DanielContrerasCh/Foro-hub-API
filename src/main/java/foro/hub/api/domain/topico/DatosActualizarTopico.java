package foro.hub.api.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarTopico(
        String titulo,
        String mensaje
        //Preguntar
        //Boolean status,
        //String autor,
        //String curso
) {
}

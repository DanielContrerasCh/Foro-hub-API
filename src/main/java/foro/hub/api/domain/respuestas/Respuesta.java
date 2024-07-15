package foro.hub.api.domain.respuestas;

import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String mensaje;

    private LocalDateTime fecha;

    public Respuesta (DatosRegistrarRespuesta datosRegistrarRespuesta, Usuario usuario, Topico topico) {
        this.mensaje = datosRegistrarRespuesta.mensaje();
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.topico = topico;
    }

    public void actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta) {
        if (datosActualizarRespuesta.mensaje() != null) {
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
        this.fecha = LocalDateTime.now();
    }
}

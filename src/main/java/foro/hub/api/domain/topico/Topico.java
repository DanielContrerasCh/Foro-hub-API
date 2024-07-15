package foro.hub.api.domain.topico;

import foro.hub.api.domain.cursos.Curso;
import foro.hub.api.domain.respuestas.Respuesta;
import foro.hub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;


    public Topico(DatosRegistrarTopico datosRegistrarTopico, Usuario usuario, Curso curso) {
        this.status = true;
        this.titulo = datosRegistrarTopico.titulo();
        this.mensaje = datosRegistrarTopico.mensaje();
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.curso = curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        this.fecha = LocalDateTime.now();
    }

}

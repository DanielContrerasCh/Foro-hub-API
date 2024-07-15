package foro.hub.api.controller;

import foro.hub.api.domain.respuestas.*;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestasController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra una nueva respuesta en la base de datos")
    public ResponseEntity registrarRespuesta(@RequestBody @Valid DatosRegistrarRespuesta datosRegistrarRespuesta,
                                             UriComponentsBuilder uriComponentsBuilder) {
        var usuario = usuarioRepository.findById(datosRegistrarRespuesta.idUsuario()).get();
        var topico = topicoRepository.findById(datosRegistrarRespuesta.idTopico()).get();
        var respuesta = new Respuesta(datosRegistrarRespuesta, usuario, topico);
        respuestaRepository.save(respuesta);

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de respuestas")
    public ResponseEntity<Page<DatosRespuestaRespuesta>> listadoRespuestas(@PageableDefault(size = 4) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosRespuestaRespuesta::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene la respuesta con el ID")
    public ResponseEntity<DatosRespuestaRespuesta> muestraRespuesta(@PathVariable long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza una respuesta en la base de datos")
    public ResponseEntity actualizarRespuesta(@PathVariable long id, @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarDatos(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta de la base de datos")
    public ResponseEntity eliminarRespuesta(@PathVariable long id) {
        if(!respuestaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.eliminarRespuesta(id);
        respuestaRepository.flush();
        return ResponseEntity.noContent().build();
    }
}

package foro.hub.api.controller;

import foro.hub.api.domain.cursos.CursoRepository;
import foro.hub.api.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topico en la base de datos")
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datosRegistrarTopico, UriComponentsBuilder uriComponentsBuilder) {
        var respuesta = topicoService.registrar(datosRegistrarTopico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de topicos")
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoTopicos(@PageableDefault(size = 4) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosRespuestaTopico::new));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el topico con el ID")
    public ResponseEntity<DatosRespuestaTopico> muestraTopico(@PathVariable long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @GetMapping("/fecha")
    @Operation(summary = "Obtiene el listado de los primeros 10 topicos en orden ascendente de fecha ")
    public ResponseEntity<Page<DatosRespuestaTopico>> listaPrimeros10ASC() {
        Pageable paginacion = PageRequest.of(0, 10);
        var respuesta = topicoRepository.findTop10ByOrderByFechaAsc(paginacion).map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/curso/{id}")
    @Operation(summary = "Obtiene el listado de los topicos pertenecientes a un curso especifico")
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoPorCurso(@PathVariable Long id,
                                                                    @PageableDefault(size = 4) Pageable paginacion) {
        if(!cursoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        var respuesta = topicoRepository.findAllByNombreCurso(id, paginacion).map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/fecha/{anio}")
    @Operation(summary = "Obtiene el listado de los topicos ordenados en un año especifico")
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoPorAnio(@PathVariable Long anio,
                                                                   @PageableDefault(size = 4) Pageable paginacion) {
        var respuesta = topicoRepository.findAllByAnio(anio, paginacion).map(DatosRespuestaTopico::new);
        if(respuesta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza un topico en la base de datos")
    public ResponseEntity actualizarTopico(@PathVariable long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico de la base de datos")
    public ResponseEntity eliminarTopico(@PathVariable long id) {
        if(!topicoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        topicoRepository.eliminarTopico(id);
        topicoRepository.flush();
        return ResponseEntity.noContent().build();
    }
}

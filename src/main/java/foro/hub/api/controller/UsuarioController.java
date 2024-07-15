package foro.hub.api.controller;

import foro.hub.api.domain.usuarios.*;
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
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Registra un nuevo usuario en la base de datos")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datosRegistrarUsuario, UriComponentsBuilder uriComponentsBuilder) {
        var respuesta = usuarioService.registrar(datosRegistrarUsuario);

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de usuarios")
    public ResponseEntity<Page<DatosRespuestaUsuario>> listadoUsuarios(@PageableDefault(size = 4) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosRespuestaUsuario::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el usuario con el ID")
    public ResponseEntity<DatosRespuestaUsuario> muestraUsuario(@PathVariable long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza un usuario en la base de datos")
    public ResponseEntity actualizarUsuario(@PathVariable long id, @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        if(datosActualizarUsuario.password() != null){
            String hash = usuarioService.codificarPassword(datosActualizarUsuario.password());
            usuario.actualizarDatos(datosActualizarUsuario, hash);
            return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
        }
        usuario.actualizarDatos(datosActualizarUsuario, null);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un usuario de la base de datos")
    public ResponseEntity eliminarUsuario(@PathVariable long id) {
        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.eliminarUsuario(id);
        usuarioRepository.flush();
        return ResponseEntity.noContent().build();
    }
}

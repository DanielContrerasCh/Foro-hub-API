package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.respuestas.Respuesta;
import foro.hub.api.domain.topico.DatosActualizarTopico;
import foro.hub.api.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Topico> topicos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;

    private String nombre;
    private String email;
    private String password;

    public Usuario(DatosRegistrarUsuario datosRegistrarUsuario, String hash) {
        this.nombre = datosRegistrarUsuario.nombre();
        this.email = datosRegistrarUsuario.email();
        this.password = hash;
    }

    public void actualizarDatos(DatosActualizarUsuario datosActualizarUsuario,String hash) {
        if (datosActualizarUsuario.nombre() != null) {
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.email() != null) {
            this.email = datosActualizarUsuario.email();
        }
        if (datosActualizarUsuario.password() != null) {
            this.password = hash;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

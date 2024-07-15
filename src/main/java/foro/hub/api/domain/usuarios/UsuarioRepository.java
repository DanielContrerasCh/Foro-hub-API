package foro.hub.api.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByNombre(String username);

    Usuario findByEmail(String email);

    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.id = :id")
    void eliminarUsuario(long id);
}

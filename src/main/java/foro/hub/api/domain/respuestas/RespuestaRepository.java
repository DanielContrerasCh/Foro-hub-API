package foro.hub.api.domain.respuestas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Modifying
    @Query("DELETE FROM Respuesta r WHERE r.id = :id")
    void eliminarRespuesta(long id);
}

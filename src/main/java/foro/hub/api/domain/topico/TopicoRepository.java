package foro.hub.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Topico findByTitulo(String titulo);

    @Modifying
    @Query("DELETE FROM Topico t WHERE t.id = :id")
    void eliminarTopico(Long id);

    @Query("""
        SELECT t
        FROM Topico t
        JOIN t.curso c
        JOIN t.usuario u
        WHERE c.id = :id
    """)
    Page<Topico> findAllByNombreCurso(Long id, Pageable paginacion);

    @Query("""
        SELECT t
        FROM Topico t
        WHERE YEAR(t.fecha) = :anio
    """)
    Page<Topico> findAllByAnio(Long anio, Pageable paginacion);

    @Query("SELECT t FROM Topico t ORDER BY t.fecha ASC")
    Page<Topico> findTop10ByOrderByFechaAsc(Pageable paginacion);
}

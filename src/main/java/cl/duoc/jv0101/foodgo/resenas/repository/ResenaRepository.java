package cl.duoc.jv0101.foodgo.resenas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.jv0101.foodgo.resenas.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    @Override
    @EntityGraph(attributePaths = "respuestas")
    List<Resena> findAll();

    @Override
    @EntityGraph(attributePaths = "respuestas")
    Optional<Resena> findById(Long id);
}

package cl.duoc.jv0101.foodgo.resenas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.jv0101.foodgo.resenas.model.RespuestaResena;

public interface RespuestaResenaRepository extends JpaRepository<RespuestaResena, Long> {
    List<RespuestaResena> findByResena_Id(Long resenaId);
}

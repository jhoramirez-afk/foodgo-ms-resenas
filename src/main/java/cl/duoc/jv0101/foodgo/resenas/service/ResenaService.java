package cl.duoc.jv0101.foodgo.resenas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.duoc.jv0101.foodgo.resenas.model.Resena;
import cl.duoc.jv0101.foodgo.resenas.repository.ResenaRepository;

@Service
@Transactional
public class ResenaService {

    private final ResenaRepository repository;

    public ResenaService(ResenaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Resena> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Resena> findById(Long id) {
        return repository.findById(id);
    }

    public Resena create(Resena recurso) {
        recurso.setId(null);
        recurso.getRespuestas().forEach(item -> item.setId(null));
        return repository.save(recurso);
    }

    public Optional<Resena> update(Long id, Resena datos) {
        return repository.findById(id).map(existente -> {
            existente.setPedido(datos.getPedido());
            existente.setComentario(datos.getComentario());
            existente.setCalificacion(datos.getCalificacion());
            return repository.save(existente);
        });
    }

    public boolean delete(Long id) {
        return repository.findById(id).map(existente -> {
            repository.delete(existente);
            return true;
        }).orElse(false);
    }
}

package cl.duoc.jv0101.foodgo.resenas.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.duoc.jv0101.foodgo.resenas.exception.ResourceNotFoundException;
import cl.duoc.jv0101.foodgo.resenas.model.RespuestaResena;
import cl.duoc.jv0101.foodgo.resenas.model.Resena;
import cl.duoc.jv0101.foodgo.resenas.repository.RespuestaResenaRepository;
import cl.duoc.jv0101.foodgo.resenas.repository.ResenaRepository;

@Service
@Transactional
public class RespuestaResenaService {

    private final RespuestaResenaRepository repository;
    private final ResenaRepository resenaRepository;

    public RespuestaResenaService(RespuestaResenaRepository repository, ResenaRepository resenaRepository) {
        this.repository = repository;
        this.resenaRepository = resenaRepository;
    }

    @Transactional(readOnly = true)
    public List<RespuestaResena> findByResenaId(Long resenaId) {
        if (!resenaRepository.existsById(resenaId)) {
            throw new ResourceNotFoundException("Resena no encontrado con id " + resenaId);
        }
        return repository.findByResena_Id(resenaId);
    }

    @Transactional(readOnly = true)
    public RespuestaResena findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RespuestaResena no encontrado con id " + id));
    }

    public RespuestaResena create(Long resenaId, RespuestaResena recurso) {
        Resena resena = resenaRepository.findById(resenaId)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrado con id " + resenaId));
        recurso.setId(null);
        recurso.setResena(resena);
        return repository.save(recurso);
    }

    public RespuestaResena update(Long id, RespuestaResena datos) {
        RespuestaResena existente = findById(id);
        existente.setAutor(datos.getAutor());
        existente.setMensaje(datos.getMensaje());
        existente.setFechaHora(datos.getFechaHora());
        return repository.save(existente);
    }

    public void delete(Long id) {
        RespuestaResena existente = findById(id);
        repository.delete(existente);
    }
}

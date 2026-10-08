package cl.duoc.jv0101.foodgo.resenas.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.jv0101.foodgo.resenas.model.RespuestaResena;
import cl.duoc.jv0101.foodgo.resenas.service.RespuestaResenaService;

@RestController
@RequestMapping("/api")
public class RespuestaResenaController {

    private final RespuestaResenaService service;

    public RespuestaResenaController(RespuestaResenaService service) {
        this.service = service;
    }

    @GetMapping("/resenas/{resenaId}/respuestas")
    public ResponseEntity<List<RespuestaResena>> listarPorResena(@PathVariable Long resenaId) {
        return ResponseEntity.ok(service.findByResenaId(resenaId));
    }

    @PostMapping("/resenas/{resenaId}/respuestas")
    public ResponseEntity<RespuestaResena> crear(@PathVariable Long resenaId, @Valid @RequestBody RespuestaResena recurso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(resenaId, recurso));
    }

    @GetMapping("/respuestas/{id}")
    public ResponseEntity<RespuestaResena> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/respuestas/{id}")
    public ResponseEntity<RespuestaResena> actualizar(@PathVariable Long id, @Valid @RequestBody RespuestaResena datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/respuestas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

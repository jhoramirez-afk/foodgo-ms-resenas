package cl.duoc.jv0101.foodgo.resenas.controller;

import cl.duoc.jv0101.foodgo.resenas.exception.ResourceNotFoundException;
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
import cl.duoc.jv0101.foodgo.resenas.model.Resena;
import cl.duoc.jv0101.foodgo.resenas.service.ResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Resena>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtener(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrado con id " + id));
    }

    @PostMapping
    public ResponseEntity<Resena> crear(@Valid @RequestBody Resena recurso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(recurso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(@PathVariable Long id,
            @Valid @RequestBody Resena datos) {
        return service.update(id, datos).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException("Resena no encontrado con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}

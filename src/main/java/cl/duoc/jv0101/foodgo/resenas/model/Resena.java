package cl.duoc.jv0101.foodgo.resenas.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pedido es obligatorio")
    @Size(max = 255, message = "El campo admite hasta 255 caracteres")
    @Column(nullable = false)
    private String pedido;
    @NotBlank(message = "Comentario es obligatorio")
    @Size(max = 255, message = "El campo admite hasta 255 caracteres")
    @Column(nullable = false)
    private String comentario;
    @NotNull(message = "Calificación es obligatoria")
    @DecimalMin(value = "1", message = "La calificación mínima es 1")
    @DecimalMax(value = "5", message = "La calificación máxima es 5")
    @Digits(integer = 1, fraction = 0, message = "La calificación debe ser un número entero de 1 a 5")
    @Column(nullable = false, precision = 1, scale = 0)
    private BigDecimal calificacion;

    @Valid
    @OneToMany(mappedBy = "resena", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("resena-respuestas")
    private List<RespuestaResena> respuestas = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPedido() { return pedido; }

    public void setPedido(String pedido) { this.pedido = pedido; }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    public BigDecimal getCalificacion() { return calificacion; }

    public void setCalificacion(BigDecimal calificacion) { this.calificacion = calificacion; }

    public List<RespuestaResena> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaResena> items) {
        this.respuestas.clear();
        if (items != null) {
            items.forEach(this::addRespuestaResena);
        }
    }

    public void addRespuestaResena(RespuestaResena item) {
        respuestas.add(item);
        item.setResena(this);
    }

    public void removeRespuestaResena(RespuestaResena item) {
        respuestas.remove(item);
        item.setResena(null);
    }
}

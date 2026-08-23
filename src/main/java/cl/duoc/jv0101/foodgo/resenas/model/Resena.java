package cl.duoc.jv0101.foodgo.resenas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;


@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El pedido es obligatorio")
    @Column(nullable = false)
    private String pedido;
    @Column
    private String comentario;
    @Column
    private BigDecimal calificacion;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPedido() { return pedido; }

    public void setPedido(String pedido) { this.pedido = pedido; }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    public BigDecimal getCalificacion() { return calificacion; }

    public void setCalificacion(BigDecimal calificacion) { this.calificacion = calificacion; }

}

package co.edu.unbosque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_movimiento")
public class DetalleMovimientoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_MOVIMIENTO", unique = true)
    private int ID_DETALLE_MOVIMIENTO;

    private int CNT_PRODUCTO_MOVIMIENTO;

    @ManyToOne
    @JoinColumn(name = "ID_MOVIMIENTO_BODEGA")
    private MovimientoBodegaDTO ID_MOVIMIENTO_BODEGA;

    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_BODEGA")
    private DetalleBodegaDTO ID_DETALLE_BODEGA;
}

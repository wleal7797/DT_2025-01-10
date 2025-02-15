package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_bodega")
public class DetalleBodegaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_BODEGA", unique = true)
    private int ID_DETALLE_BODEGA;

    private int CNT_PRODUCTO_BODEGA;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private ProductoDTO PRODUCTO;

    @ManyToOne
    @JoinColumn(name = "ID_BODEGA")
    private BodegaDTO ID_BODEGA;
}

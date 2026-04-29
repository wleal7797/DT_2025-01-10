package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_bodega")
public class DetalleBodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_BODEGA", unique = true)
    private int ID_DETALLE_BODEGA;
    @Column(name="CNT_PRODUCTO_BODEGA")
    private int CNT_PRODUCTO_BODEGA;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ID_BODEGA")
    private Bodega bodega;
}
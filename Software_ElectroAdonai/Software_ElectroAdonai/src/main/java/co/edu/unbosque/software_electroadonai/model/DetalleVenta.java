package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA", unique = true)
    private int ID_DETALLE_VENTA;
    @Column(name="CNT_PRODUCTO_VENTA")
    private int CNT_PRODUCTO_VENTA;
    @Column(name="PRECIO_VENTA_PRODUCTO")
    private double PRECIO_VENTA_PRODUCTO;
    @ManyToOne
    @JoinColumn(name = "ID_VENTA")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "ID_BODEGA")
    private Bodega bodega;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
}
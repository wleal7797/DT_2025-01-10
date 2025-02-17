package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_venta")
public class DetalleVentaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA", unique = true)
    private int ID_DETALLE_VENTA;

    private int CNT_PRODUCTO_VENTA;
    private double PRECIO_VENTA_PRODUCTO;
    private String SERIAL_PRODUCTO;

    @ManyToOne
    @JoinColumn(name = "ID_VENTA")
    private VentaDTO VENTA;

    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_BODEGA")
    private DetalleBodega DETALLE_VENTA;
}
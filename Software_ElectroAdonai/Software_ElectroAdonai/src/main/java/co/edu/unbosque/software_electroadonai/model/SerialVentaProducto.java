package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "serial_venta_producto")
public class SerialVentaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERIAL_PRODUCTO", unique = true)
    private int ID_SERIAL_PRODUCTO;
    @Column(name="SERIAL")
    private String SERIAL;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "ID_VENTA")
    private Venta venta;
}

package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utilidades_pr_producto")
public class UtilidadesPorProductoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UTILIDAD_PR", unique = true)
    private int ID_UTILIDAD_POR_PRODUCTO;

    private double UTILIDAD_BRUTA;
    private double UTILIDAD_FINAL;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private ProductoDTO ID_PRODUCTO;

    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_VENTA")
    private DetalleVentaDTO ID_DETALLE_VENTA;
}

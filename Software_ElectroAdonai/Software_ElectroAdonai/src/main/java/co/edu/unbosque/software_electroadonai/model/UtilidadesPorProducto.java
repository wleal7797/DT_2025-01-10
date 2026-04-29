package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utilidades_pr_producto")
public class UtilidadesPorProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UTILIDAD_PR", unique = true)
    private int ID_UTILIDAD_PR;
    @Column(name="UTILIDAD_BRUTA")
    private double UTILIDAD_BRUTA;
    @Column(name="UTILIDAD_FINAL")
    private double UTILIDAD_FINAL;
    @Column(name="ESTADO")
    private String ESTADO;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_VENTA")
    private DetalleVenta detalle_venta;
}


package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA", unique = true)
    private int ID_VENTA;
    @Column(name="PRECIO_VENTA_TOTAL")
    private double PRECIO_VENTA_TOTAL;
    @Column(name="REMISION")
    private String REMISION;
    @Column(name="FECHA_VENTA")
    private LocalDate FECHA_VENTA;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}

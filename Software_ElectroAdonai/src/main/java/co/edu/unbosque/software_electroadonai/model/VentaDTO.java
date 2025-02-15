package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "venta")
public class VentaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA", unique = true)
    private int ID_VENTA;

    private double PRECIO_VENTA_TOTAL;
    private String REMISION;
    private LocalDate FECHA_VENTA;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado ID_EMPLEADO;
}

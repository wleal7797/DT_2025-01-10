package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movimiento_bodega")
public class MovimientoBodega{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO", unique = true)
    private int ID_MOVIMIENTO;
    @Column(name="FECHA_MOVIMIENTO")
    private LocalDate FECHA_MOVIMIENTO;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_MOVIMIENTO")
    private TipoMovimiento TIPO_MOVIMIENTO;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}


package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movimiento_bodega")
public class MovimientoBodegaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO", unique = true)
    private int ID_MOVIMIENTO;

    private LocalDate FECHA_MOVIMIENTO;


    private String TIPO_MOVIMIENTO;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}

package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "adelantos")
public class Adelanto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADELANTO", unique = true)
    private int ID_ADELANTO;
    @Column(name="CNT_ADELANTO")
    private double CNT_ADELANTO;
    @Column(name="FECHA_ADELANTO")
    private LocalDate FECHA_ADELANTO;
    @Column(name="ESTADO_ADELANTO")
    private String ESTADO_ADELANTO;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}

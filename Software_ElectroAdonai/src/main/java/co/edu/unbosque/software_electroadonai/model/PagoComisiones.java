package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "pago_comisiones")
public class PagoComisiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMISIONES", unique = true)
    private int ID_COMISIONES;
    @Column(name="COMISION")
    private double COMISION;
    @Column(name="SALDO")
    private double SALDO;
    @Column(name="ESTADO")
    private String ESTADO;
    @Column(name="FECHA")
    private LocalDate FECHA;

    @ManyToOne
    @JoinColumn(name="ID_ADELANTOS")
    private Adelanto adelantos;
    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}
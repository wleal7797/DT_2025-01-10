package co.edu.unbosque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "pago_comisiones")
public class PagoComisionesDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMISIONES", unique = true)
    private int ID_COMISIONES;

    private double COMISION;
    private double ADELANTOS;
    private double SALDO;
    private String ESTADO;
    private LocalDate FECHA;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private EmpleadoDTO ID_EMPLEADO;
}

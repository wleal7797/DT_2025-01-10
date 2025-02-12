package co.edu.unbosque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "adelantos")
public class AdelantosDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADELANTO", unique = true)
    private int ID_ADELANTO;

    private double CNT_ADELANTO;
    private LocalDate FECHA_ADELANTO;
    private String ESTADO_ADELANTO;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private EmpleadoDTO ID_EMPLEADO;
}

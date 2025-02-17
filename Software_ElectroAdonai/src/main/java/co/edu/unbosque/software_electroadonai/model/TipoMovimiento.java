package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tipo_movimiento")
public class TipoMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_TIPO_MOVIMIENTO", unique = true)
    private int ID_TIPO_PRODUCTO;
    @Column(name="NOMBRE_MOVIMIENTO")
    private String NOMBRE_MOVIMIENTO;
}


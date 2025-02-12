package co.edu.unbosque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bodega")
public class BodegaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID_BODEGA", unique = true)
    private int ID_BODEGA;

    private String NUM_BODEGA;

    private int CNT_PRODUCTOS;

    private String ESTADO;
}
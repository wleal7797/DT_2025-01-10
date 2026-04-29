package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bodega")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID_BODEGA", unique = true)
    private int ID_BODEGA;
    @Column(name="N_BODEGA")
    private String N_BODEGA;
    @Column(name="CNT_PRODUCTOS")
    private int CNT_PRODUCTOS;
    @Column(name="ESTADO")
    private String ESTADO;
}
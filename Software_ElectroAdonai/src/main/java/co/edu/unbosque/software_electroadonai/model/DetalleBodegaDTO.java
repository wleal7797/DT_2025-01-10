package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_bodega")
public class DetalleBodegaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_BODEGA", unique = true)
    private int ID_DETALLE_BODEGA;

    private int CNT_PRODUCTO_BODEGA;


    @Column(name = "ID_PRODUCTO")
    private int PRODUCTO;


    @Column(name = "ID_BODEGA")
    private int ID_BODEGA;
}

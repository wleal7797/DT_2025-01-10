package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tipo_producto")
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_TIPO_PRODUCTO", unique = true)
    private int ID_TIPO_PRODUCTO;
    @Column(name="NOMBRE_TIPO")
    private String NOMBRE_TIPO;
}

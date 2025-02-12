package co.edu.unbosque.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class ProductoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_PRODUCTO", unique = true)
    private int ID_PRODUCTO;

    private String NOMBRE_PRODUCTO;

    private String REFERENCIA_PRODUCTO;

    private String TIPO_PRODUCTO;

    private long COSTO_PRODUCTO;

    private int EXISTENCIAS_PRODUCTO;

}

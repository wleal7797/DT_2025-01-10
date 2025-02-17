package co.edu.unbosque.software_electroadonai.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_PRODUCTO", unique = true)
    private int ID_PRODUCTO;
    @Column(name="NOMBRE_PRODUCTO")
    private String NOMBRE_PRODUCTO;
    @Column(name="REFERENCIA")
    private String REFERENCIA;
    @Column(name="COSTO")
    private long COSTO;
    @Column(name="EXISTENCIAS")
    private int EXISTENCIAS;
    @ManyToOne
    @JoinColumn(name="ID_TIPO_PRODUCTO")
    private TipoProducto ID_TIPO_PRODUCTO;
}

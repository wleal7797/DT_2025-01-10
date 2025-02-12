package co.edu.unbosque.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="empleado")
public class EmpleadoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_EMPLEADO", unique = true)
    private int ID_EMPLEADO;

    private String NOMBRE_EMPLEADO;

    private String DOCUMENTO_EMPLEADO;

    private String TELEFONO_EMPLEADO;

}

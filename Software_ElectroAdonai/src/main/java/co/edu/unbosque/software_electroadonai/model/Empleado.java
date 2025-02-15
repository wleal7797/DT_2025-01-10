package co.edu.unbosque.software_electroadonai.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_EMPLEADO", unique = true)
    private int ID_EMPLEADO;
    @Column(name="N_DOCUMENTO")
    private String N_DOCUMENTO;
    @Column(name="NOMBRE_EMPLEADO")
    private String NOMBRE_EMPLEADO;
    @Column(name="TELEFONO_EMPLEADO")
    private String TELEFONO_EMPLEADO;

}


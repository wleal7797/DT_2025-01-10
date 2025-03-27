package co.edu.unbosque.software_electroadonai.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_USUARIO", unique = true)
    private int ID_USUARIO;
    @Column(name="NOMBRE_USUARIO")
    private String NOMBRE_USUARIO;
    @Column(name="CONTRASENA")
    private String CONTRASENA;
    //private String privilegio;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;
}

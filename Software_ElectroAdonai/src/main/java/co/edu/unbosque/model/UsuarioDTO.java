package co.edu.unbosque.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class UsuarioDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID_USUARIO", unique = true)
    private int ID_USUARIO;

    private String NOMBRE_USUARIO;

    private String CONTRASENA_USUARIO;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private EmpleadoDTO ID_EMPLEADO;

}

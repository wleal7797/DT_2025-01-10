package co.edu.unbosque.software_electroadonai.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.MappedCollection;

@Data
@Table(name= "usuario")
public class Usuario {
    @Id
    @Column(name= "ID_USUARIO")
    private int ID_USUARIO;

    @Column(name= "NOMBRE_USUARIO")
    private String NOMBRE_USUARIO;

    @Column(name= "CONTRASENA")
    private String CONTRASENA;

    @Column(name= "ID_EMPLEADO")
    private int ID_EMPLEADO;

    @MappedCollection(idColumn = "ID_EMPLEADO")
    private Empleado empleado;
}

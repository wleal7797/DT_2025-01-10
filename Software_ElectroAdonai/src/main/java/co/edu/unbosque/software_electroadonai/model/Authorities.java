package co.edu.unbosque.software_electroadonai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {

//    @Column(name = "username", unique = true)
//    private String username;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

    @OneToOne
    @JoinColumn(name = "username")
    private Users user;
    
}

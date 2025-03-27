package co.edu.unbosque.software_electroadonai.config;

import java.io.Serializable;

public interface UserDetails extends Serializable {
    String getUsername();
    String getPassword();
}

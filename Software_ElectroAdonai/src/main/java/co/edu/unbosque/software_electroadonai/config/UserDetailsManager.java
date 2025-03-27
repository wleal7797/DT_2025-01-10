package co.edu.unbosque.software_electroadonai.config;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsManager extends UserDetailsService {
    void createUser(UserDetails user);
    void updateUser(UserDetails user);
    void deleteUser(String user);
    void changePassword(String oldPassword, String newPassword);
    boolean userExists(String username);
}

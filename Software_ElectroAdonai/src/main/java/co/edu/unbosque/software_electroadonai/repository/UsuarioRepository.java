package co.edu.unbosque.software_electroadonai.repository;


import co.edu.unbosque.software_electroadonai.model.Users;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Users, Integer> {

    Users findByUsername(String username);

}
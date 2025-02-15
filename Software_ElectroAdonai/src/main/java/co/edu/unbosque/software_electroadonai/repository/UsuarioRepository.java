package co.edu.unbosque.software_electroadonai.repository;


import co.edu.unbosque.software_electroadonai.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
}
package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Users saveOrUpdate(Users users) {
        return usuarioRepository.save(users);
    }

    public List<Users> getAllUsuarios() {
        return (List<Users>) usuarioRepository.findAll();
    }

    public Optional<Users> getUsuarioById(int id) {
        return usuarioRepository.findById(id);
    }

    public void deleteUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

}

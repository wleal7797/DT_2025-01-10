package co.edu.unbosque.software_electroadonai.services;


import co.edu.unbosque.software_electroadonai.model.Usuario;
import co.edu.unbosque.software_electroadonai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveOrUpdate(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(int id) {

        return usuarioRepository.findById(id);
    }

    public void deleteUsuario(int id) {
        usuarioRepository.deleteById(id);
    }
}

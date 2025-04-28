package co.edu.unbosque.software_electroadonai.controller;

<<<<<<< HEAD
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import co.edu.unbosque.software_electroadonai.model.Authorities;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.AutoridadesDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
=======
import co.edu.unbosque.software_electroadonai.model.Usuario;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> parent of 585f167 (Merge branch 'master' of https://github.com/wleal7797/DT_2025-01-10)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;
<<<<<<< HEAD
    @Autowired
    private AutoridadesDAO autoridadesDAO;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
=======
>>>>>>> parent of 585f167 (Merge branch 'master' of https://github.com/wleal7797/DT_2025-01-10)

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
<<<<<<< HEAD
    public String formularioRegistro(Model model) {
        model.addAttribute(new Users());
        model.addAttribute(new Authorities());
=======
    public String formularioRegistro() {
>>>>>>> parent of 585f167 (Merge branch 'master' of https://github.com/wleal7797/DT_2025-01-10)
        return "usuario-form";
    }
    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Users user, @ModelAttribute Authorities authorities) {

        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        usuarioDAO.saveOrUpdate(user);
        authorities.setUsername(user.getUsername());
        autoridadesDAO.saveOrUpdate(authorities);
        return "redirect:/users/listar";

    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Users> users = usuarioDAO.getAllUsuarios();
        model.addAttribute("users", users);
        return "lista-usuarios";
    }
<<<<<<< HEAD

    @PostMapping("/editar")
    public String editarUsuario(@ModelAttribute Users nuevoUsuario) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(nuevoUsuario.getID_USUARIO());
        if (usuarioExistente.isPresent()) {
            Users usuario = usuarioExistente.get();
            usuario.setUsername(nuevoUsuario.getUsername());
            usuario.setPassword(nuevoUsuario.getPassword());
            usuarioDAO.saveOrUpdate(usuario);
        }
        //ResponseEntity.ok("Usuario editado correctamente");
        return "redirect:/usuarios/listar";
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);
        if (usuarioExistente.isPresent()) {
            usuarioDAO.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
=======
>>>>>>> parent of 585f167 (Merge branch 'master' of https://github.com/wleal7797/DT_2025-01-10)
}

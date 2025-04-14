package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Authorities;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.AutoridadesDAO;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private AutoridadesDAO autoridadesDAO;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute(new Users());
        model.addAttribute(new Authorities());
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
}

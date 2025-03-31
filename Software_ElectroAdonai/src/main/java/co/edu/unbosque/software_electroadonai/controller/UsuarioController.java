package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute(new Users());
        return "usuario-form";
    }

    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Users user) {

        user.setEnabled(true);
        usuarioDAO.saveOrUpdate(user);
        return "redirect:/users/listar";

    }

    // Registro Login
    @PostMapping("/registrar")
    public String registrarEmpleado(@ModelAttribute Users user) {
        user.setEnabled(true);
        usuarioDAO.saveOrUpdate(user);
        System.out.println("\nUsuario registrado\n");
        return "redirect:/login";
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Users> users = usuarioDAO.getAllUsuarios();
        model.addAttribute("users", users);
        return "lista-usuarios";
    }
}

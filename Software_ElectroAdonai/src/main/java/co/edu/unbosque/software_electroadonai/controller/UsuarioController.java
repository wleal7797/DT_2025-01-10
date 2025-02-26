package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Usuario;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "usuario-form";
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }
}

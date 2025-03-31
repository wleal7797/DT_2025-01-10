package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

// Controlador para el inicio de sesión

@Controller
class LoginController {

    @GetMapping("/")
    String index() {
        return "main";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        return "/";
    }

    @GetMapping("/registrar")
    String registrar(Model model) {
        model.addAttribute(new Users());
        return "registro-usuario";
    }

}

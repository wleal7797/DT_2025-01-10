package co.edu.unbosque.software_electroadonai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    /*
    @GetMapping("/registrar")
    String registrar(Model model) {
        model.addAttribute(new Users());
        return "registro-usuario";
    }
     */

}

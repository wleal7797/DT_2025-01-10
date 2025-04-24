package co.edu.unbosque.software_electroadonai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador para el inicio de sesión
@Controller
class LoginController {

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/index")
    String index2() {
        return "index";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/denied")
    String denied() {
        return "index";
    }

}

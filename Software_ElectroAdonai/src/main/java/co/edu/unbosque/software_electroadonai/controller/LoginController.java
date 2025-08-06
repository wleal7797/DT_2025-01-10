package co.edu.unbosque.software_electroadonai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador para el inicio de sesión
@Controller
class LoginController {

    @GetMapping("/")
    String login1() {
        return "login";
    }

    @GetMapping("/login")
    String login2() {
        return "login";
    }

    // Administrador
    @GetMapping("/main")
    String main() {
        return "main";
    }

    // Empleado
    @GetMapping("/mainVendedor")
    String mainVendedor() {
        return "mainVendedor";
    }

//    @GetMapping("/denied")
//    String denied() {
//        return "login";
//    }

}

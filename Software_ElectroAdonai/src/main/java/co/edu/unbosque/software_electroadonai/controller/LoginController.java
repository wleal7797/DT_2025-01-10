package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

// Controlador para el inicio de sesión

@Controller
class LoginController {

    // Vistas inicio de sesión

    @GetMapping("/")
    String index() {
        return "main";
    }

/*
    @GetMapping("/login")
    String login() {
        return "login";
    }
*/

    @GetMapping("/login")
    String login(Model model, @RequestParam(value = "logout", required = false) String logout) {
        if (logout != null) {
            model.addAttribute("msg", "Sesión finalizada correctamente.");
        }
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/registrar")
    String registrar(Model model) {
        model.addAttribute(new Users());
        return "registro-usuario";
    }

}

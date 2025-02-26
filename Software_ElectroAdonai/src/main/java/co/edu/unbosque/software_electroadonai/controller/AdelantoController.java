package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Adelanto;
import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.services.AdelantoDAO;
import co.edu.unbosque.software_electroadonai.services.VentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adelantos")
public class AdelantoController {
    @Autowired
    private AdelantoDAO adelantoDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "adelanto-form";
    }

    @GetMapping("/listar")
    public String listarAdelantos(Model model) {
        List<Adelanto> adelantos = adelantoDAO.getAllAdelanto();
        model.addAttribute("adelantos", adelantos);
        return "lista-adelantos";
    }
}

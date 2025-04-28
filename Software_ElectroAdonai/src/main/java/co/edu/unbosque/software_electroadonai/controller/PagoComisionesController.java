package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.PagoComisiones;
import co.edu.unbosque.software_electroadonai.services.PagoComisionesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pagoComisiones")
public class PagoComisionesController {

    @Autowired
    private PagoComisionesDAO pagoComisionesDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "pagoComision-form";
    }

    @GetMapping("/listar")
    public String listarPagoComisiones(Model model) {
        List<PagoComisiones> pagoComisiones = pagoComisionesDAO.getAllPagoComisiones();
        model.addAttribute("pagoComisiones", pagoComisiones);
        return "lista-pagoComisiones";
    }
}

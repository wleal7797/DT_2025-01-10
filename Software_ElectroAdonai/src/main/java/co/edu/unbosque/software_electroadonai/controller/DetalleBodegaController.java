package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/detalleBodega")
public class DetalleBodegaController {
    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "detallesBodega-form";
    }

    @GetMapping("/listar")
    public String listarDetallesBodega(Model model) {
        List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
        model.addAttribute("detalles", detalleBodega);
        return "lista-detalleBodegas";
    }
}

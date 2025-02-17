package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.MovimientoBodega;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.MovimientoBodegaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movimientoBodega")
public class MovimientoBodegaController {
    @Autowired
    private MovimientoBodegaDAO movimientoBodegaDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "movimientosBodega-form";
    }

    @GetMapping("/listar")
    public String listarMovimientosBodega(Model model) {
        List<MovimientoBodega> movimientoBodega = movimientoBodegaDAO.getAllMovimientosBodega();
        model.addAttribute("movimientos", movimientoBodega);
        return "lista-movimientoBodegas";
    }
}

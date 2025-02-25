package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleMovimiento;
import co.edu.unbosque.software_electroadonai.services.DetalleMovimientoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/detalleMovimiento")
public class DetalleMovimientoController {
    @Autowired
    private DetalleMovimientoDAO detalleMovimientoDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "detallesMovimiento-form";
    }

    @GetMapping("/listar")
    public String listarDetallesMovimiento(Model model) {
        List<DetalleMovimiento> detalleMovimiento = detalleMovimientoDAO.getAllDetallesMovimiento();
        model.addAttribute("detalles", detalleMovimiento);
        return "lista-detalleMovimientos";
    }
}

package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/detallesVenta")
public class DetalleVentaController {
    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "detalleVenta-form";
    }

    @GetMapping("/listar")
    public String listarDetallesVentas(Model model) {
        List<DetalleVenta> detallesVenta = detalleVentaDAO.getAllDetallesVenta();
        model.addAttribute("detallesVenta", detallesVenta);
        return "lista-detallesVenta";
    }
}


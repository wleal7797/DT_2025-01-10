package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.services.BodegaDAO;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
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
    @Autowired
    private BodegaDAO bodegaDAO;
    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        List<Bodega> bodegas = bodegaDAO.getAllBodegas();
        List<Producto> productos = productoDAO.getAllProductos();

        model.addAttribute("bodegas", bodegas);
        model.addAttribute("productos", productos);
        model.addAttribute("detalle", new DetalleBodega());

        return "detallesBodega-form";
    }
    @PostMapping("/crear")
    public String crearAdelanto(@ModelAttribute DetalleBodega detalleBodega) {
        detalleBodegaDAO.saveOrUpdate(detalleBodega);
        return "redirect:/detalleBodega/listar";
    }

    @GetMapping("/listarVendedores")
    public String listarDetallesBodegaVendedores(Model model) {
        List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
        model.addAttribute("detalles", detalleBodega);
        return "lista-detalleBodegas-vendedor";
    }
    @GetMapping("/listar")
    public String listarDetallesBodega(Model model) {
        List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
        model.addAttribute("detalles", detalleBodega);
        return "lista-detalleBodegas";
    }
}

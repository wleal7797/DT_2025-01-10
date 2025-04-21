package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.services.BodegaDAO;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/listar")
    public String listarDetallesBodega(Model model) {
        List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
        model.addAttribute("detalles", detalleBodega);
        return "lista-detalleBodegas";
    }

    @GetMapping("/listarVendedores")
    public String listarDetallesBodegaVendedores(Model model) {
        List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
        model.addAttribute("detalles", detalleBodega);
        return "lista-detalleBodegas-vendedor";
    }

    @PostMapping("/editar")
    public String editarDetalle(@ModelAttribute DetalleBodega nuevoDetalle) {
        Optional<DetalleBodega> detalleExistente = detalleBodegaDAO.getDetalleBodegaById(nuevoDetalle.getID_DETALLE_BODEGA());

        if (detalleExistente.isPresent()) {
            DetalleBodega detalle = detalleExistente.get();
            detalle.setCNT_PRODUCTO_BODEGA(nuevoDetalle.getCNT_PRODUCTO_BODEGA());

            detalleBodegaDAO.saveOrUpdate(detalle);
        }
        return "redirect:/detalleBodega/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarDetalle(@PathVariable int id) {
        Optional<DetalleBodega> detalleExistente = detalleBodegaDAO.getDetalleBodegaById(id);
        if (detalleExistente.isPresent()) {
            detalleBodegaDAO.deleteDetalleBodega(id);
            return ResponseEntity.ok("Detalle eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado");
        }
    }
}


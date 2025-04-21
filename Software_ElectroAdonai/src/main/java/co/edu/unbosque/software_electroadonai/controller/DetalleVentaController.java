package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/detallesVenta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "detalleVenta-form";
    }

    @PostMapping("/crear")
    public String crearDetalleVenta(@ModelAttribute DetalleVenta detalleVenta) {
        detalleVentaDAO.saveOrUpdate(detalleVenta);
        return "redirect:/detallesVenta/listar";
    }
    @PostMapping("/crearVendedor")
    public String crearDetalleVentaVendedor(@ModelAttribute DetalleVenta detalleVenta) {
        detalleVentaDAO.saveOrUpdate(detalleVenta);
        return "redirect:/detallesVenta/listarVendedor";
    }
    @GetMapping("/listarVendedor")
    public String listarDetalleVentaVendedor(Model model) {
        List<DetalleVenta> detallesVenta = detalleVentaDAO.getAllDetallesVenta();
        model.addAttribute("detallesVenta", detallesVenta);
        return "lista-detalleVenta-vendedor";
    }
    @GetMapping("/listar")
    public String listarDetallesVentas(Model model) {
        List<DetalleVenta> detallesVenta = detalleVentaDAO.getAllDetallesVenta();
        model.addAttribute("detallesVenta", detallesVenta);
        return "lista-detallesVenta";
    }

    @PostMapping("/editar")
    public String editarDetalleVenta(@ModelAttribute DetalleVenta nuevoDetalle) {
        Optional<DetalleVenta> existente = detalleVentaDAO.getDetalleVentaById(nuevoDetalle.getID_DETALLE_VENTA());
        if (existente.isPresent()) {
            DetalleVenta detalle = existente.get();
            detalle.setCNT_PRODUCTO_VENTA(nuevoDetalle.getCNT_PRODUCTO_VENTA());
            detalle.setPRECIO_VENTA_PRODUCTO(nuevoDetalle.getPRECIO_VENTA_PRODUCTO());
            detalleVentaDAO.saveOrUpdate(detalle);
        }
        return "redirect:/detallesVenta/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarDetalleVenta(@PathVariable int id) {
        Optional<DetalleVenta> existente = detalleVentaDAO.getDetalleVentaById(id);
        if (existente.isPresent()) {
            detalleVentaDAO.deleteDetalleVenta(id);
            return ResponseEntity.ok("Detalle de venta eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


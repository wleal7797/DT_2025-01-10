package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.model.SerialVentaProducto;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
import co.edu.unbosque.software_electroadonai.services.SerialVentaProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/serialVentaProducto")
public class SerialVentaProductoController {
    @Autowired
    private SerialVentaProductoDAO serialVentaProductoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }
    @GetMapping("/listarVendedor")
    public String listarDetallesVentasVendedor(Model model) {
        List<SerialVentaProducto> serialVentaProducto = serialVentaProductoDAO.getAllSerialVentaProducto();
        model.addAttribute("serialVentaProductos", serialVentaProducto);
        return "lista-serialVentaProducto-vendedor";
    }
    @GetMapping("/listar")
    public String listarDetallesVentas(Model model) {
        List<SerialVentaProducto> serialVentaProducto = serialVentaProductoDAO.getAllSerialVentaProducto();
        model.addAttribute("serialVentaProductos", serialVentaProducto);
        return "lista-serialVentaProducto";
    }
}

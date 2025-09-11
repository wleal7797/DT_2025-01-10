package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;
//
    @GetMapping("/main")
    public String main(Model model) {
        // 🔔 Productos en bodega "Ingreso"
        List<DetalleBodega> pendientesIngreso = detalleBodegaDAO.findByBodegaNombre("INGRESO");

        model.addAttribute("pendientesIngreso", pendientesIngreso);

        // 📊 Ventas por empleado (agrupadas)
        List<DetalleVenta> ventas = detalleVentaDAO.getAllDetallesVenta();

        Map<String, Double> ventasPorEmpleado = ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVenta().getEmpleado().getNOMBRE_EMPLEADO (),
                        Collectors.summingDouble(v -> v.getCNT_PRODUCTO_VENTA () * v.getPRECIO_VENTA_PRODUCTO())
                ));

        model.addAttribute("ventasPorEmpleado", ventasPorEmpleado);

        return "main";
    }
}

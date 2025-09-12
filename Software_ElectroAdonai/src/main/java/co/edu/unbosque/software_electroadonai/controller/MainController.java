package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
import co.edu.unbosque.software_electroadonai.services.VentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Autowired
    private VentaDAO ventaDAO;

    @GetMapping("/main")
    public String main(Model model) {
        // Obtener productos pendientes de ingreso (bodega "Ingreso")
        List<DetalleBodega> productosPendientes = detalleBodegaDAO.getAllDetallesBodega()
                .stream()
                .filter(detalle -> detalle.getBodega().getN_BODEGA().equalsIgnoreCase("INGRESO"))
                .collect(Collectors.toList());

        model.addAttribute("productosPendientes", productosPendientes);

        return "main";
    }

    // Endpoint para obtener datos de ventas por empleado (basado en Venta)
    @GetMapping("/api/ventas-por-empleado")
    @ResponseBody
    public Map<String, Object> getVentasPorEmpleado() {
        List<Venta> todasLasVentas = ventaDAO.getAllVentas();
        Map<String, Object> chartData = new HashMap<>();

        // Agrupar ventas por empleado usando el total de la venta
        Map<String, Double> ventasPorEmpleado = todasLasVentas.stream()
                .filter(venta -> venta.getEmpleado() != null)
                .collect(Collectors.groupingBy(
                        venta -> venta.getEmpleado().getNOMBRE_EMPLEADO(),
                        Collectors.summingDouble(Venta::getPRECIO_VENTA_TOTAL)
                ));

        // Si no hay datos, mostrar mensaje
        if (ventasPorEmpleado.isEmpty()) {
            List<String> labels = List.of("Sin datos disponibles");
            List<Double> ventas = List.of(0.0);
            chartData.put("labels", labels);
            chartData.put("data", ventas);
        } else {
            List<String> labels = ventasPorEmpleado.keySet().stream().collect(Collectors.toList());
            List<Double> ventas = ventasPorEmpleado.values().stream().collect(Collectors.toList());
            chartData.put("labels", labels);
            chartData.put("data", ventas);
        }

        return chartData;
    }

    @GetMapping("/api/ventas-mensuales")
    @ResponseBody
    public Map<String, Object> getVentasMensuales() {
        List<Venta> todasLasVentas = ventaDAO.getAllVentas();
        Map<String, Object> chartData = new HashMap<>();

        List<String> meses = List.of("Ene", "Feb", "Mar", "Abr", "May", "Jun",
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");

        // Calcular ventas por mes usando la fecha y total de la venta
        Map<Integer, Double> ventasPorMes = todasLasVentas.stream()
                .filter(venta -> venta.getFECHA_VENTA() != null)
                .collect(Collectors.groupingBy(
                        venta -> venta.getFECHA_VENTA().getMonthValue(),
                        Collectors.summingDouble(Venta::getPRECIO_VENTA_TOTAL)
                ));

        // Crear lista ordenada por mes
        List<Double> ventasMensuales = new java.util.ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ventasMensuales.add(ventasPorMes.getOrDefault(i, 0.0));
        }

        chartData.put("labels", meses);
        chartData.put("data", ventasMensuales);

        return chartData;
    }
}
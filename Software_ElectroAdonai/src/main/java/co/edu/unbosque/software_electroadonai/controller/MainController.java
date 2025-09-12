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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Autowired
    private VentaDAO ventaDAO;

    @GetMapping("/main")
    public String main(Model model) {
        try {
            logger.info("Iniciando carga de página principal");

            // Inicializar lista vacía por defecto
            List<DetalleBodega> productosPendientes = new ArrayList<>();

            try {
                // Obtener todos los detalles de bodega de forma segura
                List<DetalleBodega> todosLosDetalles = detalleBodegaDAO.getAllDetallesBodega();

                if (todosLosDetalles != null && !todosLosDetalles.isEmpty()) {
                    logger.info("Se encontraron {} detalles de bodega", todosLosDetalles.size());

                    // Filtrar productos pendientes de ingreso con validaciones
                    productosPendientes = todosLosDetalles.stream()
                            .filter(detalle -> {
                                try {
                                    return detalle != null &&
                                            detalle.getBodega() != null &&
                                            detalle.getBodega().getN_BODEGA() != null &&
                                            detalle.getBodega().getN_BODEGA().equalsIgnoreCase("INGRESO");
                                } catch (Exception e) {
                                    logger.warn("Error al procesar detalle de bodega: {}", e.getMessage());
                                    return false;
                                }
                            })
                            .collect(Collectors.toList());

                    logger.info("Se encontraron {} productos pendientes de ingreso", productosPendientes.size());
                } else {
                    logger.info("No se encontraron detalles de bodega");
                }
            } catch (Exception e) {
                logger.error("Error al obtener productos pendientes: {}", e.getMessage(), e);
                // En caso de error, mantenemos la lista vacía
            }

            model.addAttribute("productosPendientes", productosPendientes);
            logger.info("Modelo cargado exitosamente con {} productos pendientes", productosPendientes.size());

            return "main";

        } catch (Exception e) {
            logger.error("Error grave en el método main(): {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar la página principal");
            model.addAttribute("productosPendientes", new ArrayList<>());
            return "main";
        }
    }

    // Endpoint para obtener datos de ventas por empleado (basado en Venta)
    @GetMapping("/api/ventas-por-empleado")
    @ResponseBody
    public Map<String, Object> getVentasPorEmpleado() {
        Map<String, Object> chartData = new HashMap<>();

        try {
            logger.info("Cargando datos de ventas por empleado");

            List<Venta> todasLasVentas = ventaDAO.getAllVentas();

            if (todasLasVentas == null || todasLasVentas.isEmpty()) {
                logger.info("No se encontraron ventas");
                chartData.put("labels", List.of("Sin datos disponibles"));
                chartData.put("data", List.of(0.0));
                return chartData;
            }

            // Agrupar ventas por empleado usando el total de la venta con validaciones
            Map<String, Double> ventasPorEmpleado = todasLasVentas.stream()
                    .filter(venta -> {
                        try {
                            return venta != null &&
                                    venta.getEmpleado() != null &&
                                    venta.getEmpleado().getNOMBRE_EMPLEADO() != null &&
                                    !venta.getEmpleado().getNOMBRE_EMPLEADO().trim().isEmpty();
                        } catch (Exception e) {
                            logger.warn("Error al procesar venta para empleado: {}", e.getMessage());
                            return false;
                        }
                    })
                    .collect(Collectors.groupingBy(
                            venta -> venta.getEmpleado().getNOMBRE_EMPLEADO(),
                            Collectors.summingDouble(venta -> {
                                try {
                                    return venta.getPRECIO_VENTA_TOTAL() != 0 ? venta.getPRECIO_VENTA_TOTAL() : 0.0;
                                } catch (Exception e) {
                                    logger.warn("Error al obtener precio de venta: {}", e.getMessage());
                                    return 0.0;
                                }
                            })
                    ));

            // Si no hay datos después del filtrado, mostrar mensaje
            if (ventasPorEmpleado.isEmpty()) {
                chartData.put("labels", List.of("Sin datos disponibles"));
                chartData.put("data", List.of(0.0));
            } else {
                List<String> labels = new ArrayList<>(ventasPorEmpleado.keySet());
                List<Double> ventas = new ArrayList<>(ventasPorEmpleado.values());
                chartData.put("labels", labels);
                chartData.put("data", ventas);
            }

            logger.info("Datos de ventas por empleado cargados exitosamente: {} empleados", ventasPorEmpleado.size());

        } catch (Exception e) {
            logger.error("Error al cargar datos de ventas por empleado: {}", e.getMessage(), e);
            chartData.put("labels", List.of("Error al cargar datos"));
            chartData.put("data", List.of(0.0));
            chartData.put("error", "Error al cargar datos de ventas por empleado");
        }

        return chartData;
    }

    @GetMapping("/api/ventas-mensuales")
    @ResponseBody
    public Map<String, Object> getVentasMensuales() {
        Map<String, Object> chartData = new HashMap<>();

        try {
            logger.info("Cargando datos de ventas mensuales");

            List<Venta> todasLasVentas = ventaDAO.getAllVentas();

            List<String> meses = List.of("Ene", "Feb", "Mar", "Abr", "May", "Jun",
                    "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");

            if (todasLasVentas == null || todasLasVentas.isEmpty()) {
                logger.info("No se encontraron ventas para datos mensuales");
                // Retornar 12 meses con ceros
                List<Double> ventasMensuales = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    ventasMensuales.add(0.0);
                }
                chartData.put("labels", meses);
                chartData.put("data", ventasMensuales);
                return chartData;
            }

            // Calcular ventas por mes usando la fecha y total de la venta con validaciones
            Map<Integer, Double> ventasPorMes = todasLasVentas.stream()
                    .filter(venta -> {
                        try {
                            return venta != null && venta.getFECHA_VENTA() != null;
                        } catch (Exception e) {
                            logger.warn("Error al procesar fecha de venta: {}", e.getMessage());
                            return false;
                        }
                    })
                    .collect(Collectors.groupingBy(
                            venta -> {
                                try {
                                    return venta.getFECHA_VENTA().getMonthValue();
                                } catch (Exception e) {
                                    logger.warn("Error al obtener mes de venta: {}", e.getMessage());
                                    return 1; // Enero por defecto
                                }
                            },
                            Collectors.summingDouble(venta -> {
                                try {
                                    return venta.getPRECIO_VENTA_TOTAL() != 0 ? venta.getPRECIO_VENTA_TOTAL() : 0.0;
                                } catch (Exception e) {
                                    logger.warn("Error al obtener precio total de venta: {}", e.getMessage());
                                    return 0.0;
                                }
                            })
                    ));

            // Crear lista ordenada por mes (1-12)
            List<Double> ventasMensuales = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                ventasMensuales.add(ventasPorMes.getOrDefault(i, 0.0));
            }

            chartData.put("labels", meses);
            chartData.put("data", ventasMensuales);

            logger.info("Datos de ventas mensuales cargados exitosamente");

        } catch (Exception e) {
            logger.error("Error al cargar datos de ventas mensuales: {}", e.getMessage(), e);
            // Retornar 12 meses con ceros en caso de error
            List<Double> ventasMensuales = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                ventasMensuales.add(0.0);
            }
            chartData.put("labels", List.of("Ene", "Feb", "Mar", "Abr", "May", "Jun",
                    "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"));
            chartData.put("data", ventasMensuales);
            chartData.put("error", "Error al cargar datos de ventas mensuales");
        }

        return chartData;
    }
}
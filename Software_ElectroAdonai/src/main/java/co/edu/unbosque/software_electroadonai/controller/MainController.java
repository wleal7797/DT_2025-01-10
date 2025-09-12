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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public String main(Model model) {
        try {
            logger.info("=== INICIO: Carga de página principal ===");

            List<DetalleBodega> productosPendientes = new ArrayList<>();
            int totalDetalles = 0;
            String debugInfo = "";

            try {
                List<DetalleBodega> todosLosDetalles = detalleBodegaDAO.getAllDetallesBodega();
                totalDetalles = todosLosDetalles != null ? todosLosDetalles.size() : 0;

                logger.info("Total detalles de bodega obtenidos: {}", totalDetalles);

                if (todosLosDetalles != null && !todosLosDetalles.isEmpty()) {

                    // Debug: Ver todas las bodegas disponibles
                    Map<String, Integer> bodegasCount = new HashMap<>();

                    for (DetalleBodega detalle : todosLosDetalles) {
                        try {
                            if (detalle != null) {
                                if (detalle.getBodega() != null && detalle.getBodega().getN_BODEGA() != null) {
                                    String nombreBodega = detalle.getBodega().getN_BODEGA().trim();
                                    bodegasCount.put(nombreBodega, bodegasCount.getOrDefault(nombreBodega, 0) + 1);

                                    logger.debug("Procesando bodega: '{}' - Producto: '{}'",
                                            nombreBodega,
                                            detalle.getProducto() != null ? detalle.getProducto().getNOMBRE_PRODUCTO() : "Sin nombre");

                                    // Filtrar por bodega "INGRESO" (sin distinguir mayúsculas/minúsculas)
                                    if (nombreBodega.equalsIgnoreCase("INGRESO")) {
                                        productosPendientes.add(detalle);
                                        logger.info("✓ Producto pendiente agregado: {} - Cantidad: {}",
                                                detalle.getProducto() != null ? detalle.getProducto().getNOMBRE_PRODUCTO() : "Sin nombre",
                                                detalle.getCNT_PRODUCTO_BODEGA());
                                    }
                                } else {
                                    logger.warn("Detalle con bodega nula o sin nombre");
                                }
                            }
                        } catch (Exception e) {
                            logger.warn("Error procesando detalle individual: {}", e.getMessage());
                        }
                    }

                    // Log de todas las bodegas encontradas para debug
                    logger.info("=== BODEGAS ENCONTRADAS ===");
                    bodegasCount.forEach((nombre, count) ->
                            logger.info("Bodega: '{}' - Cantidad de productos: {}", nombre, count));
                    logger.info("=== FIN BODEGAS ===");

                    debugInfo = String.format("Total detalles: %d, Bodegas únicas: %d, Productos en INGRESO: %d",
                            totalDetalles, bodegasCount.size(), productosPendientes.size());

                } else {
                    logger.warn("No se encontraron detalles de bodega en la base de datos");
                    debugInfo = "No hay detalles de bodega en la base de datos";
                }

            } catch (Exception e) {
                logger.error("Error al obtener productos pendientes: {}", e.getMessage(), e);
                debugInfo = "Error al acceder a la base de datos: " + e.getMessage();
            }

            // Agregar atributos al modelo
            model.addAttribute("productosPendientes", productosPendientes);
            model.addAttribute("totalDetalles", totalDetalles);
            model.addAttribute("debugInfo", debugInfo);

            logger.info("Modelo cargado - Productos pendientes: {}", productosPendientes.size());
            logger.info("=== FIN: Carga de página principal ===");

            return "main";

        } catch (Exception e) {
            logger.error("Error crítico en main(): {}", e.getMessage(), e);
            model.addAttribute("error", "Error crítico al cargar la página: " + e.getMessage());
            model.addAttribute("productosPendientes", new ArrayList<>());
            model.addAttribute("totalDetalles", 0);
            model.addAttribute("debugInfo", "Error crítico del sistema");
            return "main";
        }
    }

    @GetMapping("/api/ventas-por-empleado")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> getVentasPorEmpleado() {
        Map<String, Object> chartData = new HashMap<>();

        try {
            logger.info("=== INICIO: API Ventas por Empleado ===");

            List<Venta> todasLasVentas = ventaDAO.getAllVentas();
            logger.info("Total ventas obtenidas de BD: {}", todasLasVentas != null ? todasLasVentas.size() : 0);

            if (todasLasVentas == null || todasLasVentas.isEmpty()) {
                logger.warn("No hay ventas en la base de datos");
                chartData.put("labels", List.of("Sin datos"));
                chartData.put("data", List.of(0.0));
                chartData.put("message", "No hay ventas registradas");
                chartData.put("status", "no_data");
                return chartData;
            }

            Map<String, Double> ventasPorEmpleado = new HashMap<>();
            int ventasProcesadas = 0;
            int ventasConError = 0;

            for (Venta venta : todasLasVentas) {
                try {
                    if (venta != null && venta.getEmpleado() != null) {
                        String nombreEmpleado = venta.getEmpleado().getNOMBRE_EMPLEADO();

                        if (nombreEmpleado != null && !nombreEmpleado.trim().isEmpty()) {
                            double precioVenta = 0.0;

                            // Verificar si el precio no es 0 o nulo
                            if (venta.getPRECIO_VENTA_TOTAL() != 0) {
                                precioVenta = venta.getPRECIO_VENTA_TOTAL();
                            }

                            ventasPorEmpleado.merge(nombreEmpleado.trim(), precioVenta, Double::sum);
                            ventasProcesadas++;

                            logger.debug("Venta procesada - Empleado: '{}', Precio: {}", nombreEmpleado.trim(), precioVenta);
                        } else {
                            logger.debug("Venta sin nombre de empleado válido");
                            ventasConError++;
                        }
                    } else {
                        logger.debug("Venta nula o sin empleado");
                        ventasConError++;
                    }
                } catch (Exception e) {
                    logger.warn("Error procesando venta individual: {}", e.getMessage());
                    ventasConError++;
                }
            }

            logger.info("Procesamiento completado - Procesadas: {}, Con error: {}", ventasProcesadas, ventasConError);

            if (ventasPorEmpleado.isEmpty()) {
                logger.warn("No se pudieron procesar ventas válidas por empleado");
                chartData.put("labels", List.of("Error en datos"));
                chartData.put("data", List.of(0.0));
                chartData.put("message", "No se pudieron procesar las ventas");
                chartData.put("status", "processing_error");
            } else {
                List<String> labels = new ArrayList<>(ventasPorEmpleado.keySet());
                List<Double> ventas = new ArrayList<>(ventasPorEmpleado.values());

                chartData.put("labels", labels);
                chartData.put("data", ventas);
                chartData.put("status", "success");

                logger.info("=== RESULTADO VENTAS POR EMPLEADO ===");
                for (Map.Entry<String, Double> entry : ventasPorEmpleado.entrySet()) {
                    logger.info("Empleado: '{}' - Total ventas: ${:,.2f}", entry.getKey(), entry.getValue());
                }
                logger.info("=== FIN RESULTADO ===");
            }

        } catch (Exception e) {
            logger.error("Error crítico en API ventas por empleado: {}", e.getMessage(), e);
            chartData.put("labels", List.of("Error del sistema"));
            chartData.put("data", List.of(0.0));
            chartData.put("error", e.getMessage());
            chartData.put("status", "system_error");
        }

        logger.info("=== FIN: API Ventas por Empleado ===");
        return chartData;
    }

    @GetMapping("/api/ventas-mensuales")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> getVentasMensuales() {
        Map<String, Object> chartData = new HashMap<>();
        List<String> meses = List.of("Ene", "Feb", "Mar", "Abr", "May", "Jun",
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");

        try {
            logger.info("=== INICIO: API Ventas Mensuales ===");

            List<Venta> todasLasVentas = ventaDAO.getAllVentas();
            logger.info("Total ventas para análisis mensual: {}", todasLasVentas != null ? todasLasVentas.size() : 0);

            if (todasLasVentas == null || todasLasVentas.isEmpty()) {
                List<Double> ventasMensuales = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    ventasMensuales.add(0.0);
                }
                chartData.put("labels", meses);
                chartData.put("data", ventasMensuales);
                chartData.put("message", "No hay ventas registradas");
                chartData.put("status", "no_data");
                return chartData;
            }

            Map<Integer, Double> ventasPorMes = new HashMap<>();
            int ventasProcesadas = 0;
            int ventasConError = 0;

            for (Venta venta : todasLasVentas) {
                try {
                    if (venta != null && venta.getFECHA_VENTA() != null) {
                        int mes = venta.getFECHA_VENTA().getMonthValue();
                        double precio = 0.0;

                        if (venta.getPRECIO_VENTA_TOTAL() != 0) {
                            precio = venta.getPRECIO_VENTA_TOTAL();
                        }

                        ventasPorMes.merge(mes, precio, Double::sum);
                        ventasProcesadas++;

                        logger.debug("Venta mensual - Fecha: {}, Mes: {}, Precio: {}",
                                venta.getFECHA_VENTA(), mes, precio);
                    } else {
                        logger.debug("Venta sin fecha válida");
                        ventasConError++;
                    }
                } catch (Exception e) {
                    logger.warn("Error procesando fecha de venta: {}", e.getMessage());
                    ventasConError++;
                }
            }

            logger.info("Procesamiento mensual - Procesadas: {}, Con error: {}", ventasProcesadas, ventasConError);

            // Crear array ordenado por mes (1-12)
            List<Double> ventasMensuales = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                Double ventaDelMes = ventasPorMes.getOrDefault(i, 0.0);
                ventasMensuales.add(ventaDelMes);
                logger.debug("Mes {} ({}): ${:,.2f}", i, meses.get(i-1), ventaDelMes);
            }

            chartData.put("labels", meses);
            chartData.put("data", ventasMensuales);
            chartData.put("status", "success");

            // Calcular total para log
            double totalAnual = ventasMensuales.stream().mapToDouble(Double::doubleValue).sum();
            logger.info("Total ventas anuales: ${:,.2f}", totalAnual);

        } catch (Exception e) {
            logger.error("Error crítico en API ventas mensuales: {}", e.getMessage(), e);
            List<Double> ventasMensuales = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                ventasMensuales.add(0.0);
            }
            chartData.put("labels", meses);
            chartData.put("data", ventasMensuales);
            chartData.put("error", e.getMessage());
            chartData.put("status", "system_error");
        }

        logger.info("=== FIN: API Ventas Mensuales ===");
        return chartData;
    }

    // Endpoint adicional para debug y diagnóstico
    @GetMapping("/api/debug")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> debugInfo() {
        Map<String, Object> debug = new HashMap<>();

        try {
            logger.info("=== INICIO: Debug Info ===");

            // Info básica
            debug.put("timestamp", System.currentTimeMillis());
            debug.put("status", "OK");

            // Verificar DetalleBodega
            try {
                List<DetalleBodega> detalles = detalleBodegaDAO.getAllDetallesBodega();
                debug.put("totalDetallesBodega", detalles != null ? detalles.size() : 0);

                if (detalles != null && !detalles.isEmpty()) {
                    // Contar por bodegas
                    Map<String, Long> bodegas = detalles.stream()
                            .filter(d -> d.getBodega() != null && d.getBodega().getN_BODEGA() != null)
                            .collect(Collectors.groupingBy(
                                    d -> d.getBodega().getN_BODEGA(),
                                    Collectors.counting()
                            ));
                    debug.put("bodegas", bodegas);

                    // Productos en INGRESO específicamente
                    long productosIngreso = detalles.stream()
                            .filter(d -> d.getBodega() != null &&
                                    d.getBodega().getN_BODEGA() != null &&
                                    d.getBodega().getN_BODEGA().equalsIgnoreCase("INGRESO"))
                            .count();
                    debug.put("productosEnIngreso", productosIngreso);
                }
            } catch (Exception e) {
                debug.put("errorDetallesBodega", e.getMessage());
            }

            // Verificar Ventas
            try {
                List<Venta> ventas = ventaDAO.getAllVentas();
                debug.put("totalVentas", ventas != null ? ventas.size() : 0);

                if (ventas != null && !ventas.isEmpty()) {
                    // Contar empleados únicos
                    long empleadosUnicos = ventas.stream()
                            .filter(v -> v.getEmpleado() != null && v.getEmpleado().getNOMBRE_EMPLEADO() != null)
                            .map(v -> v.getEmpleado().getNOMBRE_EMPLEADO())
                            .distinct()
                            .count();
                    debug.put("empleadosConVentas", empleadosUnicos);

                    // Total de ventas
                    double totalVentas = ventas.stream()
                            .mapToDouble(v -> v.getPRECIO_VENTA_TOTAL())
                            .sum();
                    debug.put("totalMontoVentas", totalVentas);
                }
            } catch (Exception e) {
                debug.put("errorVentas", e.getMessage());
            }

        } catch (Exception e) {
            debug.put("error", e.getMessage());
            debug.put("status", "ERROR");
        }

        logger.info("Debug info generado: {}", debug);
        logger.info("=== FIN: Debug Info ===");

        return debug;
    }
}
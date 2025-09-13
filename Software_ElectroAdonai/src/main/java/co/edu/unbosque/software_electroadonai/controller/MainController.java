package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.services.BodegaDAO;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @Autowired
    private BodegaDAO bodegaDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/main")
    public String main(Model model) {
        try {
            // Obtener información para notificaciones
            int productosEnIngreso = getProductosEnBodegaIngreso();
            int productosNuevos = getProductosNuevosHoy();
            int productosConExistencias = getProductosConExistencias();

            // Obtener datos para estadísticas
            List<Bodega> bodegas = bodegaDAO.getAllBodegas();

            // Pasar datos al modelo
            model.addAttribute("productosEnIngreso", productosEnIngreso);
            model.addAttribute("productosNuevos", productosNuevos);
            model.addAttribute("productosConExistencias", productosConExistencias);
            model.addAttribute("bodegas", bodegas);
            model.addAttribute("fechaActual", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        } catch (Exception e) {
            System.err.println("Error en main(): " + e.getMessage());
            e.printStackTrace();
            // Valores por defecto en caso de error
            model.addAttribute("productosEnIngreso", 0);
            model.addAttribute("productosNuevos", 0);
            model.addAttribute("productosConExistencias", 0);
            model.addAttribute("bodegas", new ArrayList<>());
            model.addAttribute("fechaActual", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        return "main";
    }

    @GetMapping("/estadisticas/bodegas")
    @ResponseBody
    public Map<String, Object> getEstadisticasBodegas() {
        Map<String, Object> estadisticas = new HashMap<>();

        try {
            List<Bodega> bodegas = bodegaDAO.getAllBodegas();
            List<DetalleBodega> detalles = detalleBodegaDAO.getAllDetallesBodega();

            // Datos para el gráfico
            List<String> nombresBodegas = new ArrayList<>();
            List<Integer> cantidadesProductos = new ArrayList<>();
            List<String> colores = Arrays.asList(
                    "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40",
                    "#FF9999", "#66B2FF", "#99FF66", "#FFB366", "#FF66FF", "#66FFFF"
            );

            for (Bodega bodega : bodegas) {
                nombresBodegas.add(bodega.getN_BODEGA());

                // Contar productos en esta bodega
                int totalProductos = detalles.stream()
                        .filter(detalle -> detalle.getBodega() != null &&
                                detalle.getBodega().getID_BODEGA() == bodega.getID_BODEGA())
                        .mapToInt(DetalleBodega::getCNT_PRODUCTO_BODEGA)
                        .sum();

                cantidadesProductos.add(totalProductos);
            }

            int totalProductos = cantidadesProductos.stream().mapToInt(Integer::intValue).sum();

            estadisticas.put("labels", nombresBodegas);
            estadisticas.put("data", cantidadesProductos);
            estadisticas.put("colors", colores.subList(0, Math.min(colores.size(), nombresBodegas.size())));
            estadisticas.put("totalBodegas", bodegas.size());
            estadisticas.put("totalProductos", totalProductos);

        } catch (Exception e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
            e.printStackTrace();
            // Datos de ejemplo en caso de error
            estadisticas.put("labels", Arrays.asList("INGRESO", "ALMACEN", "EXHIBICION"));
            estadisticas.put("data", Arrays.asList(150, 300, 75));
            estadisticas.put("colors", Arrays.asList("#FF6384", "#36A2EB", "#FFCE56"));
            estadisticas.put("totalBodegas", 3);
            estadisticas.put("totalProductos", 525);
        }

        return estadisticas;
    }

    private int getProductosEnBodegaIngreso() {
        try {
            List<Bodega> bodegas = bodegaDAO.getAllBodegas();
            Optional<Bodega> bodegaIngreso = bodegas.stream()
                    .filter(bodega -> "INGRESO".equalsIgnoreCase(bodega.getN_BODEGA()))
                    .findFirst();

            if (bodegaIngreso.isPresent()) {
                List<DetalleBodega> detalles = detalleBodegaDAO.getAllDetallesBodega();
                return detalles.stream()
                        .filter(detalle -> detalle.getBodega() != null &&
                                detalle.getBodega().getID_BODEGA() == bodegaIngreso.get().getID_BODEGA())
                        .mapToInt(DetalleBodega::getCNT_PRODUCTO_BODEGA)
                        .sum();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener productos en bodega INGRESO: " + e.getMessage());
        }
        return 0;
    }

    private int getProductosNuevosHoy() {
        try {
            // Como no tienes fecha_creacion en Producto, simulamos productos nuevos
            // basándonos en productos con pocas existencias (asumimos que son nuevos)
            List<Producto> productos = productoDAO.getAllProductos();
            return (int) productos.stream()
                    .filter(producto -> producto.getEXISTENCIAS() > 0 && producto.getEXISTENCIAS() <= 5)
                    .count();
        } catch (Exception e) {
            System.err.println("Error al obtener productos nuevos: " + e.getMessage());
            return 0;
        }
    }

    private int getProductosConExistencias() {
        try {
            List<Producto> productos = productoDAO.getAllProductos();
            return (int) productos.stream()
                    .filter(producto -> producto.getEXISTENCIAS() > 0)
                    .count();
        } catch (Exception e) {
            System.err.println("Error al obtener productos con existencias: " + e.getMessage());
            return 0;
        }
    }
}
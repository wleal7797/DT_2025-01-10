package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.services.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/detalleMovimiento")
public class DetalleMovimientoController {

    @Autowired
    private DetalleMovimientoDAO detalleMovimientoDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private BodegaDAO bodegaDAO;

    @Autowired
    private MovimientoBodegaDAO movimientoBodegaDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Autowired
    private DetalleBodegaDAO detalleBodegaDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("detalleMovimiento", new DetalleMovimiento());
        model.addAttribute("productos", productoDAO.getAllProductos());
        model.addAttribute("bodegas", bodegaDAO.getAllBodegas());
        model.addAttribute("movimientos", movimientoBodegaDAO.getAllMovimientosBodega());
        model.addAttribute("empleados", empleadoDAO.getAllEmpleados());

        return "detalleMovimiento-form";
    }

    @GetMapping("/listarBodegaDetalle")
    public void tablaDetalleBodega(Model model) {
        // Implementación según necesidades
    }

    @PostMapping("/crear")
    public String crearDetalleMovimiento(
            @ModelAttribute DetalleMovimiento detalle,
            @RequestParam("FECHA_MOVIMIENTO") String fechaStr,
            @RequestParam("idTipoMovimiento") int idTipoMovimiento,
            @RequestParam("idEmpleado") int idEmpleado,
            @RequestParam("ID_BODEGA_ORIGEN") int idBodegaOrigen,
            @RequestParam("ID_BODEGA_DESTINO") int idBodegaDestino,
            @RequestParam("ID_PRODUCTO") int idProducto,
            @RequestParam("CNT_PRODUCTO_MOVIMIENTO") int cantidadMovimiento,
            RedirectAttributes redirectAttributes) {

        try {
            // Validaciones básicas
            if (cantidadMovimiento <= 0) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("La cantidad debe ser mayor a 0", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            if (idBodegaOrigen == idBodegaDestino) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("La bodega origen y destino no pueden ser la misma", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Validar fecha
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Formato de fecha inválido", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Validar que las entidades existan
            Optional<Bodega> bodegaOrigenOpt = bodegaDAO.getBodegaById(idBodegaOrigen);
            Optional<Bodega> bodegaDestinoOpt = bodegaDAO.getBodegaById(idBodegaDestino);
            Optional<Producto> productoOpt = productoDAO.getProductoById(idProducto);
            Optional<Empleado> empleadoOpt = empleadoDAO.getEmpleadoById(idEmpleado);

            if (!bodegaOrigenOpt.isPresent()) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Bodega origen no encontrada", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            if (!bodegaDestinoOpt.isPresent()) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Bodega destino no encontrada", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            if (!productoOpt.isPresent()) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Producto no encontrado", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            if (!empleadoOpt.isPresent()) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Empleado no encontrado", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Validar stock disponible antes de crear el movimiento
            boolean stockSuficiente = validarStockDisponible(idProducto, idBodegaOrigen, cantidadMovimiento);
            if (!stockSuficiente) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("No hay suficientes unidades del producto en la bodega origen para realizar este movimiento", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Crear el movimiento
            MovimientoBodega nuevoMovimiento = new MovimientoBodega();
            nuevoMovimiento.setFECHA_MOVIMIENTO(fecha);
            nuevoMovimiento.setTIPO_MOVIMIENTO(entityManager.find(TipoMovimiento.class, idTipoMovimiento));
            nuevoMovimiento.setEmpleado(empleadoOpt.get());

            movimientoBodegaDAO.saveOrUpdate(nuevoMovimiento);

            // Crear el detalle del movimiento
            detalle.setCNT_PRODUCTO_MOVIMIENTO(cantidadMovimiento);
            detalle.setID_MOVIMIENTO_BODEGA(nuevoMovimiento);
            detalle.setID_BODEGA_ORIGEN(bodegaOrigenOpt.get());
            detalle.setID_BODEGA_DESTINO(bodegaDestinoOpt.get());
            detalle.setProducto(productoOpt.get());

            detalleMovimientoDAO.saveOrUpdate(detalle);

            redirectAttributes.addAttribute("success",
                    URLEncoder.encode("Movimiento creado exitosamente", StandardCharsets.UTF_8));

            return "redirect:/detalleMovimiento/listar";

        } catch (org.hibernate.exception.GenericJDBCException e) {
            String mensajeError = "Error en la base de datos";

            if (e.getMessage() != null && e.getMessage().contains("no hay suficientes unidades")) {
                mensajeError = "No hay suficientes unidades del producto en la bodega origen para realizar este movimiento";
            } else if (e.getMessage() != null && e.getMessage().contains("violates foreign key constraint")) {
                mensajeError = "Error de referencia: Verifique que todos los datos seleccionados sean válidos";
            }

            redirectAttributes.addAttribute("error",
                    URLEncoder.encode(mensajeError, StandardCharsets.UTF_8));
            e.printStackTrace();
            return "redirect:/detalleMovimiento/listar";

        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addAttribute("error",
                    URLEncoder.encode("Error de integridad de datos: Verifique que todos los campos sean válidos", StandardCharsets.UTF_8));
            e.printStackTrace();
            return "redirect:/detalleMovimiento/listar";

        } catch (Exception e) {
            String mensajeError = "Error inesperado al crear el movimiento";

            if (e.getMessage() != null && e.getMessage().contains("no hay suficientes unidades")) {
                mensajeError = "No hay suficientes unidades del producto en la bodega origen";
            }

            redirectAttributes.addAttribute("error",
                    URLEncoder.encode(mensajeError, StandardCharsets.UTF_8));
            e.printStackTrace();
            return "redirect:/detalleMovimiento/listar";
        }
    }

    /**
     * Método para validar si hay stock suficiente en la bodega origen
     */
    private boolean validarStockDisponible(int idProducto, int idBodegaOrigen, int cantidadRequerida) {
        try {
            // Buscar el detalle de bodega para el producto y bodega específicos
            List<DetalleBodega> detallesBodega = detalleBodegaDAO.getAllDetallesBodega();

            for (DetalleBodega detalle : detallesBodega) {
                if (detalle.getProducto() != null &&
                        detalle.getBodega() != null &&
                        detalle.getProducto().getID_PRODUCTO() == idProducto &&
                        detalle.getBodega().getID_BODEGA() == idBodegaOrigen) {

                    return detalle.getCNT_PRODUCTO_BODEGA() >= cantidadRequerida;
                }
            }

            // Si no se encuentra el producto en la bodega, no hay stock
            return false;

        } catch (Exception e) {
            // En caso de error, devolver false por seguridad
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/listar")
    public String listarDetallesMovimiento(Model model) {
        try {
            List<TipoMovimiento> tiposMovimiento = entityManager.createQuery("SELECT t FROM TipoMovimiento t", TipoMovimiento.class)
                    .getResultList();

            model.addAttribute("productos", productoDAO.getAllProductos());
            model.addAttribute("bodegas", bodegaDAO.getAllBodegas());
            model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
            model.addAttribute("tiposMovimiento", tiposMovimiento);
            model.addAttribute("detalles", detalleMovimientoDAO.getAllDetallesMovimiento());

            List<DetalleBodega> detalleBodega = detalleBodegaDAO.getAllDetallesBodega();
            List<Bodega> bodegas = bodegaDAO.getAllBodegas();
            List<Producto> productos = productoDAO.getAllProductos();

            model.addAttribute("bodegas", bodegas);
            model.addAttribute("productos", productos);
            model.addAttribute("detallesBodega", detalleBodega);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los datos");
        }

        return "lista-detalleMovimientos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleMovimiento(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            Optional<DetalleMovimiento> existente = detalleMovimientoDAO.getDetalleMovimientoById(id);
            if (existente.isPresent()) {
                // Verificar si el movimiento es reciente (ejemplo: últimos 30 días)
                DetalleMovimiento detalle = existente.get();
                LocalDate fechaMovimiento = detalle.getID_MOVIMIENTO_BODEGA().getFECHA_MOVIMIENTO();
                LocalDate fechaLimite = LocalDate.now().minusDays(30);

                if (fechaMovimiento.isBefore(fechaLimite)) {
                    redirectAttributes.addAttribute("error",
                            URLEncoder.encode("No se pueden eliminar movimientos de más de 30 días de antigüedad", StandardCharsets.UTF_8));
                    return "redirect:/detalleMovimiento/listar";
                }

                detalleMovimientoDAO.deleteDetalleMovimiento(id);
                redirectAttributes.addAttribute("success",
                        URLEncoder.encode("Movimiento eliminado exitosamente", StandardCharsets.UTF_8));
            } else {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Movimiento no encontrado", StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error",
                    URLEncoder.encode("Error al eliminar el movimiento", StandardCharsets.UTF_8));
            e.printStackTrace();
        }

        return "redirect:/detalleMovimiento/listar";
    }

    /**
     * Endpoint para validar stock disponible vía AJAX
     */
    @PostMapping("/validarStock")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validarStock(
            @RequestParam int idProducto,
            @RequestParam int idBodegaOrigen,
            @RequestParam int cantidad) {

        Map<String, Object> response = new HashMap<>();

        try {
            boolean stockSuficiente = validarStockDisponible(idProducto, idBodegaOrigen, cantidad);

            if (!stockSuficiente) {
                response.put("valido", false);
                response.put("mensaje", "No hay suficiente stock en la bodega origen");

                // Obtener stock actual
                int stockActual = obtenerStockActual(idProducto, idBodegaOrigen);
                response.put("stockActual", stockActual);
            } else {
                response.put("valido", true);
                response.put("mensaje", "Stock suficiente");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("valido", false);
            response.put("mensaje", "Error al validar el stock");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Obtener stock actual de un producto en una bodega específica
     */
    private int obtenerStockActual(int idProducto, int idBodegaOrigen) {
        try {
            List<DetalleBodega> detallesBodega = detalleBodegaDAO.getAllDetallesBodega();

            for (DetalleBodega detalle : detallesBodega) {
                if (detalle.getProducto() != null &&
                        detalle.getBodega() != null &&
                        detalle.getProducto().getID_PRODUCTO() == idProducto &&
                        detalle.getBodega().getID_BODEGA() == idBodegaOrigen) {

                    return detalle.getCNT_PRODUCTO_BODEGA();
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Endpoint para actualizar un detalle de movimiento
     */
    @PostMapping("/actualizar")
    public String actualizarDetalleMovimiento(
            @RequestParam int id,
            @RequestParam String fecha,
            @RequestParam int cantidad,
            RedirectAttributes redirectAttributes) {

        try {
            Optional<DetalleMovimiento> detalleOpt = detalleMovimientoDAO.getDetalleMovimientoById(id);

            if (!detalleOpt.isPresent()) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("Movimiento no encontrado", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            DetalleMovimiento detalle = detalleOpt.get();

            // Verificar si el movimiento es muy antiguo (más de 30 días)
            LocalDate fechaMovimiento = detalle.getID_MOVIMIENTO_BODEGA().getFECHA_MOVIMIENTO();
            LocalDate fechaLimite = LocalDate.now().minusDays(30);

            if (fechaMovimiento.isBefore(fechaLimite)) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("No se pueden editar movimientos de más de 30 días de antigüedad", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Validar nueva cantidad
            if (cantidad <= 0) {
                redirectAttributes.addAttribute("error",
                        URLEncoder.encode("La cantidad debe ser mayor a 0", StandardCharsets.UTF_8));
                return "redirect:/detalleMovimiento/listar";
            }

            // Validar stock si se está aumentando la cantidad
            int cantidadAnterior = detalle.getCNT_PRODUCTO_MOVIMIENTO();
            if (cantidad > cantidadAnterior) {
                int diferencia = cantidad - cantidadAnterior;
                boolean stockSuficiente = validarStockDisponible(
                        detalle.getProducto().getID_PRODUCTO(),
                        detalle.getID_BODEGA_ORIGEN().getID_BODEGA(),
                        diferencia
                );

                if (!stockSuficiente) {
                    redirectAttributes.addAttribute("error",
                            URLEncoder.encode("No hay suficiente stock para aumentar la cantidad del movimiento", StandardCharsets.UTF_8));
                    return "redirect:/detalleMovimiento/listar";
                }
            }

            // Actualizar el detalle
            detalle.setCNT_PRODUCTO_MOVIMIENTO(cantidad);

            // Actualizar fecha del movimiento si es diferente
            LocalDate nuevaFecha = LocalDate.parse(fecha);
            if (!nuevaFecha.equals(fechaMovimiento)) {
                MovimientoBodega movimiento = detalle.getID_MOVIMIENTO_BODEGA();
                movimiento.setFECHA_MOVIMIENTO(nuevaFecha);
                movimientoBodegaDAO.saveOrUpdate(movimiento);
            }

            detalleMovimientoDAO.saveOrUpdate(detalle);

            redirectAttributes.addAttribute("success",
                    URLEncoder.encode("Movimiento actualizado exitosamente", StandardCharsets.UTF_8));

        } catch (DateTimeParseException e) {
            redirectAttributes.addAttribute("error",
                    URLEncoder.encode("Formato de fecha inválido", StandardCharsets.UTF_8));
        } catch (Exception e) {
            redirectAttributes.addAttribute("error",
                    URLEncoder.encode("Error al actualizar el movimiento", StandardCharsets.UTF_8));
            e.printStackTrace();
        }

        return "redirect:/detalleMovimiento/listar";
    }

    // Manejo global de excepciones para este controlador
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String manejarErrorIntegridad(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        String mensaje = "Error de integridad de datos: Verifique que todos los campos sean válidos";
        redirectAttributes.addAttribute("error", URLEncoder.encode(mensaje, StandardCharsets.UTF_8));
        e.printStackTrace();
        return "redirect:/detalleMovimiento/listar";
    }

    @ExceptionHandler(org.hibernate.exception.GenericJDBCException.class)
    public String manejarErrorJDBC(org.hibernate.exception.GenericJDBCException e, RedirectAttributes redirectAttributes) {
        String mensaje = "Error en la base de datos";

        if (e.getMessage() != null && e.getMessage().contains("no hay suficientes unidades")) {
            mensaje = "No hay suficientes unidades del producto en la bodega origen para realizar este movimiento";
        }

        redirectAttributes.addAttribute("error", URLEncoder.encode(mensaje, StandardCharsets.UTF_8));
        e.printStackTrace();
        return "redirect:/detalleMovimiento/listar";
    }

    @ExceptionHandler(Exception.class)
    public String manejarErrorGeneral(Exception e, RedirectAttributes redirectAttributes) {
        String mensaje = "Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente";
        redirectAttributes.addAttribute("error", URLEncoder.encode(mensaje, StandardCharsets.UTF_8));
        e.printStackTrace();
        return "redirect:/detalleMovimiento/listar";
    }
}
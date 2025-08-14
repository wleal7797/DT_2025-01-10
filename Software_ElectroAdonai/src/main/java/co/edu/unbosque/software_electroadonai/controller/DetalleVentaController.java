package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/detallesVenta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private BodegaDAO bodegaDAO;

    @Autowired
    private SerialVentaProductoDAO serialVentaProductoDAO;

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    /*
        @GetMapping("/registro")
        public String mostrarFormulario(Model model) {
            model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
            model.addAttribute("productos", productoDAO.getAllProductos());
            model.addAttribute("bodegas", bodegaDAO.getAllBodegas());
            return "detalleVenta-form";
        }
    */

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
        model.addAttribute("productos", productoDAO.getAllProductos());
        model.addAttribute("bodegas", bodegaDAO.getAllBodegas());

        List<Venta> ventas = ventaDAO.getAllVentas();
        String ultimaRemision = "N/A";
        List<String> remisionesExistentes = ventas.stream()
                .map(Venta::getREMISION)
                .filter(r -> r != null && !r.trim().isEmpty())
                .toList();

        if (!ventas.isEmpty()) {
            Venta ultimaVenta = ventas.get(ventas.size() - 1);
            if (ultimaVenta.getREMISION() != null) {
                ultimaRemision = ultimaVenta.getREMISION();
            }
        }

        model.addAttribute("ultimaRemision", ultimaRemision);
        model.addAttribute("remisionesExistentes", remisionesExistentes);

        return "detalleVenta-form";
    }


    @GetMapping("/registroVendedor")
    public String mostrarFormularioVendedor(Model model) {
        model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
        model.addAttribute("productos", productoDAO.getAllProductos());
        model.addAttribute("bodegas", bodegaDAO.getAllBodegas());
        return "detalleVenta-form-vendedor";
    }

    @PostMapping("/crear")
    public String crearVentaYDetalles(@ModelAttribute VentaForm ventaForm, Model model) {
        try {
            Venta venta = new Venta();
            venta.setFECHA_VENTA(LocalDate.parse(ventaForm.getFechaVenta()));
            venta.setPRECIO_VENTA_TOTAL(ventaForm.getPrecioVentaTotal());
            venta.setREMISION(ventaForm.getRemision());
            venta.setEmpleado(empleadoDAO.getEmpleadoById(ventaForm.getIdEmpleado()).orElse(null));
            ventaDAO.saveOrUpdate(venta);

            for (DetalleVentaForm d : ventaForm.getDetalles()) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setCNT_PRODUCTO_VENTA(d.getCntProductoVenta());
                detalle.setPRECIO_VENTA_PRODUCTO(d.getPrecioVentaProducto());
                detalle.setVenta(venta);
                detalle.setProducto(productoDAO.getProductoById(d.getIdProducto()).orElse(null));
                detalle.setBodega(bodegaDAO.getBodegaById(d.getIdBodega()).orElse(null));
                detalleVentaDAO.saveOrUpdate(detalle);

                if (d.getSerialesTexto() != null && !d.getSerialesTexto().trim().isEmpty()) {
                    String[] seriales = d.getSerialesTexto().split("\\r?\\n");
                    for (String serial : seriales) {
                        SerialVentaProducto svp = new SerialVentaProducto();
                        svp.setSERIAL(serial.trim());
                        svp.setVenta(venta);
                        svp.setProducto(detalle.getProducto());
                        serialVentaProductoDAO.saveOrUpdate(svp);
                    }
                }
            }

            return "redirect:/detallesVenta/listar";

        } catch (Exception e) {
            String mensajeError = "Ocurrió un error al registrar la venta.";

            if (e.getMessage().contains("no hay suficiente inventario del producto")) {
                mensajeError = "⚠️ No hay suficiente inventario del producto seleccionado.";
            }

            model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
            model.addAttribute("productos", productoDAO.getAllProductos());
            model.addAttribute("bodegas", bodegaDAO.getAllBodegas());

            List<Venta> ventas = ventaDAO.getAllVentas();
            String ultimaRemision = "N/A";
            List<String> remisionesExistentes = ventas.stream()
                    .map(Venta::getREMISION)
                    .filter(r -> r != null && !r.trim().isEmpty())
                    .toList();

            if (!ventas.isEmpty()) {
                Venta ultimaVenta = ventas.get(ventas.size() - 1);
                if (ultimaVenta.getREMISION() != null) {
                    ultimaRemision = ultimaVenta.getREMISION();
                }
            }

            model.addAttribute("ultimaRemision", ultimaRemision);
            model.addAttribute("remisionesExistentes", remisionesExistentes);
            model.addAttribute("errorInventario", mensajeError);

            return "detalleVenta-form";
        }
    }


    @GetMapping("/listarVendedor")
    public String listarDetalleVentaVendedor(Model model) {
        List<DetalleVenta> detallesVenta = detalleVentaDAO.getAllDetallesVenta();
        model.addAttribute("detallesVenta", detallesVenta);
        return "lista-detalleVenta-vendedor";
    }

    @PostMapping("/crearVendedor")
    public String crearDetalleVentaVendedor(@ModelAttribute VentaForm ventaForm, Model model) {
        Venta venta = new Venta();
        venta.setFECHA_VENTA(LocalDate.parse(ventaForm.getFechaVenta()));
        venta.setPRECIO_VENTA_TOTAL(ventaForm.getPrecioVentaTotal());
        venta.setREMISION(ventaForm.getRemision());
        venta.setEmpleado(empleadoDAO.getEmpleadoById(ventaForm.getIdEmpleado()).orElse(null));
        ventaDAO.saveOrUpdate(venta);

        for (DetalleVentaForm d : ventaForm.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setCNT_PRODUCTO_VENTA(d.getCntProductoVenta());

            System.out.println("\nCant producto: " + d.getCntProductoVenta() + "\n");

            detalle.setPRECIO_VENTA_PRODUCTO(d.getPrecioVentaProducto());
            detalle.setVenta(venta);
            detalle.setProducto(productoDAO.getProductoById(d.getIdProducto()).orElse(null));

            System.out.println("\nID bodega: " + d.getIdBodega() + "\n");

            detalle.setBodega(bodegaDAO.getBodegaById(d.getIdBodega()).orElse(null));
            detalleVentaDAO.saveOrUpdate(detalle);

            if (d.getSerialesTexto() != null && !d.getSerialesTexto().trim().isEmpty()) {
                String[] seriales = d.getSerialesTexto().split("\\r?\\n");
                for (String serial : seriales) {
                    SerialVentaProducto svp = new SerialVentaProducto();
                    svp.setSERIAL(serial.trim());
                    svp.setVenta(venta);
                    svp.setProducto(detalle.getProducto());
                    serialVentaProductoDAO.saveOrUpdate(svp);
                }
            }
        }
        return "redirect:/detallesVenta/listarVendedor";
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

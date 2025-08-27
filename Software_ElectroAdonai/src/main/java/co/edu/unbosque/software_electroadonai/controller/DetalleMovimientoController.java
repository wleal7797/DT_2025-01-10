package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.services.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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

/*
        System.out.println(bodegas.get(1));
        System.out.println(productos.get(1));
        System.out.println(detalleBodega.get(1));
        */
    }

    @PostMapping("/crear")
    public String crearDetalleMovimiento(

            @ModelAttribute DetalleMovimiento detalle,
            @RequestParam("FECHA_MOVIMIENTO") String fechaStr,
            @RequestParam("idTipoMovimiento") int idTipoMovimiento,
            @RequestParam("idEmpleado") int idEmpleado,
            @RequestParam("ID_BODEGA_ORIGEN") int idBodegaOrigen,
            @RequestParam("ID_BODEGA_DESTINO") int idBodegaDestino,
            @RequestParam("ID_PRODUCTO") int idProducto) {

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);

            MovimientoBodega nuevoMovimiento = new MovimientoBodega();
            nuevoMovimiento.setFECHA_MOVIMIENTO(fecha);
            nuevoMovimiento.setTIPO_MOVIMIENTO(entityManager.find(TipoMovimiento.class, idTipoMovimiento));
            nuevoMovimiento.setEmpleado(empleadoDAO.getEmpleadoById(idEmpleado).get());

            movimientoBodegaDAO.saveOrUpdate(nuevoMovimiento);

            detalle.setID_MOVIMIENTO_BODEGA (nuevoMovimiento);
            detalle.setID_BODEGA_ORIGEN(bodegaDAO.getBodegaById(idBodegaOrigen).get());
            detalle.setID_BODEGA_DESTINO(bodegaDAO.getBodegaById(idBodegaDestino).get());
            detalle.setProducto(productoDAO.getProductoById(idProducto).get());

            detalleMovimientoDAO.saveOrUpdate(detalle);

            return "redirect:/detalleMovimiento/listar";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/listar")
    public String listarDetallesMovimiento(Model model) {
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

        return "lista-detalleMovimientos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleMovimiento(@PathVariable int id) {
        Optional<DetalleMovimiento> existente = detalleMovimientoDAO.getDetalleMovimientoById(id);
        if (existente.isPresent()) {
            detalleMovimientoDAO.deleteDetalleMovimiento(id);
        }
        return "redirect:/detalleMovimiento/listar";
    }
}

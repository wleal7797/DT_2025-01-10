package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import co.edu.unbosque.software_electroadonai.services.VentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "venta-form";
    }

    @GetMapping("/listar")
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaDAO.getAllVentas();
        model.addAttribute("ventas", ventas);
        return "lista-ventas";
    }

    @GetMapping("/crear")
    public String mostrarFormularioVenta(Model model) {
        model.addAttribute("empleados", empleadoDAO.getAllEmpleados());
        return "venta_crear";
    }

    @PostMapping("/crear")
    public String crearVenta(@RequestParam("FECHA_VENTA") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
                             @RequestParam("idEmpleado") int idEmpleado) {

        Venta venta = new Venta();
        venta.setFECHA_VENTA(fecha);
        venta.setEmpleado(empleadoDAO.getEmpleadoById (idEmpleado).orElse(null));
        ventaDAO.saveOrUpdate(venta);

        return "redirect:/venta/listar";
    }
}

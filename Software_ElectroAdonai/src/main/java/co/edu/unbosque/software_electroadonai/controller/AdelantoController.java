package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Adelanto;
import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Usuario;
import co.edu.unbosque.software_electroadonai.services.AdelantoDAO;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adelantos")
public class AdelantoController {
    @Autowired
    private AdelantoDAO adelantoDAO;
    @Autowired
    private EmpleadoDAO empleadoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        List<Empleado> empleados = empleadoDAO.getAllEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("usuario", new Adelanto());
        return "adelanto-form";
    }

    @GetMapping("/listar")
    public String listarAdelantos(Model model) {
        List<Adelanto> adelantos = adelantoDAO.getAllAdelanto();
        model.addAttribute("adelantos", adelantos);
        return "lista-adelantos";
    }
}

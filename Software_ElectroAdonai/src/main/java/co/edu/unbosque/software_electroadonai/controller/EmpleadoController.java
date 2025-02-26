package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @GetMapping("/")

    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "empleado-form";
    }
    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Empleado empleado) {
        empleadoDAO.saveOrUpdate(empleado);
        return "redirect:/empleados/listar";
    }

    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoDAO.getAllEmpleados();
        model.addAttribute("empleados", empleados);
        return "lista-empleados";
    }
}




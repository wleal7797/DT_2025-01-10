package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @GetMapping("/")

    public String inicio() {
        System.out.println("⚡ejecutado");
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        System.out.println("⚡ejecutado");
        return "empleado-form";
    }

    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        System.out.println("⚡ejecutado");

        List<Empleado> empleados = empleadoDAO.getAllEmpleados();
        System.out.println("⚡ejecutado");

        model.addAttribute("empleados", empleados);
        return "lista-empleados";
    }
}




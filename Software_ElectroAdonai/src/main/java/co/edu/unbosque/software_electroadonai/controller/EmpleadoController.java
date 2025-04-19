package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoDAO empleadoDAO;
    @GetMapping("/")

    public String inicio() {
        return "main";
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
    @PostMapping("/editar")
    public String editarEmpleado(@ModelAttribute Empleado nuevoEmpleado) {
        Optional<Empleado> empleadoExistente = empleadoDAO.getEmpleadoById(nuevoEmpleado.getID_EMPLEADO());

        if (empleadoExistente.isPresent()) {
            Empleado empleado = empleadoExistente.get();
            empleado.setN_DOCUMENTO(nuevoEmpleado.getN_DOCUMENTO());
            empleado.setNOMBRE_EMPLEADO(nuevoEmpleado.getNOMBRE_EMPLEADO());
            empleado.setTELEFONO_EMPLEADO(nuevoEmpleado.getTELEFONO_EMPLEADO());

            empleadoDAO.saveOrUpdate(empleado);
        }
        return "redirect:/empleados/listar";
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable int id) {
        Optional<Empleado> empleadoExistente = empleadoDAO.getEmpleadoById(id);
        if (empleadoExistente.isPresent()) {
            empleadoDAO.deleteEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }

}









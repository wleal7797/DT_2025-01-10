package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Adelanto;
import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.AdelantoDAO;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/crear")
    public String crearAdelanto(@ModelAttribute Adelanto adelanto) {
        adelantoDAO.saveOrUpdate(adelanto);
        return "redirect:/adelantos/listar";
    }

    @GetMapping("/listar")
    public String listarAdelantos(Model model) {
        List<Adelanto> adelantos = adelantoDAO.getAllAdelanto();
        model.addAttribute("adelantos", adelantos);
        return "lista-adelantos";
    }

    @PostMapping("/editar")
    public String editarAdelanto(@ModelAttribute Adelanto nuevoAdelanto) {
        Optional<Adelanto> existente = adelantoDAO.getAdelantoById(nuevoAdelanto.getID_ADELANTO());

        if (existente.isPresent()) {
            Adelanto adelanto = existente.get();
            adelanto.setFECHA_ADELANTO(nuevoAdelanto.getFECHA_ADELANTO());
            adelanto.setCNT_ADELANTO(nuevoAdelanto.getCNT_ADELANTO());
            adelanto.setESTADO_ADELANTO(nuevoAdelanto.getESTADO_ADELANTO());
            adelantoDAO.saveOrUpdate(adelanto);
        }

        return "redirect:/adelantos/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarAdelanto(@PathVariable int id) {
        Optional<Adelanto> existente = adelantoDAO.getAdelantoById(id);
        if (existente.isPresent()) {
            adelantoDAO.deleteAdelanto(id);
            return ResponseEntity.ok("Adelanto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adelanto no encontrado");
        }
    }
}


package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

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
        model.addAttribute("usuario", new Users());
        return "lista-usuarios";
    }

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute Users usuario) {
        usuarioDAO.saveOrUpdate(usuario);
        return "redirect:/users/listar";
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Users> usuarios = usuarioDAO.getAllUsuarios();
        List<Empleado> empleados = empleadoDAO.getAllEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

    @PostMapping("/editar")
    public String editarUsuario(@ModelAttribute Users nuevoUsuario) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(nuevoUsuario.getID_USUARIO());
        if (usuarioExistente.isPresent()) {
            Users usuario = usuarioExistente.get();
            usuario.setUsername(nuevoUsuario.getUsername());
            usuario.setPassword(nuevoUsuario.getPassword());
            usuarioDAO.saveOrUpdate(usuario);
        }
        return "redirect:/users/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);
        if (usuarioExistente.isPresent()) {
            usuarioDAO.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}


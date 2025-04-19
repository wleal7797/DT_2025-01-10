package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Usuario;
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
@RequestMapping("/usuarios")
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
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Usuario usuario) {
        usuarioDAO.saveOrUpdate(usuario);
        return "redirect:/usuarios/listar";
    }
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }
    @PostMapping("/editar")
    public String editarUsuario(@ModelAttribute Usuario nuevoUsuario) {
        Optional<Usuario> usuarioExistente = usuarioDAO.getUsuarioById(nuevoUsuario.getID_USUARIO());
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNOMBRE_USUARIO (nuevoUsuario.getNOMBRE_USUARIO());
            usuario.setCONTRASENA (nuevoUsuario.getCONTRASENA());
            usuarioDAO.saveOrUpdate(usuario);
        }
        //ResponseEntity.ok("Usuario editado correctamente");
        return "redirect:/usuarios/listar";
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        Optional<Usuario> usuarioExistente = usuarioDAO.getUsuarioById(id);
        if (usuarioExistente.isPresent()) {
            usuarioDAO.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}

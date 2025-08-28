package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Authorities;
import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.AutoridadesDAO;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private AutoridadesDAO autoridadesDAO;

    private final PasswordEncoder cifrar = new BCryptPasswordEncoder();

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        List<Empleado> empleados = empleadoDAO.getAllEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("usuario", new Users());
        return "lista-usuarios";
    }

    @PostMapping("/crear")
    public String crearUsuario(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam int empleadoId,
                               @RequestParam String authority) {
        // Crear usuario
        Users usuario = new Users();
        usuario.setUsername(username);
        usuario.setPassword(cifrar.encode(password));
        usuario.setEnabled(true);

        Empleado empleado = empleadoDAO.getEmpleadoById(empleadoId).orElse(null);
        usuario.setEmpleado(empleado);

        usuarioDAO.saveOrUpdate(usuario);

        // Crear autoridad
        Authorities rol = new Authorities();
        rol.setUsername(usuario.getUsername()); // mismo username como PK
        rol.setAuthority(authority);
        autoridadesDAO.saveOrUpdate(rol);

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
    public String editarUsuario(@RequestParam int id,
                                @RequestParam String username,
                                @RequestParam(required = false) String password,
                                @RequestParam String authority) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);

        if (usuarioExistente.isPresent()) {
            Users usuario = usuarioExistente.get();
            usuario.setUsername(username);

            if (password != null && !password.isBlank()) {
                usuario.setPassword(cifrar.encode(password));
            }

            usuarioDAO.saveOrUpdate(usuario);

            // Actualizar o crear rol
            Authorities rol = autoridadesDAO.getByUsername(usuario.getUsername())
                    .orElse(new Authorities());
            rol.setUsername(usuario.getUsername());
            rol.setAuthority(authority);
            autoridadesDAO.saveOrUpdate(rol);
        }

        return "redirect:/users/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);

        if (usuarioExistente.isPresent()) {
            String username = usuarioExistente.get().getUsername();
            usuarioDAO.deleteUsuario(id);
            autoridadesDAO.deleteByUsername(username);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}

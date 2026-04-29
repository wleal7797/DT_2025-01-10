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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Users> usuarios = usuarioDAO.getAllUsuarios();

        // Crear mapa de roles para cada usuario
        Map<String, String> usuarioRoles = new HashMap<>();
        for (Users usuario : usuarios) {
            Optional<Authorities> authority = autoridadesDAO.getByUsername(usuario.getUsername());
            usuarioRoles.put(usuario.getUsername(),
                    authority.map(Authorities::getAuthority).orElse("N/A"));
        }

        model.addAttribute("empleados", empleados);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioRoles", usuarioRoles);
        model.addAttribute("usuario", new Users());
        return "lista-usuarios";
    }

    @PostMapping("/crear")
    public String crearUsuario(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam int empleadoId,
                               @RequestParam String authority) {
        try {
            // Verificar si el empleado ya tiene un usuario
            List<Users> usuarios = usuarioDAO.getAllUsuarios();
            boolean empleadoTieneUsuario = usuarios.stream()
                    .anyMatch(u -> u.getEmpleado() != null && u.getEmpleado().getID_EMPLEADO() == empleadoId);

            if (empleadoTieneUsuario) {
                // Aquí podrías agregar un mensaje de error al model
                return "redirect:/users/listar?error=empleado_ya_tiene_usuario";
            }

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
            rol.setUsername(usuario.getUsername());
            rol.setAuthority(authority);
            autoridadesDAO.saveOrUpdate(rol);

            return "redirect:/users/listar?success=usuario_creado";
        } catch (Exception e) {
            return "redirect:/users/listar?error=error_crear_usuario";
        }
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Users> usuarios = usuarioDAO.getAllUsuarios();
        List<Empleado> empleados = empleadoDAO.getAllEmpleados();

        // Crear mapa de roles para cada usuario
        Map<String, String> usuarioRoles = new HashMap<>();
        for (Users usuario : usuarios) {
            Optional<Authorities> authority = autoridadesDAO.getByUsername(usuario.getUsername());
            usuarioRoles.put(usuario.getUsername(),
                    authority.map(Authorities::getAuthority).orElse("N/A"));
        }

        model.addAttribute("empleados", empleados);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioRoles", usuarioRoles);
        return "lista-usuarios";
    }

    @PostMapping("/editar")
    public String editarUsuario(@RequestParam int id,
                                @RequestParam String username,
                                @RequestParam(required = false) String password,
                                @RequestParam String authority) {
        try {
            Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);

            if (usuarioExistente.isPresent()) {
                Users usuario = usuarioExistente.get();
                String oldUsername = usuario.getUsername();

                // Actualizar usuario
                usuario.setUsername(username);

                if (password != null && !password.isBlank()) {
                    usuario.setPassword(cifrar.encode(password));
                }

                usuarioDAO.saveOrUpdate(usuario);

                // Si cambió el username, eliminar autoridad antigua
                if (!oldUsername.equals(username)) {
                    autoridadesDAO.deleteByUsername(oldUsername);
                }

                // Actualizar o crear nueva autoridad
                Authorities rol = autoridadesDAO.getByUsername(username)
                        .orElse(new Authorities());
                rol.setUsername(username);
                rol.setAuthority(authority);
                autoridadesDAO.saveOrUpdate(rol);

                return "redirect:/users/listar?success=usuario_editado";
            } else {
                return "redirect:/users/listar?error=usuario_no_encontrado";
            }
        } catch (Exception e) {
            return "redirect:/users/listar?error=error_editar_usuario";
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        try {
            Optional<Users> usuarioExistente = usuarioDAO.getUsuarioById(id);

            if (usuarioExistente.isPresent()) {
                String username = usuarioExistente.get().getUsername();

                // Eliminar autoridad primero
                autoridadesDAO.deleteByUsername(username);

                // Luego eliminar usuario
                usuarioDAO.deleteUsuario(id);

                return ResponseEntity.ok("Usuario eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
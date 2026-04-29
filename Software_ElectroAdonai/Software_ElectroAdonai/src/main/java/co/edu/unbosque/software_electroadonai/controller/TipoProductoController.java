package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.TipoProducto;
import co.edu.unbosque.software_electroadonai.services.TipoProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tipos")
public class TipoProductoController {

    @Autowired
    private TipoProductoDAO tipoProductoDAO;

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "tipo-form";
    }

    @PostMapping("/crear")
    public String crearTipo(@ModelAttribute TipoProducto tipoProducto) {
        tipoProductoDAO.saveOrUpdate(tipoProducto);
        return "redirect:/tipos/listar";
    }

    @GetMapping("/listar")
    public String listarTipos(Model model) {
        List<TipoProducto> tipos = tipoProductoDAO.getAllTipos();
        model.addAttribute("tipos", tipos);
        return "lista-tipoProducto";
    }

    @PostMapping("/editar")
    public String editarTipo(@ModelAttribute TipoProducto nuevoTipo) {
        Optional<TipoProducto> tipoExistente = tipoProductoDAO.getTipoById(nuevoTipo.getID_TIPO_PRODUCTO());

        if (tipoExistente.isPresent()) {
            TipoProducto tipo = tipoExistente.get();
            tipo.setNOMBRE_TIPO(nuevoTipo.getNOMBRE_TIPO());
            tipoProductoDAO.saveOrUpdate(tipo);
        }
        return "redirect:/tipos/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTipo(@PathVariable int id) {
        Optional<TipoProducto> tipoExistente = tipoProductoDAO.getTipoById(id);
        if (tipoExistente.isPresent()) {
            tipoProductoDAO.deleteTipo(id);
            return ResponseEntity.ok("Tipo de producto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de producto no encontrado");
        }
    }
}

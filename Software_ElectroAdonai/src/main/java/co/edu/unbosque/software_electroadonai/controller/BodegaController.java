package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.services.BodegaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bodegas")
public class BodegaController {

    @Autowired
    private BodegaDAO bodegaDAO;

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "bodega-form";
    }

    @PostMapping("/crear")
    public String crearBodega(@ModelAttribute Bodega bodega) {
        bodegaDAO.saveOrUpdate(bodega);
        return "redirect:/bodegas/listar";
    }

    @GetMapping("/listar")
    public String listarBodegas(Model model) {
        List<Bodega> bodegas = bodegaDAO.getAllBodegas();
        model.addAttribute("bodegas", bodegas);
        return "lista-bodegas";
    }

    @GetMapping("/listarVendedor")
    public String listarBodegasVendedor(Model model) {
        List<Bodega> bodegas = bodegaDAO.getAllBodegas();
        model.addAttribute("bodegas", bodegas);
        return "lista-bodegas-vendedor";
    }

    @PostMapping("/editar")
    public String editarBodega(@ModelAttribute Bodega nuevaBodega) {
        Optional<Bodega> bodegaExistente = bodegaDAO.getBodegaById(nuevaBodega.getID_BODEGA());

        if (bodegaExistente.isPresent()) {
            Bodega bodega = bodegaExistente.get();
            bodega.setN_BODEGA(nuevaBodega.getN_BODEGA());
            bodega.setCNT_PRODUCTOS(nuevaBodega.getCNT_PRODUCTOS());
            bodega.setESTADO(nuevaBodega.getESTADO());

            bodegaDAO.saveOrUpdate(bodega);
        }
        return "redirect:/bodegas/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarBodega(@PathVariable int id) {
        Optional<Bodega> bodegaExistente = bodegaDAO.getBodegaById(id);
        if (bodegaExistente.isPresent()) {
            bodegaDAO.deleteBodega(id);
            return ResponseEntity.ok("Bodega eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bodega no encontrada");
        }
    }
}

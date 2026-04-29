package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.PagoComisiones;
import co.edu.unbosque.software_electroadonai.services.PagoComisionesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pagoComisiones")
public class PagoComisionesController {

    @Autowired
    private PagoComisionesDAO pagoComisionesDAO;

    @GetMapping("/")
    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "pagoComision-form";
    }

    @PostMapping("/crear")
    public String crearPagoComision(@ModelAttribute PagoComisiones pagoComision) {
        pagoComisionesDAO.saveOrUpdate(pagoComision);
        return "redirect:/pagoComisiones/listar";
    }
    @PostMapping("/generar")
    public String generarComisiones() {
        pagoComisionesDAO.generarPagoComisiones();
        return "redirect:/pagoComisiones/listar";
    }

    @GetMapping("/listar")
    public String listarPagoComisiones(Model model) {
        List<PagoComisiones> lista = pagoComisionesDAO.getAllPagoComisiones();
        model.addAttribute("pagoComisiones", lista);
        return "lista-pagoComisiones";
    }

    @PostMapping("/editar")
    public String editarPagoComision(@ModelAttribute PagoComisiones nuevoPago) {
        Optional<PagoComisiones> existente = pagoComisionesDAO.getPagoComisionesById(nuevoPago.getID_COMISIONES());
        if (existente.isPresent()) {
            PagoComisiones pago = existente.get();
            pago.setFECHA(nuevoPago.getFECHA());
            pago.setCOMISION(nuevoPago.getCOMISION());
            pago.setSALDO(nuevoPago.getSALDO());
            pago.setESTADO(nuevoPago.getESTADO());
            pagoComisionesDAO.saveOrUpdate(pago);
        }
        return "redirect:/pagoComisiones/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPagoComision(@PathVariable int id) {
        Optional<PagoComisiones> pago = pagoComisionesDAO.getPagoComisionesById(id);
        if (pago.isPresent()) {
            pagoComisionesDAO.deletePagoComisiones(id);
            return ResponseEntity.ok("Pago de comisión eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago de comisión no encontrado");
        }
    }
    @PostMapping("/actualizarEstado")
    @ResponseBody
    public ResponseEntity<String> actualizarEstado(
            @RequestParam("ID_COMISIONES") int id,
            @RequestParam("ESTADO") String estado) {
        try {
            Optional<PagoComisiones> existente = pagoComisionesDAO.getPagoComisionesById(id);
            if (existente.isPresent()) {
                PagoComisiones pago = existente.get();
                pago.setESTADO(estado);
                pagoComisionesDAO.saveOrUpdate(pago);
                return ResponseEntity.ok("Estado actualizado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comisión no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estado");
        }
    }
}


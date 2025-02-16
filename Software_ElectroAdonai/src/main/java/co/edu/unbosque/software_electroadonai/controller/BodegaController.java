package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Bodega;

import co.edu.unbosque.software_electroadonai.services.BodegaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bodegas")
public class BodegaController {

    @Autowired
    private BodegaDAO bodegaDAO;

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {

        return "bodega-form";
    }

    @GetMapping("/listar")
    public String listarBodegas(Model model) {

        List<Bodega> bodegas = bodegaDAO.getAllBodegas();

        model.addAttribute("bodegas", bodegas);
        return "lista-bodegas";
    }
}

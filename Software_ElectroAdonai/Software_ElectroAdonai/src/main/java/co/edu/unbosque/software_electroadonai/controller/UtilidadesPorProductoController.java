package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.UtilidadesPorProducto;
import co.edu.unbosque.software_electroadonai.services.UtilidadesPorProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/utilidadesPorProducto")
public class UtilidadesPorProductoController {

    @Autowired
    private UtilidadesPorProductoDAO utilidadesPorProductoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }


    @GetMapping("/listar")
    public String listarUtilidadesPorProducto(Model model) {
        List<UtilidadesPorProducto> utilidadesPorProductos = utilidadesPorProductoDAO.getAllUtilidadesPorProductos();
        model.addAttribute("utilidadesPorProductos", utilidadesPorProductos);
        System.out.println(utilidadesPorProductos.toString());
        return "lista-utilidadesPorProductos";
    }
}

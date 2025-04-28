package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Producto;
<<<<<<< HEAD
import co.edu.unbosque.software_electroadonai.model.TipoProducto;
=======
>>>>>>> parent of 585f167 (Merge branch 'master' of https://github.com/wleal7797/DT_2025-01-10)
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "producto-form";
    }

    @GetMapping("/listarVendedor")
    public String listarProductosVendedor(Model model) {
        List<Producto> productos = productoDAO.getAllProductos();
        model.addAttribute("productos", productos);
        System.out.println(productos.toString());
        return "lista-productos-vendedor";
    }
    @GetMapping("/listar")
    public String listarProductos(Model model) {
        List<Producto> productos = productoDAO.getAllProductos();
        model.addAttribute("productos", productos);
        System.out.println(productos.toString());
        return "lista-productos";
    }
}

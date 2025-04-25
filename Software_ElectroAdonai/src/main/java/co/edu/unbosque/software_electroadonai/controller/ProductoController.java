package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.model.TipoProducto;
import co.edu.unbosque.software_electroadonai.model.Usuario;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/")

    public String inicio() {
        return "main";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        List<Producto> productos = productoDAO.getAllProductos();
        List<TipoProducto> tiposProducto = entityManager.createQuery("SELECT t FROM TipoProducto t", TipoProducto.class)
                .getResultList();

        model.addAttribute("productos", productos);
        model.addAttribute("tiposProducto", tiposProducto);
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

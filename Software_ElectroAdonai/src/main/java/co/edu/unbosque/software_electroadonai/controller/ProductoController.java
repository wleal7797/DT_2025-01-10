package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.model.TipoProducto;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("tiposProducto", tiposProducto);
        model.addAttribute("productos", productos);

        return "producto-form";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute Producto producto) {
        productoDAO.saveOrUpdate(producto);
        return "redirect:/productos/listar";
    }

    @GetMapping("/listar")
    public String listarProductos(Model model) {
        List<Producto> productos = productoDAO.getAllProductos();
        List<TipoProducto> tiposProducto = entityManager.createQuery("SELECT t FROM TipoProducto t", TipoProducto.class)
                .getResultList();

        model.addAttribute("tiposProducto", tiposProducto);
        model.addAttribute("productos", productos);
        return "lista-productos";
    }

    @GetMapping("/listarVendedor")
    public String listarProductosVendedor(Model model) {
        List<Producto> productos = productoDAO.getAllProductos();
        model.addAttribute("productos", productos);
        return "lista-productos-vendedor";
    }

    @PostMapping("/editar")
    public String editarProducto(@ModelAttribute Producto nuevoProducto) {
        Optional<Producto> productoExistente = productoDAO.getProductoById(nuevoProducto.getID_PRODUCTO());

        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNOMBRE_PRODUCTO(nuevoProducto.getNOMBRE_PRODUCTO());
            producto.setREFERENCIA(nuevoProducto.getREFERENCIA());
            producto.setCOSTO(nuevoProducto.getCOSTO());
            producto.setEXISTENCIAS(nuevoProducto.getEXISTENCIAS());

            productoDAO.saveOrUpdate(producto);
        }
        return "redirect:/productos/listar";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable int id) {
        Optional<Producto> productoExistente = productoDAO.getProductoById(id);
        if (productoExistente.isPresent()) {
            productoDAO.deleteProducto(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }
}

package co.edu.unbosque.software_electroadonai.controller;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
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

    @PostMapping("/editar")
    public String editarProducto(@ModelAttribute Producto nuevoProducto) {
        Optional<Producto> productoExistente = productoDAO.getProductoById(nuevoProducto.getID_PRODUCTO());

        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNOMBRE_PRODUCTO(nuevoProducto.getNOMBRE_PRODUCTO());
            producto.setREFERENCIA(nuevoProducto.getNOMBRE_PRODUCTO());
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

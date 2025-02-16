package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoDAO {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto saveOrUpdate(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> getAllProductos() {
        return (List<Producto>) productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(int id) {

        return productoRepository.findById(id);
    }

    public void deleteProducto(int id) {
        productoRepository.deleteById(id);
    }
}

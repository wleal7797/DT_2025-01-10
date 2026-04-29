package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductoDAO {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto saveOrUpdate(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> getAllProductos() {
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Producto> getProductoById(int id) {
        return productoRepository.findById(id);
    }

    public void deleteProducto(int id) {
        productoRepository.deleteById(id);
    }

    public long countProductos() {
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .count();
    }

    public List<Producto> getProductosByNombre(String nombre) {
        return getAllProductos().stream()
                .filter(producto -> producto.getNOMBRE_PRODUCTO().toLowerCase()
                        .contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Producto> getProductosConExistencias() {
        return getAllProductos().stream()
                .filter(producto -> producto.getEXISTENCIAS() > 0)
                .collect(Collectors.toList());
    }
}

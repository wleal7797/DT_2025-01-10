package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.SerialVentaProducto;
import co.edu.unbosque.software_electroadonai.repository.SerialVentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerialVentaProductoDAO {

    @Autowired
    private SerialVentaProductoRepository serialVentaproductoRepository;

    public SerialVentaProducto saveOrUpdate(SerialVentaProducto serialVentaProducto) {
        return serialVentaproductoRepository.save(serialVentaProducto);
    }

    public List<SerialVentaProducto> getAllSerialVentaProducto() {
        return (List<SerialVentaProducto>) serialVentaproductoRepository.findAll();
    }

    public Optional<SerialVentaProducto> getSerialVentaProductoById(int id) {

        return serialVentaproductoRepository.findById(id);
    }

    public void deleteSerialVentaProducto(int id) {
        serialVentaproductoRepository.deleteById(id);
    }
}


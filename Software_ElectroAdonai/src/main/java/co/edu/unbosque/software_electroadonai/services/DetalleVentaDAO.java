package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.DetalleVenta;
import co.edu.unbosque.software_electroadonai.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaDAO {

    @Autowired
    private DetalleVentaRepository detalleRepository;

    public DetalleVenta saveOrUpdate(DetalleVenta detalleVenta) {
        return detalleRepository.save(detalleVenta);
    }

    public List<DetalleVenta> getAllDetallesVenta() {
        return (List<DetalleVenta>) detalleRepository.findAll();
    }

    public Optional<DetalleVenta> getDetalleVentaById(int id) {

        return detalleRepository.findById(id);
    }

    public void deleteDetalleVenta(int id) {
        detalleRepository.deleteById(id);
    }
}

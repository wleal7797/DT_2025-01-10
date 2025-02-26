package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaDAO {

    @Autowired
    private VentaRepository ventaRepository;

    public Venta saveOrUpdate(Venta venta) {
        return ventaRepository.save(venta);
    }

    public List<Venta> getAllVentas() {
        return (List<Venta>) ventaRepository.findAll();
    }

    public Optional<Venta> getVentaById(int id) {

        return ventaRepository.findById(id);
    }

    public void deleteVenta(int id) {
        ventaRepository.deleteById(id);
    }
}

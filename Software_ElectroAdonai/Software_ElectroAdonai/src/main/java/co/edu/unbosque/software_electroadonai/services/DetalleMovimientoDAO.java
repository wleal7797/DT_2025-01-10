package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.DetalleMovimiento;
import co.edu.unbosque.software_electroadonai.repository.DetalleBodegaRepository;
import co.edu.unbosque.software_electroadonai.repository.DetalleMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DetalleMovimientoDAO {

    @Autowired
    private DetalleMovimientoRepository detalleMovimientoRepository;

    public DetalleMovimiento saveOrUpdate(DetalleMovimiento detalleMovimiento) {
        return detalleMovimientoRepository.save(detalleMovimiento);
    }

    public List<DetalleMovimiento> getAllDetallesMovimiento() {
        return (List<DetalleMovimiento>) detalleMovimientoRepository.findAll();
    }

    public Optional<DetalleMovimiento> getDetalleMovimientoById(int id) {

        return detalleMovimientoRepository.findById(id);
    }

    public void deleteDetalleMovimiento(int id) {
        detalleMovimientoRepository.deleteById(id);
    }
}


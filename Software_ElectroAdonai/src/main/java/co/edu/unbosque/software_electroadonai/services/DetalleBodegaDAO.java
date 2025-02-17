package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.repository.DetalleBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBodegaDAO {

    @Autowired
    private DetalleBodegaRepository detalleBodegaRepository;

    public DetalleBodega saveOrUpdate(DetalleBodega detalleBodega) {
        return detalleBodegaRepository.save(detalleBodega);
    }

    public List<DetalleBodega> getAllDetallesBodega() {
        return (List<DetalleBodega>) detalleBodegaRepository.findAll();
    }

    public Optional<DetalleBodega> getDetalleBodegaById(int id) {

        return detalleBodegaRepository.findById(id);
    }

    public void deleteDetalleBodega(int id) {
        detalleBodegaRepository.deleteById(id);
    }
}


package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.MovimientoBodega;
import co.edu.unbosque.software_electroadonai.repository.MovimientoBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoBodegaDAO {

    @Autowired
    private MovimientoBodegaRepository movimientoBodegaRepository;

    public MovimientoBodega saveOrUpdate(MovimientoBodega movimientoBodega) {
        return movimientoBodegaRepository.save(movimientoBodega);
    }

    public List<MovimientoBodega> getAllMovimientosBodega() {
        return (List<MovimientoBodega>) movimientoBodegaRepository.findAll();
    }

    public Optional<MovimientoBodega> getmovimientoBodegaById(int id) {

        return movimientoBodegaRepository.findById(id);
    }

    public void deleteMovimientoBodega(int id) {
        movimientoBodegaRepository.deleteById(id);
    }
}

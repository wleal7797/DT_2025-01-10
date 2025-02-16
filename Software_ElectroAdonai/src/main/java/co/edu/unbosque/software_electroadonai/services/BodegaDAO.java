package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaDAO {

    @Autowired
    private BodegaRepository bodegaRepository;

    public Bodega saveOrUpdate(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public List<Bodega> getAllBodegas() {
        return (List<Bodega>) bodegaRepository.findAll();
    }

    public Optional<Bodega> getBodegaById(int id) {

        return bodegaRepository.findById(id);
    }

    public void deleteBodega(int id) {
        bodegaRepository.deleteById(id);
    }
}

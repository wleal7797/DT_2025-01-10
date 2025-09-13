package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BodegaDAO {

    @Autowired
    private BodegaRepository bodegaRepository;

    public Bodega saveOrUpdate(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public List<Bodega> getAllBodegas() {
        return StreamSupport.stream(bodegaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Bodega> getBodegaById(int id) {
        return bodegaRepository.findById(id);
    }

    public Optional<Bodega> getBodegaByNombre(String nombre) {
        return getAllBodegas().stream()
                .filter(bodega -> nombre.equalsIgnoreCase(bodega.getN_BODEGA()))
                .findFirst();
    }

    public void deleteBodega(int id) {
        bodegaRepository.deleteById(id);
    }

    public long countBodegas() {
        return StreamSupport.stream(bodegaRepository.findAll().spliterator(), false)
                .count();
    }
}
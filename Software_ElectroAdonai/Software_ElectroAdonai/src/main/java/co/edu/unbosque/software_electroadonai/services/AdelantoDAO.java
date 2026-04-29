package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Adelanto;
import co.edu.unbosque.software_electroadonai.repository.AdelantoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdelantoDAO {

    @Autowired
    private AdelantoRepository adelantoRepository;

    public Adelanto saveOrUpdate(Adelanto adelanto) {
        return adelantoRepository.save(adelanto);
    }

    public List<Adelanto> getAllAdelanto() {
        return (List<Adelanto>) adelantoRepository.findAll();
    }

    public Optional<Adelanto> getAdelantoById(int id) {

        return adelantoRepository.findById(id);
    }

    public void deleteAdelanto(int id) {
        adelantoRepository.deleteById(id);
    }
}
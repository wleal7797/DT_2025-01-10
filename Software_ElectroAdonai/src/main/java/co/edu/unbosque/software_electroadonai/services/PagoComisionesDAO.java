package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.PagoComisiones;
import co.edu.unbosque.software_electroadonai.repository.PagoComisionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoComisionesDAO {

    @Autowired
    private PagoComisionesRepository pagoComisionesRepository;

    public PagoComisiones saveOrUpdate(PagoComisiones pagoComisiones) {
        return pagoComisionesRepository.save(pagoComisiones);
    }

    public List<PagoComisiones> getAllPagoComisiones() {
        return (List<PagoComisiones>) pagoComisionesRepository.findAll();
    }

    public Optional<PagoComisiones> getPagoComisionesById(int id) {

        return pagoComisionesRepository.findById(id);
    }

    public void deletePagoComisiones(int id) {
        pagoComisionesRepository.deleteById(id);
    }
}

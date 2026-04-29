package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.PagoComisiones;
import co.edu.unbosque.software_electroadonai.repository.PagoComisionesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoComisionesDAO {

    @Autowired
    private PagoComisionesRepository pagoComisionesRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void generarPagoComisiones() {
        entityManager.createNativeQuery("CALL generar_pago_comisiones()").executeUpdate();
    }
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

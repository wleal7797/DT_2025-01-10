package co.edu.unbosque.software_electroadonai.repository;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends CrudRepository<Bodega, Integer> {
}
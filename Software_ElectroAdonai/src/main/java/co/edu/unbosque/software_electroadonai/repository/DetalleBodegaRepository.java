package co.edu.unbosque.software_electroadonai.repository;

import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleBodegaRepository extends CrudRepository<DetalleBodega, Integer> {


}
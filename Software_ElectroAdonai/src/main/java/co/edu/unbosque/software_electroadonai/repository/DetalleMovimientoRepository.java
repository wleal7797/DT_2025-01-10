package co.edu.unbosque.software_electroadonai.repository;

import co.edu.unbosque.software_electroadonai.model.DetalleMovimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleMovimientoRepository extends CrudRepository<DetalleMovimiento, Integer> {


}

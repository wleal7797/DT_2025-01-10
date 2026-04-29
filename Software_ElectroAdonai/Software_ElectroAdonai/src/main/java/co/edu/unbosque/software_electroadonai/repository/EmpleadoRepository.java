package co.edu.unbosque.software_electroadonai.repository;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
}

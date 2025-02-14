package co.edu.unbosque.repository;

import co.edu.unbosque.model.EmpleadoDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpleadoRepository extends CrudRepository<EmpleadoDTO, Integer> {
}

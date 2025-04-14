package co.edu.unbosque.software_electroadonai.repository;

import co.edu.unbosque.software_electroadonai.model.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Integer> {

}

package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Authorities;
import co.edu.unbosque.software_electroadonai.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutoridadesDAO {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public Authorities saveOrUpdate(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }

    public Optional<Authorities> getByUsername(String username) {
        return authoritiesRepository.findById(username);
    }

    public void deleteByUsername(String username) {
        authoritiesRepository.deleteById(username);
    }
}


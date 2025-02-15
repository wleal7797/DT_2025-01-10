package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoDAO {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado saveOrUpdate(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> getAllEmpleados() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    public Optional<Empleado> getEmpleadoById(int id) {

        return empleadoRepository.findById(id);
    }

    public void deleteEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }
}

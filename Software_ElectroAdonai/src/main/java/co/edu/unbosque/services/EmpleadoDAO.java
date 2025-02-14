package co.edu.unbosque.services;

import co.edu.unbosque.model.EmpleadoDTO;
import co.edu.unbosque.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoDAO {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Crear o actualizar un empleado
    public EmpleadoDTO saveOrUpdate(EmpleadoDTO empleado) {
        return empleadoRepository.save(empleado);
    }

    // Obtener todos los empleados
    public List<EmpleadoDTO> getAllEmpleados() {
        return (List<EmpleadoDTO>) empleadoRepository.findAll();
    }

    // Obtener un empleado por ID
    public Optional<EmpleadoDTO> getEmpleadoById(int id) {
        return empleadoRepository.findById(id);
    }

    // Eliminar un empleado
    public void deleteEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }
}

package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.UtilidadesPorProducto;
import co.edu.unbosque.software_electroadonai.repository.UtilidadesPorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilidadesPorProductoDAO {

    @Autowired
    private UtilidadesPorProductoRepository utilidadesPorProductoRepository;

    public UtilidadesPorProducto saveOrUpdate(UtilidadesPorProducto utilidadesPorProducto) {
        return utilidadesPorProductoRepository.save(utilidadesPorProducto);
    }

    public List<UtilidadesPorProducto> getAllUtilidadesPorProductos() {
        return (List<UtilidadesPorProducto>) utilidadesPorProductoRepository.findAll();
    }

    public Optional<UtilidadesPorProducto> getUtilidadesPorProductoById(int id) {

        return utilidadesPorProductoRepository.findById(id);
    }

    public void deleteUtilidadesPorProducto(int id) {
        utilidadesPorProductoRepository.deleteById(id);
    }
}

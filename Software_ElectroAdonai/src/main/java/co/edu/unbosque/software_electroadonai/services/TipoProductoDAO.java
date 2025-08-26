package co.edu.unbosque.software_electroadonai.services;

import co.edu.unbosque.software_electroadonai.model.TipoProducto;
import co.edu.unbosque.software_electroadonai.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoDAO {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    public TipoProducto saveOrUpdate(TipoProducto tipoProducto) {
        return tipoProductoRepository.save(tipoProducto);
    }

    public List<TipoProducto> getAllTipos() {
        return (List<TipoProducto>) tipoProductoRepository.findAll();
    }

    public Optional<TipoProducto> getTipoById(int id) {
        return tipoProductoRepository.findById(id);
    }

    public void deleteTipo(int id) {
        tipoProductoRepository.deleteById(id);
    }
}

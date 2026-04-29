package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.repository.UtilidadesPorProductoRepository;
import co.edu.unbosque.software_electroadonai.services.UtilidadesPorProductoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtilidadesPorProductoDAOTest {

    @Mock
    private UtilidadesPorProductoRepository repository;

    @InjectMocks
    private UtilidadesPorProductoDAO dao;

    private UtilidadesPorProducto utilidad;

    @BeforeEach
    void setUp() {
        utilidad = new UtilidadesPorProducto();
        utilidad.setID_UTILIDAD_PR(1);
        utilidad.setUTILIDAD_BRUTA(10000.0);
        utilidad.setUTILIDAD_FINAL(8000.0);
        utilidad.setESTADO("ACTIVO");
        utilidad.setProducto(new Producto());
        utilidad.setDetalle_venta(new DetalleVenta());
    }

    @Test
    void testGuardarUtilidad() {
        when(repository.save(any(UtilidadesPorProducto.class))).thenReturn(utilidad);

        UtilidadesPorProducto resultado = dao.saveOrUpdate(utilidad);

        assertNotNull(resultado);
        assertEquals(8000.0, resultado.getUTILIDAD_FINAL());
        assertEquals("ACTIVO", resultado.getESTADO());
        verify(repository).save(utilidad);
    }

    @Test
    void testObtenerTodos() {
        UtilidadesPorProducto u1 = new UtilidadesPorProducto();
        u1.setESTADO("ACTIVO");

        UtilidadesPorProducto u2 = new UtilidadesPorProducto();
        u2.setESTADO("INACTIVO");

        when(repository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<UtilidadesPorProducto> lista = dao.getAllUtilidadesPorProductos();

        assertEquals(2, lista.size());
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(utilidad));

        Optional<UtilidadesPorProducto> encontrada = dao.getUtilidadesPorProductoById(1);

        assertTrue(encontrada.isPresent());
        assertEquals("ACTIVO", encontrada.get().getESTADO());
        verify(repository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(repository).deleteById(1);

        dao.deleteUtilidadesPorProducto(1);

        verify(repository).deleteById(1);
    }
}

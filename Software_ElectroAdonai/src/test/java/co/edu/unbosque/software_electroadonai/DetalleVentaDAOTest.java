package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.repository.DetalleVentaRepository;
import co.edu.unbosque.software_electroadonai.services.DetalleVentaDAO;
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
public class DetalleVentaDAOTest {

    @Mock
    private DetalleVentaRepository repository;

    @InjectMocks
    private DetalleVentaDAO dao;

    private DetalleVenta detalle;

    @BeforeEach
    void setup() {
        detalle = new DetalleVenta();
        detalle.setID_DETALLE_VENTA(1);
        detalle.setCNT_PRODUCTO_VENTA(3);
        detalle.setPRECIO_VENTA_PRODUCTO(50000);
        detalle.setVenta(new Venta());
        detalle.setBodega(new Bodega());
        detalle.setProducto(new Producto());
    }

    @Test
    void testGuardarDetalleVenta() {
        when(repository.save(any(DetalleVenta.class))).thenReturn(detalle);

        DetalleVenta guardado = dao.saveOrUpdate(detalle);

        assertNotNull(guardado);
        assertEquals(3, guardado.getCNT_PRODUCTO_VENTA());
        assertEquals(50000, guardado.getPRECIO_VENTA_PRODUCTO());
        verify(repository).save(detalle);
    }

    @Test
    void testListarTodos() {
        DetalleVenta d1 = new DetalleVenta();
        d1.setCNT_PRODUCTO_VENTA(2);

        DetalleVenta d2 = new DetalleVenta();
        d2.setCNT_PRODUCTO_VENTA(4);

        when(repository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<DetalleVenta> lista = dao.getAllDetallesVenta();

        assertEquals(2, lista.size());
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(detalle));

        Optional<DetalleVenta> encontrada = dao.getDetalleVentaById(1);

        assertTrue(encontrada.isPresent());
        assertEquals(3, encontrada.get().getCNT_PRODUCTO_VENTA());
        verify(repository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(repository).deleteById(1);

        dao.deleteDetalleVenta(1);

        verify(repository).deleteById(1);
    }
}

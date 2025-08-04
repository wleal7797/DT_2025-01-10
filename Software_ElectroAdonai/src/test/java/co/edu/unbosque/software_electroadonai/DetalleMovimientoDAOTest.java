package co.edu.unbosque.software_electroadonai;


import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.repository.DetalleMovimientoRepository;
import co.edu.unbosque.software_electroadonai.services.DetalleMovimientoDAO;
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
class DetalleMovimientoDAOTest {

    @Mock
    private DetalleMovimientoRepository detalleMovimientoRepository;

    @InjectMocks
    private DetalleMovimientoDAO detalleMovimientoDAO;

    private DetalleMovimiento detalleMovimiento;
    private Producto producto;
    private Bodega bodegaOrigen;
    private Bodega bodegaDestino;
    private MovimientoBodega movimientoBodega;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setID_PRODUCTO(1);
        producto.setNOMBRE_PRODUCTO("Producto XYZ");

        bodegaOrigen = new Bodega();
        bodegaOrigen.setID_BODEGA(1);
        bodegaOrigen.setN_BODEGA("Bodega Norte");

        bodegaDestino = new Bodega();
        bodegaDestino.setID_BODEGA(2);
        bodegaDestino.setN_BODEGA("Bodega Sur");

        movimientoBodega = new MovimientoBodega();
        movimientoBodega.setID_MOVIMIENTO(100);

        detalleMovimiento = new DetalleMovimiento();
        detalleMovimiento.setID_DETALLE_MOVIMIENTO(10);
        detalleMovimiento.setCNT_PRODUCTO_MOVIMIENTO(25);
        detalleMovimiento.setProducto(producto);
        detalleMovimiento.setID_BODEGA_ORIGEN(bodegaOrigen);
        detalleMovimiento.setID_BODEGA_DESTINO(bodegaDestino);
        detalleMovimiento.setID_MOVIMIENTO_BODEGA(movimientoBodega);
    }

    @Test
    void testSaveOrUpdate() {
        when(detalleMovimientoRepository.save(detalleMovimiento)).thenReturn(detalleMovimiento);

        DetalleMovimiento result = detalleMovimientoDAO.saveOrUpdate(detalleMovimiento);

        assertNotNull(result);
        assertEquals(25, result.getCNT_PRODUCTO_MOVIMIENTO());
        assertEquals("Producto XYZ", result.getProducto().getNOMBRE_PRODUCTO());
        assertEquals("Bodega Norte", result.getID_BODEGA_ORIGEN().getN_BODEGA());
        assertEquals("Bodega Sur", result.getID_BODEGA_DESTINO().getN_BODEGA());

        verify(detalleMovimientoRepository, times(1)).save(detalleMovimiento);
    }

    @Test
    void testGetAllDetallesMovimiento() {
        when(detalleMovimientoRepository.findAll()).thenReturn(Arrays.asList(detalleMovimiento));

        List<DetalleMovimiento> lista = detalleMovimientoDAO.getAllDetallesMovimiento();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        verify(detalleMovimientoRepository, times(1)).findAll();
    }

    @Test
    void testGetDetalleMovimientoById() {
        when(detalleMovimientoRepository.findById(10)).thenReturn(Optional.of(detalleMovimiento));

        Optional<DetalleMovimiento> result = detalleMovimientoDAO.getDetalleMovimientoById(10);

        assertTrue(result.isPresent());
        assertEquals("Producto XYZ", result.get().getProducto().getNOMBRE_PRODUCTO());
        verify(detalleMovimientoRepository, times(1)).findById(10);
    }

    @Test
    void testDeleteDetalleMovimiento() {
        doNothing().when(detalleMovimientoRepository).deleteById(10);

        detalleMovimientoDAO.deleteDetalleMovimiento(10);

        verify(detalleMovimientoRepository, times(1)).deleteById(10);
    }
}


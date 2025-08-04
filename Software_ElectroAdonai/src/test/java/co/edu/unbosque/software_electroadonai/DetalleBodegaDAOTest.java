package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.model.DetalleBodega;
import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.repository.DetalleBodegaRepository;
import co.edu.unbosque.software_electroadonai.services.DetalleBodegaDAO;
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
class DetalleBodegaDAOTest {

    @Mock
    private DetalleBodegaRepository detalleBodegaRepository;

    @InjectMocks
    private DetalleBodegaDAO detalleBodegaDAO;

    private DetalleBodega detalleBodega;
    private Producto producto;
    private Bodega bodega;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setID_PRODUCTO(1);
        producto.setNOMBRE_PRODUCTO("Producto A");

        bodega = new Bodega();
        bodega.setID_BODEGA(1);
        bodega.setN_BODEGA("Bodega Central");

        detalleBodega = new DetalleBodega();
        detalleBodega.setID_DETALLE_BODEGA(1);
        detalleBodega.setCNT_PRODUCTO_BODEGA(50);
        detalleBodega.setProducto(producto);
        detalleBodega.setBodega(bodega);
    }

    @Test
    void testSaveOrUpdate() {
        when(detalleBodegaRepository.save(detalleBodega)).thenReturn(detalleBodega);

        DetalleBodega resultado = detalleBodegaDAO.saveOrUpdate(detalleBodega);

        assertNotNull(resultado);
        assertEquals(50, resultado.getCNT_PRODUCTO_BODEGA());
        assertEquals("Producto A", resultado.getProducto().getNOMBRE_PRODUCTO());
        assertEquals("Bodega Central", resultado.getBodega().getN_BODEGA());
        verify(detalleBodegaRepository, times(1)).save(detalleBodega);
    }

    @Test
    void testGetAllDetallesBodega() {
        when(detalleBodegaRepository.findAll()).thenReturn(Arrays.asList(detalleBodega));

        List<DetalleBodega> detalles = detalleBodegaDAO.getAllDetallesBodega();

        assertNotNull(detalles);
        assertEquals(1, detalles.size());
        verify(detalleBodegaRepository, times(1)).findAll();
    }

    @Test
    void testGetDetalleBodegaById() {
        when(detalleBodegaRepository.findById(1)).thenReturn(Optional.of(detalleBodega));

        Optional<DetalleBodega> resultado = detalleBodegaDAO.getDetalleBodegaById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Producto A", resultado.get().getProducto().getNOMBRE_PRODUCTO());
        verify(detalleBodegaRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteDetalleBodega() {
        doNothing().when(detalleBodegaRepository).deleteById(1);

        detalleBodegaDAO.deleteDetalleBodega(1);

        verify(detalleBodegaRepository, times(1)).deleteById(1);
    }
}

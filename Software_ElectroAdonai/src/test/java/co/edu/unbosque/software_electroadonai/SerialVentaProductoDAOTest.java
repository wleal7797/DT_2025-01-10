package co.edu.unbosque.software_electroadonai;


import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.model.SerialVentaProducto;
import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.repository.SerialVentaProductoRepository;
import co.edu.unbosque.software_electroadonai.services.SerialVentaProductoDAO;
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
public class SerialVentaProductoDAOTest {

    @Mock
    private SerialVentaProductoRepository repository;

    @InjectMocks
    private SerialVentaProductoDAO dao;

    private SerialVentaProducto serial;

    @BeforeEach
    void setup() {
        serial = new SerialVentaProducto();
        serial.setID_SERIAL_PRODUCTO(1);
        serial.setSERIAL("ABC123");
        serial.setProducto(new Producto());
        serial.setVenta(new Venta());
    }

    @Test
    void testGuardarSerial() {
        when(repository.save(any(SerialVentaProducto.class))).thenReturn(serial);

        SerialVentaProducto guardado = dao.saveOrUpdate(serial);

        assertNotNull(guardado);
        assertEquals("ABC123", guardado.getSERIAL());
        verify(repository).save(serial);
    }

    @Test
    void testListarTodos() {
        SerialVentaProducto s1 = new SerialVentaProducto();
        s1.setSERIAL("A1");

        SerialVentaProducto s2 = new SerialVentaProducto();
        s2.setSERIAL("B2");

        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<SerialVentaProducto> lista = dao.getAllSerialVentaProducto();

        assertEquals(2, lista.size());
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(serial));

        Optional<SerialVentaProducto> encontrado = dao.getSerialVentaProductoById(1);

        assertTrue(encontrado.isPresent());
        assertEquals("ABC123", encontrado.get().getSERIAL());
        verify(repository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(repository).deleteById(1);

        dao.deleteSerialVentaProducto(1);

        verify(repository).deleteById(1);
    }
}

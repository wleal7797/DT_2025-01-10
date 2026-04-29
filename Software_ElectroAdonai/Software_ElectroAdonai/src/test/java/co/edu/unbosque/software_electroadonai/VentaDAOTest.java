package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Venta;
import co.edu.unbosque.software_electroadonai.repository.VentaRepository;
import co.edu.unbosque.software_electroadonai.services.VentaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaDAOTest {

    @Mock
    private VentaRepository repository;

    @InjectMocks
    private VentaDAO dao;

    private Venta venta;

    @BeforeEach
    void setup() {
        venta = new Venta();
        venta.setID_VENTA(1);
        venta.setFECHA_VENTA(LocalDate.of(2025, 8, 4));
        venta.setREMISION("R-001");
        venta.setPRECIO_VENTA_TOTAL(150000);
        venta.setEmpleado(new Empleado());
    }

    @Test
    void testGuardarVenta() {
        when(repository.save(any(Venta.class))).thenReturn(venta);

        Venta guardada = dao.saveOrUpdate(venta);

        assertNotNull(guardada);
        assertEquals("R-001", guardada.getREMISION());
        assertEquals(150000, guardada.getPRECIO_VENTA_TOTAL());
        verify(repository).save(venta);
    }

    @Test
    void testListarTodas() {
        Venta v1 = new Venta();
        v1.setREMISION("R-001");

        Venta v2 = new Venta();
        v2.setREMISION("R-002");

        when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Venta> lista = dao.getAllVentas();

        assertEquals(2, lista.size());
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(venta));

        Optional<Venta> encontrada = dao.getVentaById(1);

        assertTrue(encontrada.isPresent());
        assertEquals("R-001", encontrada.get().getREMISION());
        verify(repository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(repository).deleteById(1);

        dao.deleteVenta(1);

        verify(repository).deleteById(1);
    }
}

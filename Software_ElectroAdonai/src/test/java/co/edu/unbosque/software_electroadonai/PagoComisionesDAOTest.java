package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.*;
import co.edu.unbosque.software_electroadonai.repository.PagoComisionesRepository;
import co.edu.unbosque.software_electroadonai.services.PagoComisionesDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
public class PagoComisionesDAOTest {

    @Mock
    private PagoComisionesRepository repository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PagoComisionesDAO dao;

    private PagoComisiones pago;

    @BeforeEach
    void setUp() {
        pago = new PagoComisiones();
        pago.setID_COMISIONES(1);
        pago.setCOMISION(50000);
        pago.setSALDO(10000);
        pago.setESTADO("PAGADO");
        pago.setFECHA(LocalDate.now());
        pago.setEmpleado(new Empleado());
        pago.setAdelantos(new Adelanto());
    }

    @Test
    void testGuardarPagoComision() {
        when(repository.save(any(PagoComisiones.class))).thenReturn(pago);

        PagoComisiones guardado = dao.saveOrUpdate(pago);

        assertNotNull(guardado);
        assertEquals(50000, guardado.getCOMISION());
        verify(repository).save(pago);
    }

    @Test
    void testObtenerTodos() {
        PagoComisiones p1 = new PagoComisiones();
        PagoComisiones p2 = new PagoComisiones();

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<PagoComisiones> lista = dao.getAllPagoComisiones();

        assertEquals(2, lista.size());
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(pago));

        Optional<PagoComisiones> encontrado = dao.getPagoComisionesById(1);

        assertTrue(encontrado.isPresent());
        assertEquals("PAGADO", encontrado.get().getESTADO());
        verify(repository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(repository).deleteById(1);

        dao.deletePagoComisiones(1);

        verify(repository).deleteById(1);
    }

    @Test
    void testGenerarPagoComisiones() {
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery("CALL generar_pago_comisiones()")).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1);

        dao.generarPagoComisiones();

        verify(entityManager).createNativeQuery("CALL generar_pago_comisiones()");
        verify(mockQuery).executeUpdate();
    }
}

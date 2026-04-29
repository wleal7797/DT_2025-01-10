package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.MovimientoBodega;
import co.edu.unbosque.software_electroadonai.repository.MovimientoBodegaRepository;
import co.edu.unbosque.software_electroadonai.services.MovimientoBodegaDAO;
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
public class MovimientoBodegaDAOTest {

    @Mock
    private MovimientoBodegaRepository movimientoBodegaRepository;

    @InjectMocks
    private MovimientoBodegaDAO movimientoBodegaDAO;

    private MovimientoBodega movimiento;

    @BeforeEach
    void setup() {
        movimiento = new MovimientoBodega();
        movimiento.setID_MOVIMIENTO(1);
        movimiento.setFECHA_MOVIMIENTO(LocalDate.of(2025, 8, 1));
    }

    @Test
    public void testGuardarMovimiento() {
        when(movimientoBodegaRepository.save(any(MovimientoBodega.class))).thenReturn(movimiento);

        MovimientoBodega guardado = movimientoBodegaDAO.saveOrUpdate(movimiento);

        assertNotNull(guardado);
        assertEquals(1, guardado.getID_MOVIMIENTO());
        verify(movimientoBodegaRepository, times(1)).save(movimiento);
    }

    @Test
    public void testBuscarPorId() {
        when(movimientoBodegaRepository.findById(1)).thenReturn(Optional.of(movimiento));

        Optional<MovimientoBodega> encontrado = movimientoBodegaDAO.getmovimientoBodegaById(1);

        assertTrue(encontrado.isPresent());
        assertEquals(LocalDate.of(2025, 8, 1), encontrado.get().getFECHA_MOVIMIENTO());
        verify(movimientoBodegaRepository).findById(1);
    }

    @Test
    public void testListarMovimientos() {
        MovimientoBodega m1 = new MovimientoBodega();
        m1.setFECHA_MOVIMIENTO(LocalDate.now());

        MovimientoBodega m2 = new MovimientoBodega();
        m2.setFECHA_MOVIMIENTO(LocalDate.now().minusDays(1));

        when(movimientoBodegaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<MovimientoBodega> movimientos = movimientoBodegaDAO.getAllMovimientosBodega();

        assertEquals(2, movimientos.size());
        verify(movimientoBodegaRepository).findAll();
    }

    @Test
    public void testEliminarMovimiento() {
        doNothing().when(movimientoBodegaRepository).deleteById(1);
        when(movimientoBodegaRepository.findById(1)).thenReturn(Optional.empty());

        movimientoBodegaDAO.deleteMovimientoBodega(1);

        Optional<MovimientoBodega> eliminado = movimientoBodegaDAO.getmovimientoBodegaById(1);
        assertFalse(eliminado.isPresent());

        verify(movimientoBodegaRepository).deleteById(1);
        verify(movimientoBodegaRepository).findById(1);
    }
}



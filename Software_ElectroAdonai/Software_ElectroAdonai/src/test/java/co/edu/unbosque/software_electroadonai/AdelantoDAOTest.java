package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Adelanto;
import co.edu.unbosque.software_electroadonai.repository.AdelantoRepository;
import co.edu.unbosque.software_electroadonai.services.AdelantoDAO;
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
class AdelantoDAOTest {

    @Mock
    private AdelantoRepository adelantoRepository;

    @InjectMocks
    private AdelantoDAO adelantoDAO;

    private Adelanto adelantoEjemplo;

    @BeforeEach
    void setUp() {
        adelantoEjemplo = new Adelanto();
        adelantoEjemplo.setID_ADELANTO(1001);
        adelantoEjemplo.setCNT_ADELANTO(250000.0);
        adelantoEjemplo.setFECHA_ADELANTO(LocalDate.of(2025, 8, 4));
        adelantoEjemplo.setESTADO_ADELANTO("Pendiente");
        // No necesitas setEmpleado() para esta prueba unitaria
    }

    @Test
    void testSaveOrUpdate() {
        when(adelantoRepository.save(adelantoEjemplo)).thenReturn(adelantoEjemplo);

        Adelanto resultado = adelantoDAO.saveOrUpdate(adelantoEjemplo);

        assertNotNull(resultado);
        assertEquals(250000.0, resultado.getCNT_ADELANTO());
        assertEquals("Pendiente", resultado.getESTADO_ADELANTO());
        verify(adelantoRepository, times(1)).save(adelantoEjemplo);
    }

    @Test
    void testGetAllAdelanto() {
        List<Adelanto> lista = Arrays.asList(adelantoEjemplo);
        when(adelantoRepository.findAll()).thenReturn(lista);

        List<Adelanto> resultado = adelantoDAO.getAllAdelanto();

        assertEquals(1, resultado.size());
        assertEquals(250000.0, resultado.get(0).getCNT_ADELANTO());
        verify(adelantoRepository, times(1)).findAll();
    }

    @Test
    void testGetAdelantoById() {
        when(adelantoRepository.findById(1001)).thenReturn(Optional.of(adelantoEjemplo));

        Optional<Adelanto> resultado = adelantoDAO.getAdelantoById(1001);

        assertTrue(resultado.isPresent());
        assertEquals("Pendiente", resultado.get().getESTADO_ADELANTO());
        verify(adelantoRepository, times(1)).findById(1001);
    }

    @Test
    void testDeleteAdelanto() {
        doNothing().when(adelantoRepository).deleteById(1001);

        adelantoDAO.deleteAdelanto(1001);

        verify(adelantoRepository, times(1)).deleteById(1001);
    }
}


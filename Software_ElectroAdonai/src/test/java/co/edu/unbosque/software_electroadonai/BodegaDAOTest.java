package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Bodega;
import co.edu.unbosque.software_electroadonai.repository.BodegaRepository;
import co.edu.unbosque.software_electroadonai.services.BodegaDAO;
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
class BodegaDAOTest {

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private BodegaDAO bodegaDAO;

    private Bodega bodega;

    @BeforeEach
    void setUp() {
        bodega = new Bodega();
        bodega.setID_BODEGA(1);
        bodega.setN_BODEGA("Principal");
        bodega.setCNT_PRODUCTOS(100);
        bodega.setESTADO("Activo");
    }

    @Test
    void testSaveOrUpdate() {
        when(bodegaRepository.save(bodega)).thenReturn(bodega);

        Bodega resultado = bodegaDAO.saveOrUpdate(bodega);

        assertNotNull(resultado);
        assertEquals("Principal", resultado.getN_BODEGA());
        verify(bodegaRepository, times(1)).save(bodega);
    }

    @Test
    void testGetAllBodegas() {
        when(bodegaRepository.findAll()).thenReturn(Arrays.asList(bodega));

        List<Bodega> bodegas = bodegaDAO.getAllBodegas();

        assertNotNull(bodegas);
        assertEquals(1, bodegas.size());
        verify(bodegaRepository, times(1)).findAll();
    }

    @Test
    void testGetBodegaById() {
        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodega));

        Optional<Bodega> resultado = bodegaDAO.getBodegaById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Principal", resultado.get().getN_BODEGA());
        verify(bodegaRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteBodega() {
        doNothing().when(bodegaRepository).deleteById(1);

        bodegaDAO.deleteBodega(1);

        verify(bodegaRepository, times(1)).deleteById(1);
    }
}

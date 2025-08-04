package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Producto;
import co.edu.unbosque.software_electroadonai.repository.ProductoRepository;
import co.edu.unbosque.software_electroadonai.services.ProductoDAO;
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
class ProductoDAOTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoDAO productoDAO;

    private Producto productoEjemplo;

    @BeforeEach
    void setUp() {
        productoEjemplo = new Producto();
        productoEjemplo.setID_PRODUCTO(55584732);
        productoEjemplo.setNOMBRE_PRODUCTO("tv prueba producto");
        productoEjemplo.setCOSTO(150000);
        productoEjemplo.setEXISTENCIAS(10);
        productoEjemplo.setREFERENCIA("referencia");
    }

    @Test
    void testSaveOrUpdate() {
        when(productoRepository.save(productoEjemplo)).thenReturn(productoEjemplo);

        Producto resultado = productoDAO.saveOrUpdate(productoEjemplo);

        assertNotNull(resultado);
        assertEquals("tv prueba producto", resultado.getNOMBRE_PRODUCTO());
        assertEquals(150000, resultado.getCOSTO());
        verify(productoRepository, times(1)).save(productoEjemplo);
    }

    @Test
    void testGetAllProductos() {
        List<Producto> lista = Arrays.asList(productoEjemplo);
        when(productoRepository.findAll()).thenReturn(lista);

        List<Producto> resultado = productoDAO.getAllProductos();

        assertEquals(1, resultado.size());
        assertEquals("tv prueba producto", resultado.get(0).getNOMBRE_PRODUCTO());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testGetProductoById() {
        when(productoRepository.findById(55584732)).thenReturn(Optional.of(productoEjemplo));

        Optional<Producto> resultado = productoDAO.getProductoById(55584732);

        assertTrue(resultado.isPresent());
        assertEquals("tv prueba producto", resultado.get().getNOMBRE_PRODUCTO());
        verify(productoRepository, times(1)).findById(55584732);
    }

    @Test
    void testDeleteProducto() {
        doNothing().when(productoRepository).deleteById(55584732);

        productoDAO.deleteProducto(55584732);

        verify(productoRepository, times(1)).deleteById(55584732);
    }
}

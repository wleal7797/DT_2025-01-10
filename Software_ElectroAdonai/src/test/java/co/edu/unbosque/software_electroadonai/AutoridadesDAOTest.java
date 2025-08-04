package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Authorities;
import co.edu.unbosque.software_electroadonai.repository.AuthoritiesRepository;
import co.edu.unbosque.software_electroadonai.services.AutoridadesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutoridadesDAOTest {

    @Mock
    private AuthoritiesRepository authoritiesRepository;

    @InjectMocks
    private AutoridadesDAO autoridadesDAO;

    private Authorities authorityExample;

    @BeforeEach
    void setUp() {
        authorityExample = new Authorities();
        authorityExample.setUsername("admin");
        authorityExample.setAuthority("ROLE_ADMIN");
        // No necesitas setUser() en esta prueba
    }

    @Test
    void testSaveOrUpdate() {
        when(authoritiesRepository.save(authorityExample)).thenReturn(authorityExample);

        Authorities resultado = autoridadesDAO.saveOrUpdate(authorityExample);

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
        assertEquals("ROLE_ADMIN", resultado.getAuthority());
        verify(authoritiesRepository, times(1)).save(authorityExample);
    }
}

package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.model.Users;
import co.edu.unbosque.software_electroadonai.repository.UsuarioRepository;
import co.edu.unbosque.software_electroadonai.services.UsuarioDAO;
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
public class UsuarioDAOTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDAO usuarioDAO;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setID_USUARIO(1);
        user.setUsername("wilson");
        user.setPassword("1234");
        user.setEnabled(true);
        user.setEmpleado(new Empleado());
    }

    @Test
    void testGuardarUsuario() {
        when(usuarioRepository.save(any(Users.class))).thenReturn(user);

        Users guardado = usuarioDAO.saveOrUpdate(user);

        assertNotNull(guardado);
        assertEquals("wilson", guardado.getUsername());
        verify(usuarioRepository).save(user);
    }

    @Test
    void testObtenerTodos() {
        Users u1 = new Users();
        Users u2 = new Users();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Users> lista = usuarioDAO.getAllUsuarios();

        assertEquals(2, lista.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<Users> encontrado = usuarioDAO.getUsuarioById(1);

        assertTrue(encontrado.isPresent());
        assertEquals("wilson", encontrado.get().getUsername());
        verify(usuarioRepository).findById(1);
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioDAO.deleteUsuario(1);

        verify(usuarioRepository).deleteById(1);
    }
}

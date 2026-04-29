package co.edu.unbosque.software_electroadonai;

import co.edu.unbosque.software_electroadonai.model.Empleado;
import co.edu.unbosque.software_electroadonai.services.EmpleadoDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EmpleadoDAO.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmpleadoDAOTest {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Test
    public void testGuardarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setN_DOCUMENTO("123456789");
        empleado.setNOMBRE_EMPLEADO("Juan Pérez");
        empleado.setTELEFONO_EMPLEADO("3001234567");

        Empleado guardado = empleadoDAO.saveOrUpdate(empleado);

        assertNotNull(guardado.getID_EMPLEADO());
        assertEquals("Juan Pérez", guardado.getNOMBRE_EMPLEADO());
    }

    @Test
    public void testBuscarPorId() {
        Empleado empleado = new Empleado();
        empleado.setN_DOCUMENTO("987654321");
        empleado.setNOMBRE_EMPLEADO("María Gómez");
        empleado.setTELEFONO_EMPLEADO("3109876543");

        Empleado guardado = empleadoDAO.saveOrUpdate(empleado);

        Optional<Empleado> encontrado = empleadoDAO.getEmpleadoById(guardado.getID_EMPLEADO());

        assertTrue(encontrado.isPresent());
        assertEquals("María Gómez", encontrado.get().getNOMBRE_EMPLEADO());
    }

    @Test
    public void testListarEmpleados() {
        Empleado e1 = new Empleado();
        e1.setN_DOCUMENTO("111");
        e1.setNOMBRE_EMPLEADO("Empleado Uno");
        e1.setTELEFONO_EMPLEADO("123");

        Empleado e2 = new Empleado();
        e2.setN_DOCUMENTO("222");
        e2.setNOMBRE_EMPLEADO("Empleado Dos");
        e2.setTELEFONO_EMPLEADO("456");

        empleadoDAO.saveOrUpdate(e1);
        empleadoDAO.saveOrUpdate(e2);

        List<Empleado> lista = empleadoDAO.getAllEmpleados();
        System.out.println(lista.get(0));
        System.out.println(lista.get(1));
        assertEquals(9, lista.size());
    }

    @Test
    public void testEliminarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setN_DOCUMENTO("333");
        empleado.setNOMBRE_EMPLEADO("Empleado Eliminar");
        empleado.setTELEFONO_EMPLEADO("789");

        Empleado guardado = empleadoDAO.saveOrUpdate(empleado);

        int id = guardado.getID_EMPLEADO();
        empleadoDAO.deleteEmpleado(id);

        Optional<Empleado> eliminado = empleadoDAO.getEmpleadoById(id);
        assertFalse(eliminado.isPresent());
    }
}

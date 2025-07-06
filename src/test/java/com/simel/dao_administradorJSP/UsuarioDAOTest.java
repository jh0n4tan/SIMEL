/*
package com.simel.dao_administradorJSP;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.List;
import com.simel.modelo_login.Usuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioDAOTest {

    static UsuarioDAO usuarioDAO;
    static int testUserId;
    // Variable para almacenar el usuario único generado
    static String usuarioUnico;

    @BeforeAll
    public static void setup() {
        usuarioDAO = new UsuarioDAO();
        // Crear usuario único con timestamp
        usuarioUnico = "usuario_test_" + System.currentTimeMillis();
    }

    @Test
    @Order(1)
    public void testInsertarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setUsuario(usuarioUnico);  // Usuario único
        usuario.setContrasena("password123");
        usuario.setRol("alumno");
        usuario.setEstado("activo");

        boolean resultado = usuarioDAO.insertarUsuario(usuario);
        assertTrue(resultado, "La inserción debería ser exitosa");

        // Obtener el usuario insertado para guardar id
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        testUserId = usuarios.stream()
                        .filter(u -> u.getUsuario().equals(usuarioUnico))
                        .map(Usuario::getId)
                        .findFirst()
                        .orElse(0);

        assertTrue(testUserId > 0, "El usuario insertado debería tener un id válido");
    }

    @Test
    @Order(2)
    public void testEditarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(testUserId);
        usuario.setNombre("Usuario Test Editado");
        usuario.setUsuario(usuarioUnico);
        usuario.setContrasena("newpassword456");
        usuario.setRol("docente");
        usuario.setEstado("activo");

        boolean resultado = usuarioDAO.editarUsuario(usuario);
        assertTrue(resultado, "La edición debería ser exitosa");
    }

    @Test
    @Order(3)
    public void testInactivarUsuario() {
        boolean resultado = usuarioDAO.inactivarUsuario(testUserId);
        assertTrue(resultado, "El usuario debería poder ser inactivado");
    }

    @Test
    @Order(4)
    public void testObtenerUsuarios() {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        assertNotNull(usuarios);
        assertTrue(usuarios.size() > 0, "Debe haber al menos un usuario");
    }
}
*/

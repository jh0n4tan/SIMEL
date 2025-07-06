package com.simel.dao_administradorJSP;

import com.simel.modelo_login.Usuario;
import com.simel.conexion.DataSourceProvider;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM login";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setUsuario(rs.getString("usuario"));
                // Aquí obtienes la contraseña hashed, pero no deberías enviarla al cliente.
                usuario.setContrasena(rs.getString("contraseña"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean insertarUsuario(Usuario usuario) {
        String sqlLogin = "INSERT INTO login (nombre, usuario, contraseña, rol, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement psLogin = conn.prepareStatement(sqlLogin, PreparedStatement.RETURN_GENERATED_KEYS)) {

            psLogin.setString(1, usuario.getNombre());
            psLogin.setString(2, usuario.getUsuario());
            psLogin.setString(3, hashPassword(usuario.getContrasena()));
            psLogin.setString(4, usuario.getRol());
            psLogin.setString(5, usuario.getEstado());

            int filasLogin = psLogin.executeUpdate();

            if (filasLogin > 0) {
                try (ResultSet rs = psLogin.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idLogin = rs.getInt(1); // ID generado en login

                        // Insertar en tabla correspondiente según el rol
                        if ("docente".equalsIgnoreCase(usuario.getRol())) {
                            return insertarDocente(usuario.getNombre(), idLogin);
                        } else if ("alumno".equalsIgnoreCase(usuario.getRol())) {
                            return insertarAlumnoSimple(usuario.getNombre(), idLogin);
                        } else {
                            return true; // si el rol no requiere inserción adicional
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Inserta en tabla docente
    private boolean insertarDocente(String nombre, int idLogin) throws SQLException {
        String sql = "INSERT INTO docente (nombre, id_login) VALUES (?, ?)";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idLogin);
            return ps.executeUpdate() > 0;
        }
    }

// Inserta en tabla alumno_simple
    private boolean insertarAlumnoSimple(String nombre, int idLogin) throws SQLException {
        String sql = "INSERT INTO alumno_simple (nombre, id_login) VALUES (?, ?)";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idLogin);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean editarUsuario(Usuario usuario) {
        String sqlSinPass = "UPDATE login SET nombre = ?, usuario = ?, rol = ?, estado = ? WHERE id = ?";
        String sqlConPass = "UPDATE login SET nombre = ?, usuario = ?, contraseña = ?, rol = ?, estado = ? WHERE id = ?";

        try (Connection conn = DataSourceProvider.getConnection()) {
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSinPass)) {
                    ps.setString(1, usuario.getNombre());
                    ps.setString(2, usuario.getUsuario());
                    ps.setString(3, usuario.getRol());
                    ps.setString(4, usuario.getEstado());
                    ps.setInt(5, usuario.getId());
                    return ps.executeUpdate() > 0;
                }
            } else {
                String hashedPass = hashPassword(usuario.getContrasena());
                try (PreparedStatement ps = conn.prepareStatement(sqlConPass)) {
                    ps.setString(1, usuario.getNombre());
                    ps.setString(2, usuario.getUsuario());
                    ps.setString(3, hashedPass);
                    ps.setString(4, usuario.getRol());
                    ps.setString(5, usuario.getEstado());
                    ps.setInt(6, usuario.getId());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean inactivarUsuario(int id) {
        String sql = "UPDATE login SET estado = 'inactivo' WHERE id = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

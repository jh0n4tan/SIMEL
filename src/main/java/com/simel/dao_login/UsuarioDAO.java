package com.simel.dao_login;

import com.simel.modelo_login.Usuario;
import com.simel.conexion.DataSourceProvider;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public Usuario validar(String usuario, String contrasenaIngresada) {
        Usuario u = null;

        String sql = "SELECT * FROM login WHERE usuario = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contrasenaBD = rs.getString("contraseña");

                // 🔍 Detectar si es hash (por la forma de BCrypt)
                boolean esHash = contrasenaBD.startsWith("$2a$") || contrasenaBD.startsWith("$2b$");

                boolean valida = esHash
                        ? BCrypt.checkpw(contrasenaIngresada, contrasenaBD) // ✅ comparas con hash
                        : contrasenaIngresada.equals(contrasenaBD);         // ✅ comparas con texto plano

                if (valida) {
                    // Si fue texto plano, entonces actualizamos a hash
                    if (!esHash) {
                        String hash = BCrypt.hashpw(contrasenaIngresada, BCrypt.gensalt());
                        actualizarContrasenaHash(usuario, hash); // 💾 Guardamos el hash en la BD
                    }

                    // 🎯 Creamos el objeto Usuario
                    u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setContrasena(contrasenaBD); // En este punto puede ser texto o hash
                    u.setRol(rs.getString("rol"));
                    u.setEstado(rs.getString("estado"));
                    u.setFechaCreacion(rs.getString("fecha_creacion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    // 🔐 Método para guardar la nueva contraseña encriptada
    private void actualizarContrasenaHash(String usuario, String hash) {
        String sql = "UPDATE login SET contraseña = ? WHERE usuario = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hash);
            ps.setString(2, usuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

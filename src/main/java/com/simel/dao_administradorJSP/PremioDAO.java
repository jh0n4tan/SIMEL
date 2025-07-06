package com.simel.dao_administradorJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.Premio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremioDAO {

    public List<Premio> obtenerPremios() {
        List<Premio> lista = new ArrayList<>();
        String sql = "SELECT * FROM premio";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Premio premio = new Premio();
                premio.setId(rs.getInt("id"));
                premio.setNombre(rs.getString("nombre"));
                premio.setDescripcion(rs.getString("descripcion"));
                premio.setPuntosRequeridos(rs.getInt("puntos_requeridos"));
                premio.setTipo(rs.getString("tipo"));
                premio.setImagen(rs.getString("imagen"));
                lista.add(premio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean insertarPremio(Premio premio) {
        String sql = "INSERT INTO premio (nombre, descripcion, puntos_requeridos, tipo, imagen) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, premio.getNombre());
            ps.setString(2, premio.getDescripcion());
            ps.setInt(3, premio.getPuntosRequeridos());
            ps.setString(4, premio.getTipo());
            ps.setString(5, premio.getImagen());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarPremio(Premio premio) {
        String sql = "UPDATE premio SET nombre = ?, descripcion = ?, puntos_requeridos = ?, tipo = ?, imagen = ? WHERE id = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, premio.getNombre());
            ps.setString(2, premio.getDescripcion());
            ps.setInt(3, premio.getPuntosRequeridos());
            ps.setString(4, premio.getTipo());
            ps.setString(5, premio.getImagen());
            ps.setInt(6, premio.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

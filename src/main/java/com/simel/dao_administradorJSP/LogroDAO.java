package com.simel.dao_administradorJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.Logro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogroDAO {

    public List<Logro> obtenerLogros() {
        List<Logro> lista = new ArrayList<>();
        String sql = "SELECT * FROM logro";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Logro logro = new Logro();
                logro.setId(rs.getInt("id"));
                logro.setNombre(rs.getString("nombre"));
                logro.setDescripcion(rs.getString("descripcion"));
                lista.add(logro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean insertarLogro(Logro logro) {
        String sql = "INSERT INTO logro (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, logro.getNombre());
            ps.setString(2, logro.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarLogro(Logro logro) {
        String sql = "UPDATE logro SET nombre = ?, descripcion = ? WHERE id = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, logro.getNombre());
            ps.setString(2, logro.getDescripcion());
            ps.setInt(3, logro.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

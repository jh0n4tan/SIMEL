package com.simel.dao_administradorJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.Canje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CanjeDAO {

    // Método para obtener la lista de canjes realizados
    public List<Canje> obtenerCanjes() {
        List<Canje> canjes = new ArrayList<>();

        String sql = "SELECT a.nombre AS alumnoNombre, p.nombre AS premioNombre, " +
                     "c.codigo_canje AS codigoCanje, c.estado, c.fecha_canje " +
                     "FROM canje_premio c " +
                     "JOIN alumno a ON c.id_alumno = a.id " +
                     "JOIN premio p ON c.id_premio = p.id " +
                     "ORDER BY c.fecha_canje DESC";

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Canje canje = new Canje();
                canje.setAlumnoNombre(rs.getString("alumnoNombre"));
                canje.setPremioNombre(rs.getString("premioNombre"));
                canje.setCodigoCanje(rs.getString("codigoCanje"));
                canje.setEstado(rs.getString("estado"));
                canje.setFechaCanje(rs.getTimestamp("fecha_canje"));
                canjes.add(canje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes manejar la excepción
        }

        return canjes;
    }
}

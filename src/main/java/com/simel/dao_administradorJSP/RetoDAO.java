package com.simel.dao_administradorJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.Reto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetoDAO {

    public boolean insertarReto(Reto reto) {
        String sql = "INSERT INTO reto (nombre, descripcion, puntos, id_grado_seccion) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reto.getNombre());
            ps.setString(2, reto.getDescripcion());
            ps.setInt(3, reto.getPuntos());
            ps.setInt(4, reto.getIdGradoSeccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reto> obtenerRetos() {
        List<Reto> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.nombre, r.descripcion, r.puntos, r.id_grado_seccion, "
                + "g.grado, g.seccion "
                + "FROM reto r "
                + "INNER JOIN grado_seccion g ON r.id_grado_seccion = g.id";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reto reto = new Reto();
                reto.setId(rs.getInt("id"));
                reto.setNombre(rs.getString("nombre"));
                reto.setDescripcion(rs.getString("descripcion"));
                reto.setPuntos(rs.getInt("puntos"));
                reto.setIdGradoSeccion(rs.getInt("id_grado_seccion"));

                // Aquí seteas grado y sección desde la tabla relacionada
                reto.setGrado(rs.getInt("grado"));
                reto.setSeccion(rs.getString("seccion"));

                lista.add(reto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean editarReto(Reto reto) {
        String sql = "UPDATE reto SET nombre = ?, descripcion = ?, puntos = ?, id_grado_seccion = ? WHERE id = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reto.getNombre());
            ps.setString(2, reto.getDescripcion());
            ps.setInt(3, reto.getPuntos());
            ps.setInt(4, reto.getIdGradoSeccion());
            ps.setInt(5, reto.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

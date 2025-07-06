package com.simel.dao_alumnoJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_alumnoJSP.Reto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetoDAO {

    public List<Reto> obtenerRetosPorGradoSeccionId(int idGradoSeccion) {
        List<Reto> retos = new ArrayList<>();

        String sql = "SELECT id, nombre, descripcion, puntos, id_grado_seccion FROM reto WHERE id_grado_seccion = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGradoSeccion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reto reto = new Reto();
                reto.setId(rs.getInt("id"));
                reto.setNombre(rs.getString("nombre"));
                reto.setDescripcion(rs.getString("descripcion"));
                reto.setPuntos(rs.getInt("puntos"));
                reto.setIdGradoSeccion(rs.getInt("id_grado_seccion"));
                retos.add(reto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retos;
    }
}

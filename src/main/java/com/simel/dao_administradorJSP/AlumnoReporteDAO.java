package com.simel.dao_administradorJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_docenteJSP.Alumno;
import java.sql.*;
import java.util.*;

public class AlumnoReporteDAO {

    public List<Alumno> obtenerRankingBasico() throws SQLException {
        List<Alumno> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, puntos_totales FROM alumno ORDER BY puntos_totales DESC, nombre ASC";

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alumno a = new Alumno();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setPuntos(rs.getInt("puntos_totales"));
                // no asignamos medalla aquí
                lista.add(a);
            }
        }

        return lista;
    }
}

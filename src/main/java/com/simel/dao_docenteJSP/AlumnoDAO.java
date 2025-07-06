package com.simel.dao_docenteJSP;

import com.simel.modelo_docenteJSP.Alumno;
import com.simel.conexion.DataSourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public int contarAlumnos() {
        String sql = "SELECT COUNT(*) FROM alumno";
        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Alumno> obtenerRankingAlumnosConMedallas(int pagina, int tamPagina) {
        List<Alumno> ranking = new ArrayList<>();
        int offset = (pagina - 1) * tamPagina;
        String sql = "SELECT id, nombre, puntos_totales FROM alumno ORDER BY puntos_totales DESC LIMIT ? OFFSET ?";

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tamPagina);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            int oro = -1, plata = -1, bronce = -1;

            while (rs.next()) {
                Alumno a = new Alumno();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setPuntos(rs.getInt("puntos_totales"));
                int p = a.getPuntos();

                if (p > 0) {
                    if (oro == -1 || p == oro) {
                        if (oro == -1) oro = p;
                        a.setMedalla("oro");
                    } else if (plata == -1 || p == plata) {
                        if (plata == -1) plata = p;
                        a.setMedalla("plata");
                    } else if (bronce == -1 || p == bronce) {
                        if (bronce == -1) bronce = p;
                        a.setMedalla("bronce");
                    }
                }
                ranking.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranking;
    }
}





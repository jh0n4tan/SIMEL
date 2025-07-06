package com.simel.dao_alumnoJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_alumnoJSP.Reto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetoAlumnoDAO {

    public boolean insertarRetoPendiente(int idAlumno, int idReto) {
        String sqlVerificar = "SELECT COUNT(*) FROM reto_alumno WHERE id_alumno = ? AND id_reto = ?";
        String sqlInsertar = "INSERT INTO reto_alumno (id_alumno, id_reto, estado, puntos_ganados) VALUES (?, ?, 'pendiente', 0)";

        try (Connection conn = DataSourceProvider.getConnection()) {

            // Verifica si ya está registrado
            try (PreparedStatement stmt = conn.prepareStatement(sqlVerificar)) {
                stmt.setInt(1, idAlumno);
                stmt.setInt(2, idReto);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Ya existe
                }
            }

            // Inserta si no existe
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {
                stmt.setInt(1, idAlumno);
                stmt.setInt(2, idReto);
                int filas = stmt.executeUpdate();
                return filas > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Reto> obtenerRetosDisponibles(int idAlumno, int idGradoSeccion) {
        List<Reto> retos = new ArrayList<>();

        String sql = "SELECT r.id, r.nombre, r.descripcion, r.puntos, r.id_grado_seccion "
                + "FROM reto r "
                + "WHERE r.id_grado_seccion = ? "
                + "AND r.id NOT IN (SELECT id_reto FROM reto_alumno WHERE id_alumno = ?)";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGradoSeccion);
            stmt.setInt(2, idAlumno);

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

    public List<Reto> obtenerRetosPorEstado(int idAlumno, String estado) {
        List<Reto> retos = new ArrayList<>();

        String sql = "SELECT r.id, r.nombre, r.descripcion, r.puntos, r.id_grado_seccion "
                + "FROM reto r "
                + "JOIN reto_alumno ra ON r.id = ra.id_reto "
                + "WHERE ra.id_alumno = ? AND ra.estado = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            stmt.setString(2, estado); // "pendiente" o "completado"

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





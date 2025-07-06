package com.simel.dao_docenteJSP;

import com.simel.modelo_docenteJSP.RetoAlumno;
import com.simel.conexion.DataSourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetoAlumnoDAO {

    // 🔹 Obtener retos pendientes de un alumno
    public List<RetoAlumno> obtenerRetosPendientesPorAlumno(int idAlumno) {
        List<RetoAlumno> retos = new ArrayList<>();

        String sql = "SELECT ra.id, r.nombre, r.descripcion, r.puntos, ra.estado "
                + "FROM reto_alumno ra "
                + "JOIN reto r ON ra.id_reto = r.id "
                + "WHERE ra.id_alumno = ? AND ra.estado = 'pendiente'";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RetoAlumno reto = new RetoAlumno();
                reto.setId(rs.getInt("id"));
                reto.setNombre(rs.getString("nombre"));
                reto.setDescripcion(rs.getString("descripcion"));
                reto.setPuntos(rs.getInt("puntos"));
                reto.setEstado(rs.getString("estado"));
                retos.add(reto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retos;
    }

    // 🔹 Actualizar estado del reto
    public boolean actualizarEstadoReto(int idRetoAlumno, String nuevoEstado) {
        try (Connection conn = DataSourceProvider.getConnection()) {

            if (nuevoEstado.equalsIgnoreCase("rechazado")) {
                // Eliminar reto_alumno si está rechazado
                String deleteSql = "DELETE FROM reto_alumno WHERE id = ?";
                try (PreparedStatement stmtDelete = conn.prepareStatement(deleteSql)) {
                    stmtDelete.setInt(1, idRetoAlumno);
                    int filas = stmtDelete.executeUpdate();
                    return filas > 0;
                }
            } else {
                // Actualizar estado
                String sql = "UPDATE reto_alumno SET estado = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nuevoEstado);
                    stmt.setInt(2, idRetoAlumno);
                    int filas = stmt.executeUpdate();

                    // Si completado, sumamos puntos
                    if (filas > 0 && nuevoEstado.equalsIgnoreCase("completado")) {
                        String puntosSql = "UPDATE alumno a "
                                + "JOIN reto_alumno ra ON a.id = ra.id_alumno "
                                + "JOIN reto r ON ra.id_reto = r.id "
                                + "SET a.puntos_totales = a.puntos_totales + r.puntos "
                                + "WHERE ra.id = ?";

                        try (PreparedStatement stmtPuntos = conn.prepareStatement(puntosSql)) {
                            stmtPuntos.setInt(1, idRetoAlumno);
                            stmtPuntos.executeUpdate();
                        }
                    }
                    return filas > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

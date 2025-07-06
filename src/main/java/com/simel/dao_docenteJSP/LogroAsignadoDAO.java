package com.simel.dao_docenteJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_docenteJSP.LogroAsignado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogroAsignadoDAO {

    public boolean asignarLogro(int idAlumno, int idCurso, int idGradoSeccion, int idLogro, int puntos, String comentario) {
        String sql = "INSERT INTO logro_asignado (id_alumno, id_curso, id_grado_seccion, id_logro, puntos, comentario) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idCurso);
            stmt.setInt(3, idGradoSeccion); // ahora es INT correctamente
            stmt.setInt(4, idLogro);
            stmt.setInt(5, puntos);
            stmt.setString(6, comentario);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean logroYaAsignado(int idAlumno, int idLogro) {
        String sql = "SELECT COUNT(*) FROM logro_asignado WHERE id_alumno = ? AND id_logro = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idLogro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // true si ya existe el logro para ese alumno
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarPuntosAlumno(int idAlumno, int puntosSumados) {
        String sql = "UPDATE alumno SET puntos_totales = puntos_totales + ? WHERE id = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, puntosSumados);
            stmt.setInt(2, idAlumno);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener historial de logros asignados con info completa
    public List<LogroAsignado> obtenerHistorialLogros(int idDocente) {
        List<LogroAsignado> lista = new ArrayList<>();

        String sql = "SELECT la.id, a.nombre AS nombre_alumno, c.nombre AS nombre_curso, "
                + "gs.grado, gs.seccion, l.nombre AS nombre_logro, la.puntos, "
                + "la.comentario, la.fecha_asignacion "
                + "FROM logro_asignado la "
                + "JOIN alumno a ON la.id_alumno = a.id "
                + "JOIN curso c ON la.id_curso = c.id "
                + "JOIN grado_seccion gs ON la.id_grado_seccion = gs.id "
                + "JOIN logro l ON la.id_logro = l.id "
                + "JOIN curso_grado_docente ca ON ca.id_curso = la.id_curso AND ca.id_grado_seccion = la.id_grado_seccion "
                + "WHERE ca.id_docente = ? "
                + "ORDER BY la.fecha_asignacion DESC";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDocente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LogroAsignado la = new LogroAsignado();
                    la.setId(rs.getInt("id"));
                    la.setNombreAlumno(rs.getString("nombre_alumno"));
                    la.setNombreCurso(rs.getString("nombre_curso"));
                    la.setGrado(rs.getInt("grado"));
                    la.setSeccion(rs.getString("seccion"));
                    la.setNombreLogro(rs.getString("nombre_logro"));
                    la.setPuntos(rs.getInt("puntos"));
                    la.setComentario(rs.getString("comentario"));
                    la.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));

                    lista.add(la);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}

package com.simel.dao_alumnoJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_alumnoJSP.CanjePremio;
import com.simel.modelo_alumnoJSP.CursoAlumno;
import com.simel.modelo_alumnoJSP.Evaluacion;
import com.simel.modelo_alumnoJSP.LogroAsignado;
import com.simel.modelo_alumnoJSP.NivelInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public List<CursoAlumno> obtenerCursosPorAlumno(int idLogin) {
        List<CursoAlumno> cursos = new ArrayList<>();

        String sql = "SELECT c.id AS idCurso, c.nombre AS nombreCurso, d.nombre AS nombreDocente, "
                + "gs.grado, gs.seccion, gs.id AS idGradoSeccion "
                + "FROM alumno a "
                + "JOIN grado_seccion gs ON a.id_grado_seccion = gs.id "
                + "JOIN curso_grado_docente cgd ON gs.id = cgd.id_grado_seccion "
                + "JOIN curso c ON cgd.id_curso = c.id "
                + "JOIN docente d ON cgd.id_docente = d.id "
                + "WHERE a.id_login = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLogin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CursoAlumno curso = new CursoAlumno();
                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNombreCurso(rs.getString("nombreCurso"));
                curso.setNombreDocente(rs.getString("nombreDocente"));
                curso.setGrado(rs.getInt("grado"));
                curso.setSeccion(rs.getString("seccion"));
                curso.setEstado("Activo"); // o la lógica que uses para el estado
                curso.setIdGradoSeccion(rs.getInt("idGradoSeccion"));
                cursos.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    public int obtenerIdAlumnoPorLogin(int idLogin) {
        String sql = "SELECT id FROM alumno WHERE id_login = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLogin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // No encontrado
    }

    public NivelInfo obtenerNivelPorLogin(int idLogin) {
        String sql = "SELECT puntos_totales FROM alumno WHERE id_login = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLogin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int puntos = rs.getInt("puntos_totales");

                // Lógica para determinar el nivel basado en bloques de 100 puntos
                int nivel = 0;
                int puntosMin = 0;
                int puntosMax = 100;

                while (puntos >= puntosMax) {
                    nivel++;
                    puntosMin = puntosMax;
                    puntosMax += 100;
                }

                return new NivelInfo(nivel, puntos, puntosMin, puntosMax);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Evaluacion obtenerEvaluacionPorAlumnoCursoYGradoSeccion(int idAlumno, int idCurso, int idGradoSeccion) {
        String sql = "SELECT nota_eva1, nota_eva2, nota_eva3, promedio, comentario "
                + "FROM evaluacion_curso_alumno "
                + "WHERE id_alumno = ? AND id_curso = ? AND id_grado_seccion = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idCurso);
            stmt.setInt(3, idGradoSeccion);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double eva1 = rs.getDouble("nota_eva1");
                double eva2 = rs.getDouble("nota_eva2");
                double eva3 = rs.getDouble("nota_eva3");
                double promedio = rs.getDouble("promedio");
                String comentario = rs.getString("comentario") != null ? rs.getString("comentario") : "";

                return new Evaluacion(eva1, eva2, eva3, promedio, comentario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No encontrado o error
    }

    public List<LogroAsignado> obtenerLogrosPorAlumno(int idAlumno) {
        List<LogroAsignado> logros = new ArrayList<>();

        String sql = "SELECT l.nombre AS nombreLogro, c.nombre AS nombreCurso, "
                + "la.puntos, la.fecha_asignacion "
                + "FROM logro_asignado la "
                + "JOIN logro l ON la.id_logro = l.id "
                + "JOIN curso c ON la.id_curso = c.id "
                + "WHERE la.id_alumno = ? "
                + "ORDER BY la.fecha_asignacion DESC";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LogroAsignado logro = new LogroAsignado();
                logro.setNombreLogro(rs.getString("nombreLogro"));
                logro.setNombreCurso(rs.getString("nombreCurso"));
                logro.setPuntos(rs.getInt("puntos"));
                logro.setFechaAsignacion(rs.getTimestamp("fecha_asignacion").toLocalDateTime());
                logros.add(logro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logros;
    }

    public boolean registrarCanje(int idAlumno, int idPremio, String codigo, String estado, int puntosPremio) {
        String insertSQL = "INSERT INTO canje_premio (id_alumno, id_premio, codigo_canje, estado) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE alumno SET puntos_totales = puntos_totales - ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = DataSourceProvider.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción manualmente

            insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setInt(1, idAlumno);
            insertStmt.setInt(2, idPremio);
            insertStmt.setString(3, codigo);
            estado = estado.trim();
            insertStmt.setString(4, estado);
            insertStmt.executeUpdate();

            updateStmt = conn.prepareStatement(updateSQL);
            updateStmt.setInt(1, puntosPremio);
            updateStmt.setInt(2, idAlumno);
            updateStmt.executeUpdate();

            conn.commit(); // Confirmar transacción
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Deshacer cambios en caso de error
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (insertStmt != null) {
                    insertStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar autocommit
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Método para obtener canjes por alumno con INNER JOIN para traer datos de premio
    public List<CanjePremio> obtenerCanjesPorAlumno(int idAlumno) throws SQLException {
        List<CanjePremio> listaCanjes = new ArrayList<>();

        String sql = "SELECT cp.codigo_canje, cp.estado, p.nombre, p.tipo "
                + "FROM canje_premio cp "
                + "INNER JOIN premio p ON cp.id_premio = p.id "
                + "WHERE cp.id_alumno = ? "
                + "ORDER BY cp.fecha_canje DESC";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CanjePremio canje = new CanjePremio();
                    canje.setCodigo(rs.getString("codigo_canje"));
                    canje.setEstado(rs.getString("estado"));
                    canje.setNombre(rs.getString("nombre"));
                    canje.setTipo(rs.getString("tipo"));

                    listaCanjes.add(canje);
                }
            }
        }

        return listaCanjes;
    }

    public boolean yaCanjeoPremio(int idAlumno, int idPremio) {
        String sql = "SELECT COUNT(*) FROM canje_premio WHERE id_alumno = ? AND id_premio = ? AND estado IN ('Canje digital exitoso', 'Canje físico solicitado')";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idPremio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

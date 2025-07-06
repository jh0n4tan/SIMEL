package com.simel.dao_administradorJSP;

import com.simel.modelo_administradorJSP.Alumno;
import com.simel.modelo_administradorJSP.Curso;
import com.simel.modelo_administradorJSP.CursoAsignado;
import com.simel.modelo_administradorJSP.Docente;
import com.simel.modelo_administradorJSP.GradoSeccion;
import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.CursoGradoDocente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {

    public List<Docente> obtenerDocentes() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM docente";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Docente docente = new Docente();
                docente.setId(rs.getInt("id"));
                docente.setNombre(rs.getString("nombre"));
                lista.add(docente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Curso> obtenerCursos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM curso";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNombre(rs.getString("nombre"));
                lista.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<GradoSeccion> obtenerGradosSecciones() {
        List<GradoSeccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM grado_seccion";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GradoSeccion gs = new GradoSeccion();
                gs.setId(rs.getInt("id"));
                gs.setGrado(rs.getInt("grado"));
                gs.setSeccion(rs.getString("seccion"));
                lista.add(gs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<CursoAsignado> obtenerCursosAsignados() {
        List<CursoAsignado> lista = new ArrayList<>();
        String sql = "SELECT d.nombre AS docente_nombre, c.nombre AS curso_nombre, gs.grado, gs.seccion "
                + "FROM curso_grado_docente cgd "
                + "JOIN docente d ON cgd.id_docente = d.id "
                + "JOIN curso c ON cgd.id_curso = c.id "
                + "JOIN grado_seccion gs ON cgd.id_grado_seccion = gs.id";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CursoAsignado asignado = new CursoAsignado();
                asignado.setDocenteNombre(rs.getString("docente_nombre"));
                asignado.setCursoNombre(rs.getString("curso_nombre"));
                asignado.setGrado(rs.getInt("grado"));
                asignado.setSeccion(rs.getString("seccion"));
                lista.add(asignado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Alumno> obtenerAlumnos() {
        List<Alumno> lista = new ArrayList<>();
        // Actualiza la consulta SQL para obtener los alumnos desde la tabla alumno_simple
        String sql = "SELECT id, nombre FROM alumno_simple";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                lista.add(alumno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> obtenerResumenAlumnosPorSeccion() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT gs.grado, gs.seccion, COUNT(*) AS total "
                + "FROM alumno a "
                + "JOIN grado_seccion gs ON a.id_grado_seccion = gs.id "
                + "GROUP BY gs.grado, gs.seccion "
                + "ORDER BY gs.grado, gs.seccion";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int grado = rs.getInt("grado");
                String seccion = rs.getString("seccion");
                int total = rs.getInt("total");

                String resumen = "Sección: <strong>" + grado + "° - " + seccion + "</strong> - Total alumnos: <strong>" + total + "</strong>";
                lista.add(resumen);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Validar si curso ya asignado a otro docente en ese grado/sección
    public boolean existeCursoAsignadoOtroDocente(int idCurso, int idGradoSeccion, int idDocente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM curso_grado_docente WHERE id_curso = ? AND id_grado_seccion = ? AND id_docente != ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ps.setInt(2, idGradoSeccion);
            ps.setInt(3, idDocente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    // Validar si asignación ya existe para ese docente, curso y grado/sección
    public boolean existeAsignacionDocenteCursoGrado(int idDocente, int idCurso, int idGradoSeccion) throws SQLException {
        String sql = "SELECT COUNT(*) FROM curso_grado_docente WHERE id_docente = ? AND id_curso = ? AND id_grado_seccion = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            ps.setInt(2, idCurso);
            ps.setInt(3, idGradoSeccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    // Insertar asignación
    public boolean insertarAsignacion(CursoGradoDocente asignacion) throws SQLException {
        String sql = "INSERT INTO curso_grado_docente (id_docente, id_curso, id_grado_seccion) VALUES (?, ?, ?)";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, asignacion.getIdDocente());
            ps.setInt(2, asignacion.getIdCurso());
            ps.setInt(3, asignacion.getIdGradoSeccion());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Obtener id_login desde alumno_simple por id alumno_simple
    public int obtenerIdLoginPorAlumnoSimple(int idAlumnoSimple) throws SQLException {
        String sql = "SELECT id_login FROM alumno_simple WHERE id = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumnoSimple);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_login");
                }
            }
        }
        return 0; // No encontrado
    }

    // Verificar si el alumno ya está asignado a un grado (en tabla alumno)
    public boolean verificarAlumnoAsignado(int idLogin) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM alumno WHERE id_login = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLogin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }

    // Insertar asignación de alumno a grado/sección
    public boolean insertarAlumnoAsignado(int idLogin, int idGradoSeccion) throws SQLException {
        String sql = "INSERT INTO alumno (nombre, id_login, id_grado_seccion, puntos_totales) "
                + "SELECT nombre, ?, ?, 0 FROM alumno_simple WHERE id_login = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLogin);
            ps.setInt(2, idGradoSeccion);
            ps.setInt(3, idLogin);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

}




package com.simel.dao_docenteJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_administradorJSP.Alumno;
import java.sql.*;
import com.simel.modelo_docenteJSP.CursoAsignadoDocente;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    public int obtenerIdDocentePorUsuario(String nombreUsuario) {
        int idDocente = -1;

        String sql = "SELECT d.id "
                + "FROM docente d "
                + "JOIN login l ON d.id_login = l.id "
                + "WHERE l.usuario = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idDocente = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idDocente;
    }

    public List<CursoAsignadoDocente> obtenerCursosPorDocente(int idDocente) {
        List<CursoAsignadoDocente> cursos = new ArrayList<>();

        String sql = "SELECT c.id AS id_curso, c.nombre AS nombre_curso, gs.grado, gs.seccion, "
                + "gs.id AS id_grado_seccion, "
                + "(SELECT COUNT(*) FROM alumno a WHERE a.id_grado_seccion = gs.id) AS total_alumnos "
                + "FROM curso_grado_docente cgd "
                + "JOIN curso c ON cgd.id_curso = c.id "
                + "JOIN grado_seccion gs ON cgd.id_grado_seccion = gs.id "
                + "WHERE cgd.id_docente = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDocente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CursoAsignadoDocente curso = new CursoAsignadoDocente(
                        rs.getInt("id_curso"),
                        rs.getString("nombre_curso"),
                        rs.getInt("grado"),
                        rs.getString("seccion"),
                        rs.getInt("total_alumnos"),
                        rs.getInt("id_grado_seccion") // ¡Este es el nuevo!
                );

                cursos.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    public List<Alumno> obtenerAlumnosPorCursoGradoSeccion(int idCurso, int grado, String seccion) {
        List<Alumno> alumnos = new ArrayList<>();

        String sql = "SELECT a.id, a.nombre "
                + "FROM alumno a "
                + "JOIN grado_seccion gs ON a.id_grado_seccion = gs.id "
                + "JOIN curso_grado_docente cgd ON cgd.id_grado_seccion = gs.id "
                + "WHERE cgd.id_curso = ? AND gs.grado = ? AND gs.seccion = ?";

        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            stmt.setInt(2, grado);
            stmt.setString(3, seccion);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }

    public int obtenerIdGradoSeccion(int grado, String seccion) {
        int id = -1;
        String sql = "SELECT id FROM grado_seccion WHERE grado = ? AND seccion = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grado);
            stmt.setString(2, seccion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}

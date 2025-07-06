package com.simel.dao_docenteJSP;

import com.simel.conexion.DataSourceProvider;
import com.simel.modelo_docenteJSP.Evaluacion;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EvaluacionDAO {

    public void insertarOActualizarNota(int idAlumno, int idCurso, int idGradoSeccion,
            double nota1, double nota2, double nota3, String comentario) {

        double promedio = (nota1 + nota2 + nota3) / 3.0;

        String sql = "INSERT INTO evaluacion_curso_alumno "
                + "(id_alumno, id_curso, id_grado_seccion, nota_eva1, nota_eva2, nota_eva3, promedio, comentario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "nota_eva1 = VALUES(nota_eva1), nota_eva2 = VALUES(nota_eva2), "
                + "nota_eva3 = VALUES(nota_eva3), promedio = VALUES(promedio), comentario = VALUES(comentario), "
                + "fecha_registro = CURRENT_TIMESTAMP";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idCurso);
            stmt.setInt(3, idGradoSeccion);
            stmt.setDouble(4, nota1);
            stmt.setDouble(5, nota2);
            stmt.setDouble(6, nota3);
            stmt.setDouble(7, promedio);
            stmt.setString(8, comentario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Evaluacion> obtenerEvaluacionesPorCursoGrado(int idCurso, int idGradoSeccion) {
        Map<Integer, Evaluacion> map = new HashMap<>();
        String sql = "SELECT id_alumno, nota_eva1, nota_eva2, nota_eva3, promedio, comentario FROM evaluacion_curso_alumno WHERE id_curso = ? AND id_grado_seccion = ?";
        try (Connection conn = DataSourceProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            stmt.setInt(2, idGradoSeccion);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Evaluacion ev = new Evaluacion();
                ev.setNota1(rs.getDouble("nota_eva1"));
                ev.setNota2(rs.getDouble("nota_eva2"));
                ev.setNota3(rs.getDouble("nota_eva3"));
                ev.setPromedio(rs.getDouble("promedio"));  // nuevo
                ev.setComentario(rs.getString("comentario"));
                map.put(rs.getInt("id_alumno"), ev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

}




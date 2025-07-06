package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.AsignacionDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AsignarAlumno")
public class AsignarAlumnoServlet extends HttpServlet {

    private AsignacionDAO asignacionDAO = new AsignacionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sIdAlumnoSimple = request.getParameter("id_alumno");
        String sIdGradoSeccion = request.getParameter("id_grado_seccion");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String mensajeExito = null;
        String mensajeError = null;

        if (sIdAlumnoSimple == null || sIdGradoSeccion == null || sIdAlumnoSimple.isEmpty() || sIdGradoSeccion.isEmpty()) {
            mensajeError = "Debe seleccionar alumno y grado/sección.";
        } else {
            try {
                int idAlumnoSimple = Integer.parseInt(sIdAlumnoSimple);
                int idGradoSeccion = Integer.parseInt(sIdGradoSeccion);

                // Paso 1: Obtener el id_login desde alumno_simple
                int idLogin = asignacionDAO.obtenerIdLoginPorAlumnoSimple(idAlumnoSimple);

                if (idLogin == 0) {
                    mensajeError = "No se encontró el login asociado al alumno.";
                } else if (asignacionDAO.verificarAlumnoAsignado(idLogin)) {
                    mensajeError = "Este alumno ya está asignado a un grado/sección.";
                } else {
                    // Paso 2: Insertar asignación
                    boolean insertado = asignacionDAO.insertarAlumnoAsignado(idLogin, idGradoSeccion);

                    if (insertado) {
                        mensajeExito = "Alumno asignado correctamente.";
                    } else {
                        mensajeError = "No se pudo asignar al alumno.";
                    }
                }

            } catch (NumberFormatException e) {
                mensajeError = "Datos inválidos.";
            } catch (SQLException e) {
                mensajeError = "Error en base de datos: " + e.getMessage();
            }
        }

        // Construir respuesta JSON
        StringBuilder json = new StringBuilder("{");

        if (mensajeExito != null) {
            json.append("\"mensajeExito\":\"").append(mensajeExito.replace("\"", "\\\"")).append("\"");
        } else if (mensajeError != null) {
            json.append("\"mensajeError\":\"").append(mensajeError.replace("\"", "\\\"")).append("\"");
        }

        json.append("}");

        response.getWriter().write(json.toString());
    }
}
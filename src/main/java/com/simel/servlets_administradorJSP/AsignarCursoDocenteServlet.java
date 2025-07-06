package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.AsignacionDAO;
import com.simel.modelo_administradorJSP.CursoGradoDocente;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AsignarCursoDocente")
public class AsignarCursoDocenteServlet extends HttpServlet {

    private AsignacionDAO asignacionDAO = new AsignacionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Al final del proceso, en vez de forward a JSP, responder JSON
        String sIdDocente = request.getParameter("id_docente");
        String sIdCurso = request.getParameter("id_curso");
        String sIdGradoSeccion = request.getParameter("id_grado_seccion");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String mensajeExito = null;
        String mensajeError = null;

        if (sIdDocente == null || sIdCurso == null || sIdGradoSeccion == null
                || sIdDocente.isEmpty() || sIdCurso.isEmpty() || sIdGradoSeccion.isEmpty()) {
            mensajeError = "Debe seleccionar todos los campos.";
        } else {
            try {
                int idDocente = Integer.parseInt(sIdDocente);
                int idCurso = Integer.parseInt(sIdCurso);
                int idGradoSeccion = Integer.parseInt(sIdGradoSeccion);

                if (asignacionDAO.existeCursoAsignadoOtroDocente(idCurso, idGradoSeccion, idDocente)) {
                    mensajeError = "El curso ya está asignado a otro docente en ese grado/sección.";
                } else if (asignacionDAO.existeAsignacionDocenteCursoGrado(idDocente, idCurso, idGradoSeccion)) {
                    mensajeError = "Este docente ya tiene asignado ese curso en ese grado/sección.";
                } else {
                    CursoGradoDocente nuevaAsignacion = new CursoGradoDocente();
                    nuevaAsignacion.setIdDocente(idDocente);
                    nuevaAsignacion.setIdCurso(idCurso);
                    nuevaAsignacion.setIdGradoSeccion(idGradoSeccion);

                    boolean insertado = asignacionDAO.insertarAsignacion(nuevaAsignacion);
                    if (insertado) {
                        mensajeExito = "Asignación realizada correctamente.";
                    } else {
                        mensajeError = "Error al insertar la asignación.";
                    }
                }

            } catch (NumberFormatException e) {
                mensajeError = "Datos numéricos inválidos.";
            } catch (SQLException e) {
                mensajeError = "Error de base de datos: " + e.getMessage();
            }
        }

        // Crear JSON
        String json = "{";

        if (mensajeExito != null) {
            json += "\"mensajeExito\":\"" + mensajeExito.replace("\"", "\\\"") + "\"";
        } else if (mensajeError != null) {
            json += "\"mensajeError\":\"" + mensajeError.replace("\"", "\\\"") + "\"";
        }

        json += "}";

        response.getWriter().write(json);
    }
}



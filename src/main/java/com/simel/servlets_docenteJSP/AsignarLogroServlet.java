package com.simel.servlets_docenteJSP;

import com.simel.dao_docenteJSP.DocenteDAO;
import com.simel.dao_docenteJSP.LogroAsignadoDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AsignarLogroServlet")
public class AsignarLogroServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idAlumno = Integer.parseInt(request.getParameter("idAlumno"));
            int idCurso = Integer.parseInt(request.getParameter("idCurso"));
            int idGradoSeccion = Integer.parseInt(request.getParameter("idGradoSeccion"));
            int idLogro = Integer.parseInt(request.getParameter("idLogro"));
            int puntos = Integer.parseInt(request.getParameter("puntos"));
            String comentario = request.getParameter("comentario");

            LogroAsignadoDAO dao = new LogroAsignadoDAO();

            // Validación para evitar asignar el mismo logro dos veces
            if (dao.logroYaAsignado(idAlumno, idLogro)) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\":false, \"msg\":\"El alumno ya tiene asignado este logro.\"}");
                return;
            }

            boolean insertado = dao.asignarLogro(idAlumno, idCurso, idGradoSeccion, idLogro, puntos, comentario);

            response.setContentType("application/json");
            if (insertado) {
                dao.actualizarPuntosAlumno(idAlumno, puntos);
                response.getWriter().write("{\"success\":true, \"msg\":\"Logro asignado correctamente\"}");
            } else {
                response.getWriter().write("{\"success\":false, \"msg\":\"Error al asignar el logro\"}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false, \"msg\":\"Parámetros inválidos\"}");
        }
    }
}

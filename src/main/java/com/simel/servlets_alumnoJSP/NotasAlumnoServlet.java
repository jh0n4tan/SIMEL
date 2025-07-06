
package com.simel.servlets_alumnoJSP;

import com.google.gson.Gson;
import com.simel.dao_alumnoJSP.AlumnoDAO;
import com.simel.modelo_alumnoJSP.Evaluacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notasAlumno")
public class NotasAlumnoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_login") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idLoginAlumno = (int) session.getAttribute("id_login");
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        int idAlumno = alumnoDAO.obtenerIdAlumnoPorLogin(idLoginAlumno);
        if (idAlumno == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Alumno no encontrado.");
            return;
        }

        String idCursoGrado = request.getParameter("idCurso");
        if (idCursoGrado == null || !idCursoGrado.contains("-")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro inválido.");
            return;
        }

        // idCurso y idGradoSeccion vienen concatenados como "3-1"
        String[] partes = idCursoGrado.split("-");
        int idCurso = Integer.parseInt(partes[0]);
        int idGradoSeccion = Integer.parseInt(partes[1]);

        Evaluacion evaluacion = alumnoDAO.obtenerEvaluacionPorAlumnoCursoYGradoSeccion(idAlumno, idCurso, idGradoSeccion);

        // Enviar resultado en JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (evaluacion == null) {
            response.getWriter().write("{}"); // No hay datos
        } else {
            // Usar simple formato JSON manual o usar librería si quieres
            String json = String.format(
                "{\"notaEva1\":%.2f,\"notaEva2\":%.2f,\"notaEva3\":%.2f,\"promedio\":%.2f,\"comentario\":\"%s\"}",
                evaluacion.getNotaEva1(),
                evaluacion.getNotaEva2(),
                evaluacion.getNotaEva3(),
                evaluacion.getPromedio(),
                evaluacion.getComentario().replace("\"", "\\\"") // escape simple
            );
            response.getWriter().write(json);
        }
    }
}

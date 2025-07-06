package com.simel.servlets_alumnoJSP;

import com.simel.dao_alumnoJSP.AlumnoDAO;
import com.simel.dao_alumnoJSP.RetoAlumnoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/marcarReto")
public class RetoAlumnoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_login") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int idLogin = (int) session.getAttribute("id_login");
        int idReto = Integer.parseInt(request.getParameter("idReto"));

        // Obtener id del alumno desde su login
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        int idAlumno = alumnoDAO.obtenerIdAlumnoPorLogin(idLogin);

        if (idAlumno <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Insertar en la tabla reto_alumno
        RetoAlumnoDAO retoAlumnoDAO = new RetoAlumnoDAO();
        boolean exito = retoAlumnoDAO.insertarRetoPendiente(idAlumno, idReto);

        if (exito) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // ya existe o fallo
        }
    }
}

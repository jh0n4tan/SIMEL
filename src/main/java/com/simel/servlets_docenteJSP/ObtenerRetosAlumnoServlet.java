package com.simel.servlets_docenteJSP;

import com.simel.dao_docenteJSP.RetoAlumnoDAO;
import com.simel.modelo_docenteJSP.RetoAlumno;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/obtenerRetosAlumno")
public class ObtenerRetosAlumnoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idAlumnoParam = request.getParameter("idAlumno");

        if (idAlumnoParam == null || idAlumnoParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro idAlumno");
            return;
        }

        int idAlumno;
        try {
            idAlumno = Integer.parseInt(idAlumnoParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "idAlumno inválido");
            return;
        }

        RetoAlumnoDAO dao = new RetoAlumnoDAO();
        List<RetoAlumno> retos = dao.obtenerRetosPendientesPorAlumno(idAlumno);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new Gson().toJson(retos);
        response.getWriter().write(json);
    }
}

package com.simel.servlets_docenteJSP;

import com.simel.dao_docenteJSP.RetoAlumnoDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/actualizarEstadoReto")
public class ActualizarEstadoRetoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idRetoAlumnoParam = request.getParameter("idRetoAlumno");
        String nuevoEstado = request.getParameter("nuevoEstado");

        if (idRetoAlumnoParam == null || nuevoEstado == null || idRetoAlumnoParam.isEmpty() || nuevoEstado.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros necesarios");
            return;
        }

        int idRetoAlumno;
        try {
            idRetoAlumno = Integer.parseInt(idRetoAlumnoParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "idRetoAlumno inválido");
            return;
        }

        RetoAlumnoDAO dao = new RetoAlumnoDAO();
        boolean actualizado = dao.actualizarEstadoReto(idRetoAlumno, nuevoEstado);

        if (actualizado) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Estado actualizado correctamente");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar estado");
        }
    }
}

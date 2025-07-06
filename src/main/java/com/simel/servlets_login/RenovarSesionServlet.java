package com.simel.servlets_login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "RenovarSesionServlet", urlPatterns = {"/RenovarSesionServlet"})
public class RenovarSesionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Aquí simplemente "tocamos" la sesión para que el servidor la mantenga viva
            session.setMaxInactiveInterval(session.getMaxInactiveInterval());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Sesión no encontrada\"}");
        }
    }
}

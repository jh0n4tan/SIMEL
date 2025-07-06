
package com.simel.servlets_LogOut;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Invalida la sesión actual si existe
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirige al login o página principal
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // Puedes agregar doPost si lo deseas, aquí solo el GET suele ser suficiente
}



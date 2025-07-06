package com.simel.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/administrador", "/docente", "/alumno"})
public class RolFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Puedes dejar vacío si no necesitas inicializar nada
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // No crear sesión nueva
        String rol = (session != null) ? (String) session.getAttribute("rol") : null;
        String uri = req.getRequestURI();

        if (session == null || rol == null) {
            // Redirige al login si no hay sesión activa o no hay rol
            res.sendRedirect(req.getContextPath() + "/index.jsp?expired=true");
            return;
        }

        // Verificar si el rol corresponde con la ruta
        if ((uri.contains("/administrador") && !rol.equals("administrador")) ||
            (uri.contains("/docente") && !rol.equals("docente")) ||
            (uri.contains("/alumno") && !rol.equals("alumno"))) {
            // Redirige a acceso denegado si intenta acceder a una ruta que no le corresponde
            res.sendRedirect(req.getContextPath() + "/accesoDenegado.jsp");
            return;
        }

        // Si todo está correcto, permite el acceso
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Libera recursos si es necesario
    }
}

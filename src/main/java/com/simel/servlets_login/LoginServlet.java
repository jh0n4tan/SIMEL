package com.simel.servlets_login;

import com.simel.dao_login.UsuarioDAO;
import com.simel.modelo_login.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final int TIEMPO_SESION_ALUMNO = 3 * 60;      // 3 minutos
    private static final int TIEMPO_SESION_DOCENTE = 3 * 60;       // 3 minutos
    private static final int TIEMPO_SESION_ADMIN = 3 * 60;         // 3 minutos

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.validar(usuario, password);

        if (user == null) {
            out.write("{\"status\":\"error\", \"message\":\"Usuario y/o contraseña incorrecta\"}");
            return;
        }

        if ("inactivo".equalsIgnoreCase(user.getEstado())) {
            out.write("{\"status\":\"inactive\", \"message\":\"Usuario inactivo\"}");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("usuario", user.getUsuario());
        session.setAttribute("rol", user.getRol());
        session.setAttribute("nombre", user.getNombre());
        session.setAttribute("id_login", user.getId());

        int tiempoSesion;
        String imgSrc;
        String redirectPage;

        switch (user.getRol()) {
            case "alumno":
                tiempoSesion = TIEMPO_SESION_ALUMNO;
                imgSrc = "img/student.svg";
                redirectPage = "alumno";
                break;
            case "docente":
                tiempoSesion = TIEMPO_SESION_DOCENTE;
                imgSrc = "img/teacher.svg";
                redirectPage = "docente";
                break;
            case "administrador":
            default:
                tiempoSesion = TIEMPO_SESION_ADMIN;
                imgSrc = "img/admin.svg";
                redirectPage = "administrador";
                break;
        }
        session.setMaxInactiveInterval(tiempoSesion);
        session.setAttribute("imgSrc", imgSrc);
        session.setAttribute("panelOrigen", user.getRol());

        out.write("{\"status\":\"success\", \"redirectPage\":\"" + redirectPage + "\"}");
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet con uso de DAO y pool de conexiones";
    }
}
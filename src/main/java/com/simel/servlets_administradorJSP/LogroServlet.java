package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.LogroDAO;
import com.simel.modelo_administradorJSP.Logro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logro")
public class LogroServlet extends HttpServlet {

    private LogroDAO logroDAO = new LogroDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        System.out.println("Acción recibida: " + accion);

        boolean resultado = false;

        try {
            if ("agregar".equals(accion)) {
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");

                Logro nuevoLogro = new Logro(nombre, descripcion);
                resultado = logroDAO.insertarLogro(nuevoLogro);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");

                Logro logroEditar = new Logro(nombre, descripcion);
                logroEditar.setId(id);
                resultado = logroDAO.editarLogro(logroEditar);
            }
        } catch (Exception e) {

            e.printStackTrace(); // <-- IMPRIME EL ERROR EN CONSOLA
        }

        response.setContentType("text/plain");
        response.getWriter().print(resultado ? "OK" : "ERROR");
    }
}

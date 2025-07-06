package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.RetoDAO;
import com.simel.modelo_administradorJSP.Reto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/retos")
public class RetoServlet extends HttpServlet {

    private final RetoDAO retoDAO = new RetoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        boolean resultado = false;

        try {
            if ("agregar".equals(accion)) {
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                int puntos = Integer.parseInt(request.getParameter("puntos"));
                int grado = Integer.parseInt(request.getParameter("grado"));

                int idGradoSeccion = mapGradoToId(grado);

                Reto reto = new Reto(nombre, descripcion, puntos, idGradoSeccion);
                resultado = retoDAO.insertarReto(reto);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                int puntos = Integer.parseInt(request.getParameter("puntos"));
                int grado = Integer.parseInt(request.getParameter("grado"));

                int idGradoSeccion = mapGradoToId(grado);

                Reto reto = new Reto(nombre, descripcion, puntos, idGradoSeccion);
                reto.setId(id);
                resultado = retoDAO.editarReto(reto);
            }

            if (resultado) {
                response.getWriter().write("OK");
            } else {
                response.getWriter().write("ERROR");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("ERROR");
        }
    }

    private int mapGradoToId(int grado) throws IllegalArgumentException {
        switch (grado) {
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            default:
                throw new IllegalArgumentException("Grado inválido");
        }
    }
}

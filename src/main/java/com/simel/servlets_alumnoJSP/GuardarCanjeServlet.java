package com.simel.servlets_alumnoJSP;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.simel.dao_alumnoJSP.AlumnoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/guardarCanje")
public class GuardarCanjeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Configurar correctamente la codificación de caracteres
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_login") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int idLogin = (int) session.getAttribute("id_login");
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        int idAlumno = alumnoDAO.obtenerIdAlumnoPorLogin(idLogin);

        // Leer el cuerpo del request (JSON)
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        int idPremio = json.get("idPremio").getAsInt();
        String codigo = json.get("codigo").getAsString();
        String estado = json.get("estado").getAsString();
        int puntosPremio = json.get("puntosPremio").getAsInt();

        JsonObject respuesta = new JsonObject();

        // Validar si ya canjeó el premio
        boolean yaCanjeo = alumnoDAO.yaCanjeoPremio(idAlumno, idPremio);

        if (yaCanjeo) {
            respuesta.addProperty("success", false);
            respuesta.addProperty("message", "Ya has canjeado este premio anteriormente.");
        } else {
            boolean exito = alumnoDAO.registrarCanje(idAlumno, idPremio, codigo, estado, puntosPremio);
            respuesta.addProperty("success", exito);
            if (!exito) {
                respuesta.addProperty("message", "No se pudo guardar el canje.");
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respuesta.toString());
    }
}

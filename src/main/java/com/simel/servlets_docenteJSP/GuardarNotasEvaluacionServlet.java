
package com.simel.servlets_docenteJSP;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.simel.dao_docenteJSP.DocenteDAO;
import com.simel.dao_docenteJSP.EvaluacionDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/guardarNotasEvaluacion")
public class GuardarNotasEvaluacionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Leer JSON
        BufferedReader reader = req.getReader();
        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

        int idCurso = json.get("idCurso").getAsInt();
        int grado = json.get("grado").getAsInt();
        String seccion = json.get("seccion").getAsString();
        int idGradoSeccion = new DocenteDAO().obtenerIdGradoSeccion(grado, seccion);

        EvaluacionDAO evaDao = new EvaluacionDAO();
        JsonArray array = json.getAsJsonArray("alumnos");
        for (JsonElement e : array) {
            JsonObject o = e.getAsJsonObject();
            evaDao.insertarOActualizarNota(
                o.get("idAlumno").getAsInt(),
                idCurso, idGradoSeccion,
                o.get("nota1").getAsDouble(),
                o.get("nota2").getAsDouble(),
                o.get("nota3").getAsDouble(),
                o.get("comentario").getAsString()
            );
        }
        resp.setContentType("application/json");
        resp.getWriter().print("{\"status\":\"ok\"}");
    }
}

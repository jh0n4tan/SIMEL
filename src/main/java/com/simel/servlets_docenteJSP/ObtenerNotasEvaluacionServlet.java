package com.simel.servlets_docenteJSP;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simel.dao_docenteJSP.DocenteDAO;
import com.simel.dao_docenteJSP.EvaluacionDAO;
import com.simel.modelo_docenteJSP.Evaluacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/obtenerNotasEvaluacion")
public class ObtenerNotasEvaluacionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idCurso = Integer.parseInt(req.getParameter("idCurso"));
        int grado = Integer.parseInt(req.getParameter("grado"));
        String seccion = req.getParameter("seccion");
        int idGradoSeccion = new DocenteDAO().obtenerIdGradoSeccion(grado, seccion);

        Map<Integer, Evaluacion> notasMap
                = new EvaluacionDAO().obtenerEvaluacionesPorCursoGrado(idCurso, idGradoSeccion);

        JsonArray arr = new JsonArray();
        for (Map.Entry<Integer, Evaluacion> e : notasMap.entrySet()) {
            JsonObject o = new JsonObject();
            o.addProperty("idAlumno", e.getKey());
            Evaluacion ev = e.getValue();
            o.addProperty("nota1", ev.getNota1());
            o.addProperty("nota2", ev.getNota2());
            o.addProperty("nota3", ev.getNota3());
            o.addProperty("promedio", ev.getPromedio());
            o.addProperty("comentario", ev.getComentario());
            arr.add(o);
        }
        resp.setContentType("application/json");
        resp.getWriter().print(arr.toString());
    }
}

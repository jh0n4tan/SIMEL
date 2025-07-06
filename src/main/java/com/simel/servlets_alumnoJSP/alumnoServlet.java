package com.simel.servlets_alumnoJSP;

import com.simel.dao_administradorJSP.PremioDAO;
import com.simel.dao_alumnoJSP.AlumnoDAO;
import com.simel.dao_alumnoJSP.RetoAlumnoDAO;
import com.simel.dao_alumnoJSP.RetoDAO;
import com.simel.modelo_administradorJSP.Premio;
import com.simel.modelo_alumnoJSP.CanjePremio;
import com.simel.modelo_alumnoJSP.CursoAlumno;
import com.simel.modelo_alumnoJSP.NivelInfo;
import com.simel.modelo_alumnoJSP.Evaluacion;
import com.simel.modelo_alumnoJSP.LogroAsignado;
import com.simel.modelo_alumnoJSP.Reto;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/alumno")
public class alumnoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String seccion = request.getParameter("seccion");
        if (seccion == null || seccion.isEmpty()) {
            seccion = "misCursos";  // valor por defecto
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_login") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idLoginAlumno = (int) session.getAttribute("id_login");

        switch (seccion) {
            case "misCursos":

                AlumnoDAO dao = new AlumnoDAO();  // Instancias el DAO
                List<CursoAlumno> cursos = dao.obtenerCursosPorAlumno(idLoginAlumno);
                request.setAttribute("cursosAlumno", cursos);

                break;

            case "misNotas":

                AlumnoDAO daoNotas = new AlumnoDAO();
                List<CursoAlumno> cursosParaNotas = daoNotas.obtenerCursosPorAlumno(idLoginAlumno);
                request.setAttribute("cursosParaNotas", cursosParaNotas);
                break;

            case "logros":
                AlumnoDAO daoLogros = new AlumnoDAO();
                NivelInfo nivelInfo = daoLogros.obtenerNivelPorLogin(idLoginAlumno);

                // Asegúrate de que nunca sea null para evitar errores en la vista
                if (nivelInfo == null) {
                    nivelInfo = new NivelInfo(0, 0, 0, 100);  // Nivel 0, 0 puntos
                }

                int idAlumnoLogros = daoLogros.obtenerIdAlumnoPorLogin(idLoginAlumno);
                List<LogroAsignado> listaLogros = daoLogros.obtenerLogrosPorAlumno(idAlumnoLogros);

                request.setAttribute("nivelInfo", nivelInfo);
                request.setAttribute("listaLogros", listaLogros);

                break;

            case "canjes":
                AlumnoDAO daoCanjes = new AlumnoDAO();

                int idAlumnoCanjes = daoCanjes.obtenerIdAlumnoPorLogin(idLoginAlumno);

                List<CanjePremio> listaCanjes = new ArrayList<>();
                try {
                    listaCanjes = daoCanjes.obtenerCanjesPorAlumno(idAlumnoCanjes);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Aquí podrías mostrar un mensaje de error, loguearlo o asignar una lista vacía
                }
                request.setAttribute("listaCanjes", listaCanjes);

                List<Premio> listaPremios = new PremioDAO().obtenerPremios();
                request.setAttribute("premios", listaPremios);

                NivelInfo nivelInfoCanjes = daoCanjes.obtenerNivelPorLogin(idLoginAlumno);
                if (nivelInfoCanjes == null) {
                    nivelInfoCanjes = new NivelInfo(0, 0, 0, 100);
                }
                request.setAttribute("nivelInfo", nivelInfoCanjes);
                break;

            case "retos":
                AlumnoDAO alumnoDAO = new AlumnoDAO();
                int idAlumno = alumnoDAO.obtenerIdAlumnoPorLogin(idLoginAlumno);

                Integer idGradoSeccion = null;
                List<CursoAlumno> cursosAlumno = alumnoDAO.obtenerCursosPorAlumno(idLoginAlumno);

                if (!cursosAlumno.isEmpty()) {
                    idGradoSeccion = cursosAlumno.get(0).getIdGradoSeccion();
                    session.setAttribute("idGradoSeccion", idGradoSeccion);
                } else {
                    idGradoSeccion = 0;
                }

                RetoAlumnoDAO retoAlumnoDAO = new RetoAlumnoDAO();

                List<Reto> retosDisponibles = retoAlumnoDAO.obtenerRetosDisponibles(idAlumno, idGradoSeccion);
                List<Reto> retosPendientes = retoAlumnoDAO.obtenerRetosPorEstado(idAlumno, "pendiente");
                List<Reto> retosCompletados = retoAlumnoDAO.obtenerRetosPorEstado(idAlumno, "completado");

                request.setAttribute("retosDisponibles", retosDisponibles);
                request.setAttribute("retosPendientes", retosPendientes);
                request.setAttribute("retosCompletados", retosCompletados);

                break;
            default:
                break;
        }

        // Resto de las secciones igual...
        request.setAttribute("seccion", seccion);
        request.getRequestDispatcher("/WEB-INF/alumno.jsp").forward(request, response);
    }
}

package com.simel.servlets_docenteJSP;

import com.simel.dao_administradorJSP.LogroDAO;
import com.simel.dao_docenteJSP.AlumnoDAO;
import com.simel.dao_docenteJSP.DocenteDAO;
import com.simel.dao_docenteJSP.LogroAsignadoDAO;
import com.simel.modelo_administradorJSP.Logro;
import com.simel.modelo_docenteJSP.Alumno;
import com.simel.modelo_docenteJSP.CursoAsignadoDocente;
import com.simel.modelo_docenteJSP.LogroAsignado;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/docente")
public class docenteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String seccion = request.getParameter("seccion");
        if (seccion == null || seccion.isEmpty()) {
            seccion = "misCursos";  // valor por defecto
        }

        HttpSession session = request.getSession();
        String usuarioSesion = (String) session.getAttribute("usuario"); // usuario de login
        int idDocente = new DocenteDAO().obtenerIdDocentePorUsuario(usuarioSesion);

        // Puedes guardar el ID en sesión si lo vas a usar varias veces
        session.setAttribute("idDocente", idDocente);
        switch (seccion) {
            case "misCursos":
                List<CursoAsignadoDocente> cursos = new DocenteDAO().obtenerCursosPorDocente(idDocente);
                request.setAttribute("cursosAsignados", cursos);
                break;

            case "ingresarNotas":

                List<CursoAsignadoDocente> cursosNotas = new DocenteDAO().obtenerCursosPorDocente(idDocente);
                request.setAttribute("cursosAsignados", cursosNotas);
                break;

            case "asignarLogros":

                List<CursoAsignadoDocente> cursosLogros = new DocenteDAO().obtenerCursosPorDocente(idDocente);
                request.setAttribute("cursosAsignados", cursosLogros);

                LogroDAO logroDAO = new LogroDAO();
                List<Logro> listaLogros = logroDAO.obtenerLogros();
                request.setAttribute("logros", listaLogros);

                // Para obtener historial
                LogroAsignadoDAO dao = new LogroAsignadoDAO();
                List<LogroAsignado> historial = dao.obtenerHistorialLogros(idDocente);
                request.setAttribute("historialLogros", historial);
                break;

            case "rankingEstudiantes":

                int tamPagina = 20; // alumnos por página
                int pagina = 1;

                String p = request.getParameter("p");
                if (p != null) {
                    try {
                        pagina = Integer.parseInt(p);
                    } catch (NumberFormatException ignored) {
                    }
                }

                AlumnoDAO alumnoDAO = new AlumnoDAO();
                int totalAlumnos = alumnoDAO.contarAlumnos(); // necesitas este método en DAO
                int totalPaginas = (int) Math.ceil((double) totalAlumnos / tamPagina);

                List<Alumno> ranking = alumnoDAO.obtenerRankingAlumnosConMedallas(pagina, tamPagina);

                request.setAttribute("rankingAlumnos", ranking);
                request.setAttribute("pagina", pagina);
                request.setAttribute("totalPaginas", totalPaginas);

                break;

            case "retosEstudiantes":

                List<CursoAsignadoDocente> cursoRetos = new DocenteDAO().obtenerCursosPorDocente(idDocente);
                request.setAttribute("cursosAsignados", cursoRetos);

                break;
            default:
                break;
        }

        // Resto de las secciones igual...
        request.setAttribute("seccion", seccion);
        request.getRequestDispatcher("/WEB-INF/docente.jsp").forward(request, response);
    }
}

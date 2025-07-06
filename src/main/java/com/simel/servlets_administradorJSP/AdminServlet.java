package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.AsignacionDAO;
import com.simel.dao_administradorJSP.CanjeDAO;
import com.simel.dao_administradorJSP.LogroDAO;
import com.simel.dao_administradorJSP.PremioDAO;
import com.simel.dao_administradorJSP.RetoDAO;
import com.simel.dao_administradorJSP.UsuarioDAO;
import com.simel.dao_docenteJSP.AlumnoDAO;
import com.simel.modelo_administradorJSP.Alumno;
import com.simel.modelo_administradorJSP.Canje;
import com.simel.modelo_administradorJSP.Curso;
import com.simel.modelo_administradorJSP.CursoAsignado;
import com.simel.modelo_administradorJSP.Docente;
import com.simel.modelo_administradorJSP.GradoSeccion;
import com.simel.modelo_administradorJSP.Logro;
import com.simel.modelo_administradorJSP.Premio;
import com.simel.modelo_administradorJSP.Reto;
import com.simel.modelo_login.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/administrador")
public class AdminServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();  // Ya no usa la clase Conexion

    private final AsignacionDAO asignacionDAO = new AsignacionDAO();  // nuevo DAO que contiene los métodos

    private final LogroDAO logroDAO = new LogroDAO(); // DAO para los logros

    private final RetoDAO retoDAO = new RetoDAO(); // ✔️ // si lo usarás después

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String seccion = request.getParameter("seccion");
        if (seccion == null || seccion.isEmpty()) {
            seccion = "usuarios";  // valor por defecto
        }

        switch (seccion) {
            case "usuarios":
                List<Usuario> listaUsuarios = usuarioDAO.obtenerUsuarios();
                request.setAttribute("usuarios", listaUsuarios);
                break;

            case "cursosAsignaciones":
                List<Docente> docentes = asignacionDAO.obtenerDocentes();
                List<Curso> cursos = asignacionDAO.obtenerCursos();
                List<GradoSeccion> gradosSeccion = asignacionDAO.obtenerGradosSecciones();
                List<CursoAsignado> cursosAsignados = asignacionDAO.obtenerCursosAsignados();
                List<Alumno> alumnos = asignacionDAO.obtenerAlumnos();
                List<String> resumenPorSeccion = asignacionDAO.obtenerResumenAlumnosPorSeccion();
                request.setAttribute("docentes", docentes);
                request.setAttribute("cursos", cursos);
                request.setAttribute("gradosSeccion", gradosSeccion);
                request.setAttribute("cursosAsignados", cursosAsignados);
                request.setAttribute("alumnos", alumnos);
                request.setAttribute("resumenPorSeccion", resumenPorSeccion);
                break;

            case "logrosPremios":

                List<Logro> listaLogros = logroDAO.obtenerLogros(); // 👈 obtener los logros de la DB
                request.setAttribute("logros", listaLogros);
                break;

            case "canjes":
                List<Premio> listaPremios = new PremioDAO().obtenerPremios();
                request.setAttribute("premios", listaPremios);

                break;

            case "reportes":
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

                List<com.simel.modelo_docenteJSP.Alumno> ranking = alumnoDAO.obtenerRankingAlumnosConMedallas(pagina, tamPagina);

                request.setAttribute("rankingAlumnos", ranking);
                request.setAttribute("pagina", pagina);
                request.setAttribute("totalPaginas", totalPaginas);

                // Obtener lista de canjes para la pestaña "Canjes Realizados"
                CanjeDAO canjeDAO = new CanjeDAO();
                List<Canje> listaCanjes = canjeDAO.obtenerCanjes();
                request.setAttribute("listaCanjes", listaCanjes);

                break;

            case "retosadmin":

                List<Reto> listaRetos = retoDAO.obtenerRetos();  // Este método debes implementarlo en RetoDAO
                request.setAttribute("retos", listaRetos);
                break;
            default:
                break;
        }

        // Resto de las secciones igual...
        request.setAttribute("seccion", seccion);
        request.getRequestDispatcher("/WEB-INF/administrador.jsp").forward(request, response);

    }
}

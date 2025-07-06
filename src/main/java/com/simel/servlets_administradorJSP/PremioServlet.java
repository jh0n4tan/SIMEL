package com.simel.servlets_administradorJSP;

import com.simel.dao_administradorJSP.PremioDAO;
import com.simel.modelo_administradorJSP.Premio;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/premio")
@MultipartConfig
public class PremioServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:/SIMEL_UPLOAD/img/premios";
    private PremioDAO premioDAO = new PremioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        boolean resultado = false;

        Part filePart = request.getPart("archivoImagenPremio");
        String fileName = null;

        if (filePart != null && filePart.getSize() > 0) {
            String submittedFileName = filePart.getSubmittedFileName();
            String extension = submittedFileName.substring(submittedFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(UPLOAD_DIR + File.separator + uniqueFileName);

            // Guardar solo el nombre del archivo en la BD
            fileName = uniqueFileName;
        } else {
            fileName = request.getParameter("imagenAntigua");
        }

        if ("agregar".equals(accion)) {
            String nombre = request.getParameter("nombrePremio");
            String descripcion = request.getParameter("descripcionPremio");
            int puntos = Integer.parseInt(request.getParameter("puntosPremio"));
            String tipo = request.getParameter("tipoPremio");

            Premio nuevoPremio = new Premio(nombre, descripcion, puntos, tipo, fileName);
            resultado = premioDAO.insertarPremio(nuevoPremio);

        } else if ("editar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("premioId"));
            String nombre = request.getParameter("nombrePremio");
            String descripcion = request.getParameter("descripcionPremio");
            int puntos = Integer.parseInt(request.getParameter("puntosPremio"));
            String tipo = request.getParameter("tipoPremio");

            Premio premioEditar = new Premio(nombre, descripcion, puntos, tipo, fileName);
            premioEditar.setId(id);

            resultado = premioDAO.editarPremio(premioEditar);
        }

        response.setContentType("text/plain");
        response.getWriter().print(resultado ? "OK" : "ERROR");
    }
}

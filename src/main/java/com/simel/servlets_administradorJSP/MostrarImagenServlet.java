package com.simel.servlets_administradorJSP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/mostrarImagen")
public class MostrarImagenServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:/SIMEL_UPLOAD/img/premios";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");

        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro file");
            return;
        }

        File imageFile = new File(UPLOAD_DIR, fileName);

        if (!imageFile.exists() || !imageFile.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        // Detectar el tipo mime
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLengthLong(imageFile.length());

        // Enviar el contenido del archivo al response
        java.nio.file.Files.copy(imageFile.toPath(), response.getOutputStream());
    }
}

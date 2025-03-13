/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.file;

import com.joca.lacomputadorafeliz.file.InputFileReader;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author joca
 */
@WebServlet(name = "FileController", urlPatterns = {"/controllers/file-servlet"})
@MultipartConfig
public class FileController extends HttpServlet {

    /**
     * Crea una nueva computadora en la base de datos
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");

        InputFileReader inputFile = new InputFileReader(request.getSession());
        List<String> errors = inputFile.readFile(filePart);
        if (errors.isEmpty()) {
            request.setAttribute("message", "No se registraron errores");
            request.setAttribute("success", true);
        } else {
            request.setAttribute("message", "Se registraron " + errors.size() + " errores");
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/admin/error-log.jsp").forward(request, response);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.computers;

import com.joca.lacomputadorafeliz.computers.AdminComponents;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
@WebServlet(name = "EditComponentController", urlPatterns = {"/controllers/edit-component-servlet"})
public class EditComponentController extends HttpServlet {

    /**
     * Edita la información de una computadora en el sistema
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminComponents adminComponents = new AdminComponents(request.getSession());
            adminComponents.updateComponent(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Componente actualizado con exito");
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al actualizar el componente, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/edit-component-loader?componentName="+request.getParameter("originalName")).forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/edit-component-loader?componentName="+request.getParameter("originalName")).forward(request, response);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.computers;

import com.joca.lacomputadorafeliz.computers.AdminComponents;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
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
@WebServlet(name = "ComponentController", urlPatterns = {"/controllers/component-servlet"})
public class ComponentController extends HttpServlet {

    /**
     * Crea un nuevo componente en la base de datos
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminComponents adminComponents = new AdminComponents(request.getSession());
            adminComponents.createComponent(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Componente creado con exito");
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al crear el Componente, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/component/new-component.jsp").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/component/new-component.jsp").forward(request, response);
        }
    }
    
    /**
     * Elimina un componente del sistema
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminComponents adminComponents = new AdminComponents(request.getSession());
            adminComponents.deleteComponent(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Componente eliminado con exito");
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException | EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al eliminar el componente, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        }
    }
}

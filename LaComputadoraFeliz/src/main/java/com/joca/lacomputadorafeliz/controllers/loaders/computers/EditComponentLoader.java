/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.computers;

import com.joca.lacomputadorafeliz.computers.AdminComponents;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.Component;
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
@WebServlet(name = "EditComponentLoader", urlPatterns = {"/controllers/edit-component-loader"})
public class EditComponentLoader extends HttpServlet {


    /**
     * Handles the HTTP <code>GET</code> method.
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
            Component component = adminComponents.searchComponent(request);
            request.setAttribute("component", component);
            request.getRequestDispatcher("/component/edit-component.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener el componente");
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        } catch (EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/components-loader").forward(request, response);
        }
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

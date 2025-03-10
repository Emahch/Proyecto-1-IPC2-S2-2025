/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.computers;

import com.joca.lacomputadorafeliz.computers.AdminAssignments;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
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
@WebServlet(name = "EditAsignLoader", urlPatterns = {"/controllers/edit-asign-loader"})
public class EditAsignLoader extends HttpServlet {


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
            AdminAssignments adminAssignments = new AdminAssignments(request.getSession());
            ComponentAsignDTO assignment = adminAssignments.searchAssignment(request);
            request.setAttribute("assignment", assignment);
            request.getRequestDispatcher("/assemble/edit-assignment.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos de la computadoras");
            request.getRequestDispatcher("/controllers/computers-loader").forward(request, response);
        } catch (EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/computers-loader").forward(request, response);
        }
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

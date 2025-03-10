/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.computers;

import com.joca.lacomputadorafeliz.computers.AdminAssignments;
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
@WebServlet(name = "AssignmentController", urlPatterns = {"/controllers/assignment-servlet"})
public class AssignmentController extends HttpServlet {

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminAssignments adminAssignments = new AdminAssignments(request.getSession());
            adminAssignments.deleteAsignment(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Asignacion de componente eliminada con exito");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } catch (EntityNotFound | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al eliminar la asignacion, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        }
    }
    
    /**
     * Edita la cantidad de una asignacion en el sistema
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
            AdminAssignments adminAssignments = new AdminAssignments(request.getSession());
            adminAssignments.createAssignment(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Asignacion de componente creada con exito");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException | EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al crear la asignacion, intentalo de nuevo más tarde");
            request.setAttribute("computerName", request.getParameter("computerName"));
            request.getRequestDispatcher("/controllers/new-asign-loader").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("computerName", request.getParameter("computerName"));
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/new-asign-loader").forward(request, response);
        }
    }
}

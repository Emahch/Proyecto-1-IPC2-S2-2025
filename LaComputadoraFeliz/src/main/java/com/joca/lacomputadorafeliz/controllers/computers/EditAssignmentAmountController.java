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
@WebServlet(name = "EditAssignmentAmountController", urlPatterns = {"/controllers/edit-assignment-servlet"})
public class EditAssignmentAmountController extends HttpServlet {

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
            adminAssignments.updateAssignmentAmount(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Asignacion de componente actualizada con exito");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException | EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al actualizar la asignacion, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/edit-asign-loader").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/edit-asign-loader").forward(request, response);
        }
    }
}

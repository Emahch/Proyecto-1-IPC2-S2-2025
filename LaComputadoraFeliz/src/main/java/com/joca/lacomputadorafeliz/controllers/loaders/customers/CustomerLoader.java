/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.customers;

import com.joca.lacomputadorafeliz.computers.AdminAssignments;
import com.joca.lacomputadorafeliz.computers.AdminComputers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
import com.joca.lacomputadorafeliz.model.computers.Computer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author joca
 */
@WebServlet(name = "CustomerLoader", urlPatterns = {"/controllers/customer-loader"})
public class CustomerLoader extends HttpServlet {


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
            AdminComputers adminComputers = new AdminComputers(request.getSession());
            Computer computer = adminComputers.searchComputer(request);
            AdminAssignments adminAssignments = new AdminAssignments(request.getSession());
            List<ComponentAsignDTO> assignments = adminAssignments.getComponentsAssigned(request);
            List<ComponentAsignDTO> assignmentsOrdered = assignments.stream()
                    .sorted(Comparator.comparingInt(ComponentAsignDTO::getAmount))
                    .collect(Collectors.toList());
            request.setAttribute("computer", computer);
            request.setAttribute("assignments", assignmentsOrdered);
            request.getRequestDispatcher("/computer/edit-computer.jsp").forward(request, response);
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

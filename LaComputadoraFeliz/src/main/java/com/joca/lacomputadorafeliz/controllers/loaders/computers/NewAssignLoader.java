/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.computers;

import com.joca.lacomputadorafeliz.computers.AdminAssignments;
import com.joca.lacomputadorafeliz.computers.AdminComponents;
import com.joca.lacomputadorafeliz.model.computers.Component;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author joca
 */
@WebServlet(name = "NewAssignLoader", urlPatterns = {"/controllers/new-assign-loader"})
public class NewAssignLoader extends HttpServlet {

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
            List<Component> allComponents = adminComponents.getComponents();
            AdminAssignments adminAssignments = new AdminAssignments(request.getSession());
            List<ComponentAsignDTO> componentsAssigned = adminAssignments.getComponentsAssigned(request);
            List<Component> componentsAvailables = allComponents.stream()
                    .filter(c -> componentsAssigned.stream()
                    .noneMatch(a -> a.getComponentName().equals(c.getName())))
                    .collect(Collectors.toList());
            request.setAttribute("components", componentsAvailables);
            request.setAttribute("computerName", request.getParameter("computerName"));
            request.getRequestDispatcher("/assemble/new-assignment.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos de los componentes");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

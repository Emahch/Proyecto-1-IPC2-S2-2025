/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.computers;

import com.joca.lacomputadorafeliz.computers.AdminComponents;
import com.joca.lacomputadorafeliz.model.computers.Component;
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
@WebServlet(name = "ComponentsLoader", urlPatterns = {"/controllers/components-loader"})
public class ComponentsLoader extends HttpServlet {


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
            List<Component> components = adminComponents.getComponents();
            List<Component> componentsOrdered = components.stream()
                    .sorted(Comparator.comparingInt(Component::getAmount))
                    .collect(Collectors.toList());
            request.setAttribute("components", componentsOrdered);
            request.setAttribute("ascendent", true);
            request.getRequestDispatcher("/component/admin-components.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los componentes");
            UserRedirect redirect = new UserRedirect();
            redirect.redirect(request, response);
        }
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminComponents adminComponents = new AdminComponents(request.getSession());
            List<Component> components = adminComponents.getComponents();
            List<Component> componentsOrdered = components.stream()
                    .sorted(Comparator.comparingInt(Component::getAmount).reversed())
                    .collect(Collectors.toList());
            request.setAttribute("components", componentsOrdered);
            request.setAttribute("ascendent", false);
            request.getRequestDispatcher("/component/admin-components.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los componentes");
            UserRedirect redirect = new UserRedirect();
            redirect.redirect(request, response);
        }
    }
}

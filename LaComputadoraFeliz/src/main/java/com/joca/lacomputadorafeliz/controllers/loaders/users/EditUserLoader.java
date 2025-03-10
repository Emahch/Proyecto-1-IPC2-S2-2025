/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.users;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import com.joca.lacomputadorafeliz.users.AdminRoles;
import com.joca.lacomputadorafeliz.users.AdminUsers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
@WebServlet(name = "EditUserLoader", urlPatterns = {"/controllers/edit-user-loader"})
public class EditUserLoader extends HttpServlet {


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
            AdminRoles adminRoles = new AdminRoles(request.getSession());
            List<UserRol> roles = adminRoles.getRoles();
            AdminUsers adminUsers = new AdminUsers(request.getSession());
            User user = adminUsers.getUser(request);
            request.setAttribute("roles", roles);
            request.setAttribute("usuario", user);
            request.getRequestDispatcher("/admin/edit-user.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al acceder al usuario");
            request.getRequestDispatcher("/controllers/users-loader").forward(request, response);
        } catch (EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", "No se encontro el usuario");
            request.getRequestDispatcher("/controllers/users-loader").forward(request, response);
        }
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

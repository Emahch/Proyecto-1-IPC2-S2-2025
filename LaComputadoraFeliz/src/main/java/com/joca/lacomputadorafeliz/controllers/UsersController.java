/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers;

import com.joca.lacomputadorafeliz.users.AdminUsers;
import com.joca.lacomputadorafeliz.model.users.User;
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
@WebServlet(name = "UsersController", urlPatterns = {"/controllers/users-servlet"})
public class UsersController extends HttpServlet {

    /**
     * Obtiene todos los usuarios hallados en la base de datos
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
            AdminUsers adminUsers = new AdminUsers(request.getSession());
            List<User> users = adminUsers.getUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/admin-users.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
        }
    }

    /**
     * Crea un nuevo usuario en el sistema
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}

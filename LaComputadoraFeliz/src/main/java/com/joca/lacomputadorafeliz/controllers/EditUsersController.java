/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers;

import com.joca.lacomputadorafeliz.users.AdminUsers;
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
@WebServlet(name = "EditUsersController", urlPatterns = {"/controllers/edit-users-servlet"})
public class EditUsersController extends HttpServlet {

    /**
     * Elimina un usuario
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
            adminUsers.deleteUser(request);
            request.getRequestDispatcher("/controllers/users-servlet").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            request.getRequestDispatcher("/admin/admin-users.jsp").forward(request, response);
        }
    }

}
